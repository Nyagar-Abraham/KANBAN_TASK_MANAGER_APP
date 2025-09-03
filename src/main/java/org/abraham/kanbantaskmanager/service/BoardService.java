package org.abraham.kanbantaskmanager.service;

import jakarta.transaction.Transactional;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.abraham.kanbantaskmanager.Exceptions.DuplicateEntityException;
import org.abraham.kanbantaskmanager.Exceptions.EntityNotFoundException;
import org.abraham.kanbantaskmanager.dtos.BoardResponse;
import org.abraham.kanbantaskmanager.dtos.requests.CreateBoardRequest;
import org.abraham.kanbantaskmanager.dtos.requests.UpdateBoardInput;
import org.abraham.kanbantaskmanager.entities.Board;
import org.abraham.kanbantaskmanager.entities.BoardColumn;
import org.abraham.kanbantaskmanager.entities.TaskStatusAndColumnName;
import org.abraham.kanbantaskmanager.mappers.BoardMapper;
import org.abraham.kanbantaskmanager.repository.BoardColumnRepository;
import org.abraham.kanbantaskmanager.repository.BoardRepository;
import org.abraham.kanbantaskmanager.repository.UserRepository;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.List;
import java.util.Objects;
import java.util.Set;
import java.util.stream.Collectors;

@Slf4j
@Service
@AllArgsConstructor
public class BoardService {
    private final BoardColumnRepository boardColumnRepository;
    private final BoardRepository boardRepository;
    private final UserRepository userRepository;
    private final BoardMapper boardMapper;

    public BoardResponse createBoard(CreateBoardRequest request) {
        log.info("Service: createBoard request = {}", request);
        boardRepository.findByName(request.getName())
                .ifPresent(board -> {
                    throw new DuplicateEntityException("Board", "name", request.getName());
                });

        var user_id = request.getCreatedBy();
        var user = userRepository.findById(user_id).orElse(null);
        if (user == null)
            throw new EntityNotFoundException("User", "Id", user_id.toString());


        var board = new Board();
        board.setName(request.getName());
        board.setDescription(request.getDescription());
        board.setCreatedBy(user);
        board.setColor(request.getColor());

        addColumnsToBoardIfExistOrElseCreate(request.getColumns(), board);

        boardRepository.save(board);

        log.info("board : {}", board);
        return boardMapper.boardToBoardResponse(board);
    }

    public List<BoardResponse> getBoards() {
        return boardRepository.findAll().stream().map(boardMapper::boardToBoardResponse).collect(Collectors.toList());
    }

    public BoardResponse getBoardById(Long boardId) {
        var board = boardRepository.findById(boardId).orElseThrow(() -> new EntityNotFoundException("Board", "Id", boardId.toString()));
        return boardMapper.boardToBoardResponse(board);
    }

    public BoardResponse updateBoardName(Long board_id, String name) {
        var board = boardRepository.findById(board_id).orElseThrow(() -> new EntityNotFoundException("Board", "Id", board_id.toString()));
        board.setName(name);
        boardRepository.save(board);
        return boardMapper.boardToBoardResponse(board);
    }

    public BoardResponse updateBoardColumns(Long board_id, Set<TaskStatusAndColumnName> columns) {
        var board = boardRepository.findById(board_id).orElseThrow(() -> new EntityNotFoundException("Board", "Id", board_id.toString()));
        addAndRemoveNecessaryColumns(columns, board);
        boardRepository.save(board);
        return boardMapper.boardToBoardResponse(board);
    }

    @Transactional
    public BoardResponse updateBoard(UpdateBoardInput request, Long boardId) {
        var board = boardRepository.findById(boardId).orElseThrow(() -> new EntityNotFoundException("Board", "Id", boardId.toString()));
        if (!Objects.isNull(request.getName())) board.setName(request.getName());
        if (!Objects.isNull(request.getDescription())) board.setDescription(request.getDescription());
        if (!Objects.isNull(request.getColor())) board.setColor(request.getColor());
        if (!Objects.isNull(request.getColumns()))
            addAndRemoveNecessaryColumns(request.getColumns(), board);

        boardRepository.save(board);
        return boardMapper.boardToBoardResponse(board);
    }

    public void deleteBoard(Long boardId) {
        boardRepository.findById(boardId).orElseThrow(() -> new EntityNotFoundException("Board", "Id", boardId.toString()));
        boardRepository.deleteById(boardId);

    }

    private void addAndRemoveNecessaryColumns(Set<TaskStatusAndColumnName> newColumnsNames, Board board) {
        var currentColumns = new HashSet<>(board.getColumns());
        var currentColumnsNames = board.getColumns().stream().map(BoardColumn::getName).collect(Collectors.toSet());

        for (BoardColumn column : currentColumns)
            if (!newColumnsNames.contains(column.getName()))
                board.removeColumn(column);


        newColumnsNames.forEach(newColumnsName -> {
            if (!currentColumnsNames.contains(newColumnsName)) {
                var column = boardColumnRepository.findByName(newColumnsName).orElse(null);
                if (column == null) {
                    column = new BoardColumn();
                    column.setName(newColumnsName);
                }
                board.addColumn(column);
            }
        });
    }

    private void addColumnsToBoardIfExistOrElseCreate(Set<TaskStatusAndColumnName> columns, Board board) {
        columns.forEach(column -> {
            var boardColumn = boardColumnRepository.findByName(column).orElse(null);
            if (boardColumn != null) {
                board.addColumn(boardColumn);
            } else {
                var newBoardColumn = new BoardColumn();
                newBoardColumn.setName(column);
                newBoardColumn.setPosition(1);
                board.addColumn(newBoardColumn);
            }
        });
    }


}
