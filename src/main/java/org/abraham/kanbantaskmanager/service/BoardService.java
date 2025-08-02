package org.abraham.kanbantaskmanager.service;

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
import org.abraham.kanbantaskmanager.mappers.BoardMapper;
import org.abraham.kanbantaskmanager.repository.BoardColumnRepository;
import org.abraham.kanbantaskmanager.repository.BoardRepository;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.List;
import java.util.stream.Collectors;

@Slf4j
@Service
@AllArgsConstructor
public class BoardService {

    private final BoardColumnRepository boardColumnRepository;
    private BoardRepository boardRepository;

    public BoardResponse createBoard(CreateBoardRequest request) {
         boardRepository.findByName(request.getName())
                 .ifPresent(board -> {
                     throw new DuplicateNameException("board", request.getName());
                 });

         var board = new Board();
         board.setName(request.getName());

         request.getColumns().forEach(column -> {
             var  boardColumn = boardColumnRepository.findByName(column).orElse(null);
             if (boardColumn != null) {
                board.addColumn(boardColumn);
             }else {
                 var newBoardColumn = new BoardColumn();
                 newBoardColumn.setName(column);
                 board.addColumn(newBoardColumn);
             }
         });
         boardRepository.save(board);
         return BoardMapper.toDto(board);
    }

    public BoardResponse updateBoard( EditBoardRequest request, Long boardId) {
        var board = boardRepository.findById(boardId).orElseThrow(() -> new EntityNotFoundException("Board",boardId.toString()));
        board.setName(request.getName());

        var currentColumns = new HashSet<>(board.getColumns());
        var newColumnsNames = request.getColumns();
        var currentColumnsNames = board.getColumns().stream().map(BoardColumn::getName).collect(Collectors.toSet());

        for(BoardColumn column : currentColumns) {
            if(!newColumnsNames.contains(column.getName()))
                board.removeColumn(column);
        }

        newColumnsNames.forEach(newColumnsName -> {
            if(!currentColumnsNames.contains(newColumnsName)){
                var newBoardColumn = new BoardColumn();
                newBoardColumn.setName(newColumnsName);
                board.addColumn(newBoardColumn);
            }
        });

        boardRepository.save(board);
        return BoardMapper.toDto(board);
    }

    public void deleteBoard(Long boardId) {
//        boardRepository.findById(boardId).orElseThrow(() -> new EntityNotFoundException("Board",boardId.toString()));
        boardRepository.deleteById(boardId);
    }

    public List<BoardResponse> getBoards() {
       return boardRepository.findAll().stream().map(BoardMapper::toDto).collect(Collectors.toList());
    }
}
