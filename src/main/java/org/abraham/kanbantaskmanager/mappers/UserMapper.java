package org.abraham.kanbantaskmanager.mappers;

import org.abraham.kanbantaskmanager.dtos.UserResponse;
import org.abraham.kanbantaskmanager.entities.User;
import org.mapstruct.Mapper;
import org.mapstruct.MappingConstants;
import org.mapstruct.ReportingPolicy;

@Mapper(
        componentModel = MappingConstants.ComponentModel.SPRING,
//        uses = {BoardColumnMapper.class,},
        unmappedTargetPolicy = ReportingPolicy.IGNORE
)
public interface UserMapper {
    UserResponse userToUserResponse(User user);
}
