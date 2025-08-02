package org.abraham.kanbantaskmanager.mappers;

import org.abraham.kanbantaskmanager.dtos.BoardResponse;
import org.abraham.kanbantaskmanager.entities.Board;

import java.util.stream.Collectors;

public class BoardMapper {

    public static BoardResponse toDto(Board board) {
        BoardResponse response = new BoardResponse();
        response.setName(board.getName());
        response.setId(board.getId());
        var columns = board.getColumns()
                .stream().map(BoardColumnMapper::toDto)
                .collect(Collectors.toSet());
        response.setColumns(columns);
        return response;
    }
}
