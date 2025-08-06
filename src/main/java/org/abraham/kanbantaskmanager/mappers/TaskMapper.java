package org.abraham.kanbantaskmanager.mappers;

import org.abraham.kanbantaskmanager.dtos.RestDtos.TaskResponse;
import org.abraham.kanbantaskmanager.entities.Task;

import java.util.stream.Collectors;

public class TaskMapper {
    public static TaskResponse toDto(Task task) {
        TaskResponse dto = new TaskResponse();
        dto.setId(task.getId());
        dto.setTitle(task.getTitle());
        dto.setDescription(task.getDescription());
        dto.setStatus(task.getStatus());
        dto.setSubtasks(task.getSubTasks().stream().map(SubTaskMapper::toDto).collect(Collectors.toSet()));
        return dto;
    }
}
