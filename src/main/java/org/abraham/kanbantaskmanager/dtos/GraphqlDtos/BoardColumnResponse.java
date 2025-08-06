package org.abraham.kanbantaskmanager.dtos.GraphqlDtos;

import lombok.Data;
import org.abraham.kanbantaskmanager.entities.TaskStatusAndColumnName;

@Data
public class BoardColumnResponse {
    private Long id;
    private TaskStatusAndColumnName name;
    private int tasksCount;
}
