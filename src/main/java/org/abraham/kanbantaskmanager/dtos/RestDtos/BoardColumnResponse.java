package org.abraham.kanbantaskmanager.dtos.RestDtos;

import lombok.Data;
import org.abraham.kanbantaskmanager.entities.TaskStatusAndColumnName;

@Data
public class BoardColumnResponse {
    private Long id;
    private TaskStatusAndColumnName name;
//    private List<TaskResponse> tasks = new ArrayList<>();
}
