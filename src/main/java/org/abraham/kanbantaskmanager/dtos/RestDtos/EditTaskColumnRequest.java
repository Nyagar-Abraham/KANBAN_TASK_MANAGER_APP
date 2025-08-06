package org.abraham.kanbantaskmanager.dtos.RestDtos;

import jakarta.validation.constraints.NotNull;
import lombok.Data;
import org.abraham.kanbantaskmanager.entities.TaskStatusAndColumnName;

@Data
public class EditTaskColumnRequest {
    @NotNull(message = "status is required")
    private TaskStatusAndColumnName status;
}
