package org.abraham.kanbantaskmanager.controller;

import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.abraham.kanbantaskmanager.dtos.CreateBoardRequest;
import org.abraham.kanbantaskmanager.dtos.BoardResponse;
import org.abraham.kanbantaskmanager.dtos.EditBoardRequest;
import org.abraham.kanbantaskmanager.service.BoardService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.util.UriComponentsBuilder;

import java.util.List;

@RestController
@RequestMapping("/boards")
@AllArgsConstructor
public class BoardController {
    private final BoardService boardService;

    @GetMapping
    public ResponseEntity<List<BoardResponse>> getAllBoards() {
        var boards = boardService.getBoards();
        return ResponseEntity.ok(boards);
    }

    @PostMapping()
    public ResponseEntity<BoardResponse> createBoard(@Valid @RequestBody CreateBoardRequest createBoardRequest, UriComponentsBuilder uriBuilder) {
       var board =  boardService.createBoard(createBoardRequest);
       var uri = uriBuilder.path("/boards/{id}").buildAndExpand(board.getId()).toUri();
       return ResponseEntity.created(uri).body(board);
    }

    @PutMapping("/{board_id}")
    public ResponseEntity<BoardResponse> updateBoard(@PathVariable(name = "board_id") Long boardId,@Valid @RequestBody EditBoardRequest request){
        var board = boardService.updateBoard(request,boardId);
        return ResponseEntity.ok(board);
    }

    @DeleteMapping("/{boerd_id}")
    public ResponseEntity<?> deleteBoard(@PathVariable(name = "boerd_id") Long boardId) {
        boardService.deleteBoard(boardId);
        return ResponseEntity.noContent().build();
    }
}
