package org.abraham.kanbantaskmanager.dtos;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;
import org.abraham.kanbantaskmanager.entities.TaskStatusAndColumnName;

import java.util.HashSet;
import java.util.Set;

@Data
public class CreateTaskRequest {
    @NotNull(message = "boardId is required")
    private Long boardId;
    @NotBlank(message = "title is required")
    private String title;
    private String description;
    @NotNull(message = "status is required")
    private TaskStatusAndColumnName status;
    private Set<String> subtasks = new HashSet<>();
}
