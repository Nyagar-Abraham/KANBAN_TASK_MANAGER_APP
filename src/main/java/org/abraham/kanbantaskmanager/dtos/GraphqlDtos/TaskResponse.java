package org.abraham.kanbantaskmanager.dtos.GraphqlDtos;

import lombok.Data;
import org.abraham.kanbantaskmanager.entities.TaskStatusAndColumnName;

@Data
public class TaskResponse {
    private Long id;
    private String title;
    private String description;
    private TaskStatusAndColumnName status;
}
