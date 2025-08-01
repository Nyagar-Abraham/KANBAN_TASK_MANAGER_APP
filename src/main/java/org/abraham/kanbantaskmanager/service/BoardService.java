package org.abraham.kanbantaskmanager.service;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.abraham.kanbantaskmanager.Exceptions.DuplicateNameException;
import org.abraham.kanbantaskmanager.dtos.CreateBoardRequest;
import org.abraham.kanbantaskmanager.dtos.CreateBoardResponse;
import org.abraham.kanbantaskmanager.entities.Board;
import org.abraham.kanbantaskmanager.entities.BoardColumn;
import org.abraham.kanbantaskmanager.mappers.BoardMapper;
import org.abraham.kanbantaskmanager.repository.BoardColumnRepository;
import org.abraham.kanbantaskmanager.repository.BoardRepository;
import org.springframework.stereotype.Service;

@Slf4j
@Service
@AllArgsConstructor
public class BoardService {

    private final BoardColumnRepository boardColumnRepository;
    private BoardRepository boardRepository;

    public CreateBoardResponse createBoard(CreateBoardRequest request) {
         boardRepository.findByName(request.getName())
                 .ifPresent(board -> {
                     throw new DuplicateNameException("board", request.getName());
                 });

         log.info("Creating board with name {}", request.getName());

         var board = new Board();
         board.setName(request.getName());

         request.getColumns().forEach(column -> {
             var  boardColumn = boardColumnRepository.findByName(column.name()).orElse(null);
             if (boardColumn != null) {
                board.addColumn(boardColumn);
             }else {
                 var newBoardColumn = new BoardColumn();
                 newBoardColumn.setName(column.name());
                 board.addColumn(newBoardColumn);
             }
         });
         var db_board = boardRepository.save(board);
         return BoardMapper.toDto(db_board);
    }
}
