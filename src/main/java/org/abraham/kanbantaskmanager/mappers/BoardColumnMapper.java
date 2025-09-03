package org.abraham.kanbantaskmanager.mappers;


import org.abraham.kanbantaskmanager.dtos.BoardColumnResponse;
import org.abraham.kanbantaskmanager.entities.BoardColumn;
import org.mapstruct.Mapper;
import org.mapstruct.MappingConstants;
import org.mapstruct.ReportingPolicy;

@Mapper(
        componentModel = MappingConstants.ComponentModel.SPRING,
        unmappedTargetPolicy = ReportingPolicy.IGNORE
)
public interface BoardColumnMapper {
    BoardColumnResponse  boardColumnToBoardColumnResponse(BoardColumn boardColumn);

//    public static BoardColumnResponse toDto(BoardColumn boardColumn) {
//        BoardColumnResponse boardColumnResponse = new BoardColumnResponse();
//        boardColumnResponse.setId(boardColumn.getId());
//        boardColumnResponse.setName(boardColumn.getName());
//        boardColumnResponse.setPosition(boardColumn.getPosition());
//        boardColumnResponse.setColor(boardColumn.getColor());
//        boardColumnResponse.setTaskLimit(boardColumn.getTaskLimit());
//        boardColumnResponse.setCreatedAt(boardColumn.getCreatedAt().toString());
//        boardColumnResponse.setUpdatedAt(boardColumn.getUpdatedAt().toString());
//        return boardColumnResponse;
//    }
}
