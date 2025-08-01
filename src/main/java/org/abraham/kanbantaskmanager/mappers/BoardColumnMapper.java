package org.abraham.kanbantaskmanager.mappers;

import org.abraham.kanbantaskmanager.dtos.BoardColumnResponse;
import org.abraham.kanbantaskmanager.entities.BoardColumn;

public class BoardColumnMapper {
    public static BoardColumnResponse toDto(BoardColumn boardColumn) {
        BoardColumnResponse boardColumnResponse = new BoardColumnResponse();
        boardColumnResponse.setName(boardColumn.getName());
        boardColumnResponse.setId(boardColumn.getId());
        return boardColumnResponse;
    }
}
