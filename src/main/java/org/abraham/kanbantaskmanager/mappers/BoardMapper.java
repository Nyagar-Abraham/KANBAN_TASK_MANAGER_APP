package org.abraham.kanbantaskmanager.mappers;


import org.abraham.kanbantaskmanager.dtos.BoardResponse;
import org.abraham.kanbantaskmanager.entities.Board;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingConstants;
import org.mapstruct.ReportingPolicy;


@Mapper(
        componentModel = MappingConstants.ComponentModel.SPRING,
        uses = {BoardColumnMapper.class, UserMapper.class},
        unmappedTargetPolicy = ReportingPolicy.IGNORE
)
public interface BoardMapper {

//    @Mapping(source = "columns", target = "columns")
//    @Mapping()
    BoardResponse boardToBoardResponse(Board board);

}
