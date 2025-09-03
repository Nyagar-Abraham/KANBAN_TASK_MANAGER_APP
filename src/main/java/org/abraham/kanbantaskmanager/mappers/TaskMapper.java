package org.abraham.kanbantaskmanager.mappers;


import org.abraham.kanbantaskmanager.dtos.TaskResponse;
import org.abraham.kanbantaskmanager.entities.Task;
import org.mapstruct.Mapper;
import org.mapstruct.MappingConstants;
import org.mapstruct.ReportingPolicy;

import java.util.stream.Collectors;

@Mapper(
        componentModel = MappingConstants.ComponentModel.SPRING,
        uses = {BoardColumnMapper.class, SubTaskMapper.class, UserMapper.class},
        unmappedTargetPolicy = ReportingPolicy.IGNORE
)
public interface TaskMapper {

    TaskResponse taskToTaskResponse(Task task);

//    public static TaskResponse toDto(Task task) {
//        TaskResponse dto = new TaskResponse();
//        dto.setId(task.getId());
//        dto.setTitle(task.getTitle());
//        dto.setDescription(task.getDescription());
//        dto.setStatus(task.getStatus());
//        dto.setSubtasks(task.getSubTasks().stream().map(SubTaskMapper::toDto).collect(Collectors.toSet()));
//        return dto;
//    }
}
