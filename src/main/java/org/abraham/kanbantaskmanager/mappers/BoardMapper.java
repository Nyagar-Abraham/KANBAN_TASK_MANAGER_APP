package org.abraham.kanbantaskmanager.mappers;

import org.abraham.kanbantaskmanager.dtos.CreateBoardResponse;
import org.abraham.kanbantaskmanager.entities.Board;

public class BoardMapper {

    public static CreateBoardResponse toDto(Board board) {
        CreateBoardResponse response = new CreateBoardResponse();
        response.setName(board.getName());
        response.setId(board.getId());
        var columns = board.getColumns()
                .stream().map(BoardColumnMapper::toDto)
                .toList();
        response.setColumns(columns);
        return response;
    }
}
