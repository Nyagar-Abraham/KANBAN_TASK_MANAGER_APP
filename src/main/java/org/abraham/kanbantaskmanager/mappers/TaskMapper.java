package org.abraham.kanbantaskmanager.mappers;

import org.abraham.kanbantaskmanager.dtos.CreateTaskResponse;
import org.abraham.kanbantaskmanager.entities.Task;

import java.util.stream.Collectors;

public class TaskMapper {
    public static CreateTaskResponse toDto(Task task) {
        CreateTaskResponse dto = new CreateTaskResponse();
        dto.setId(task.getId());
        dto.setTitle(task.getTitle());
        dto.setDescription(task.getDescription());
        dto.setStatus(task.getStatus());
        dto.setSubTasks(task.getSubTasks().stream().map(SubTaskMapper::toDto).collect(Collectors.toSet()));
        return dto;
    }
}
