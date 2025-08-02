package org.abraham.kanbantaskmanager.service;

import jakarta.transaction.Transactional;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.abraham.kanbantaskmanager.Exceptions.DuplicateNameException;
import org.abraham.kanbantaskmanager.Exceptions.EntityNotFoundException;
import org.abraham.kanbantaskmanager.dtos.CreateBoardRequest;
import org.abraham.kanbantaskmanager.dtos.BoardResponse;
import org.abraham.kanbantaskmanager.dtos.EditBoardRequest;
import org.abraham.kanbantaskmanager.entities.Board;
import org.abraham.kanbantaskmanager.entities.BoardColumn;
import org.abraham.kanbantaskmanager.entities.TaskStatusAndColumnName;
import org.abraham.kanbantaskmanager.mappers.BoardMapper;
import org.abraham.kanbantaskmanager.repository.BoardColumnRepository;
import org.abraham.kanbantaskmanager.repository.BoardRepository;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Slf4j
@Service
@AllArgsConstructor
public class BoardService {
    private  final  BoardColumnRepository boardColumnRepository;
    private final BoardRepository boardRepository;

    public BoardResponse createBoard(CreateBoardRequest request) {
         boardRepository.findByName(request.getName())
                 .ifPresent(board -> {
                     throw new DuplicateNameException("Board", request.getName());
                 });

         var board = new Board();
         board.setName(request.getName());

        addColumnsToBoardIfExistOrElseCreate(request.getColumns(), board);

        boardRepository.save(board);
         return BoardMapper.toDto(board);
    }

    @Transactional
    public BoardResponse updateBoard(EditBoardRequest request, Long boardId) {
        var board = boardRepository.findById(boardId).orElseThrow(() -> new EntityNotFoundException("Board",boardId.toString()));
        board.setName(request.getName());

        addAndRemoveNecessaryColumns(request.getColumns(), board);

        boardRepository.save(board);
        return BoardMapper.toDto(board);
    }

    public void deleteBoard(Long boardId) {
        boardRepository.deleteById(boardId);
    }

    public List<BoardResponse> getBoards() {
       return boardRepository.findAll().stream().map(BoardMapper::toDto).collect(Collectors.toList());
    }

    private  void addAndRemoveNecessaryColumns(Set<TaskStatusAndColumnName> newColumnsNames, Board board) {
        var currentColumns = new HashSet<>(board.getColumns());
        var currentColumnsNames = board.getColumns().stream().map(BoardColumn::getName).collect(Collectors.toSet());

        for(BoardColumn column : currentColumns) {
            if(!newColumnsNames.contains(column.getName()))
                board.removeColumn(column);
        }

        newColumnsNames.forEach(newColumnsName -> {
            if(!currentColumnsNames.contains(newColumnsName)){
                var column = boardColumnRepository.findByName(newColumnsName).orElse(null);
                if(column == null) {
                    column= new BoardColumn();
                    column.setName(newColumnsName);
                }
                board.addColumn(column);
            }
        });
    }

    private void addColumnsToBoardIfExistOrElseCreate(Set<TaskStatusAndColumnName> columns, Board board) {
        columns.forEach(column -> {
            var  boardColumn = boardColumnRepository.findByName(column).orElse(null);
            if (boardColumn != null) {
                board.addColumn(boardColumn);
            }else {
                var newBoardColumn = new BoardColumn();
                newBoardColumn.setName(column);
                board.addColumn(newBoardColumn);
            }
        });
    }

    public BoardResponse getBoardById(Long boardId) {
        var board = boardRepository.findById(boardId).orElseThrow();
        return BoardMapper.toDto(board);
    }
}
