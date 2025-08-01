package org.abraham.kanbantaskmanager.controller;

import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.abraham.kanbantaskmanager.Exceptions.DuplicateNameException;
import org.abraham.kanbantaskmanager.dtos.CreateBoardRequest;
import org.abraham.kanbantaskmanager.dtos.CreateBoardResponse;
import org.abraham.kanbantaskmanager.dtos.Error;
import org.abraham.kanbantaskmanager.service.BoardService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.util.UriComponentsBuilder;

@RestController
@RequestMapping("/boards")
@AllArgsConstructor
public class BoardController {
    private final BoardService boardService;

    @PostMapping()
    public ResponseEntity<CreateBoardResponse> createBoard(@Valid @RequestBody CreateBoardRequest createBoardRequest, UriComponentsBuilder uriBuilder) {
       var board =  boardService.createBoard(createBoardRequest);
       var uri = uriBuilder.path("/boards/{id}").buildAndExpand(board.getId()).toUri();
       return ResponseEntity.created(uri).body(board);
    }
}
