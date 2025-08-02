package org.abraham.kanbantaskmanager.dtos;

import lombok.Data;
import org.abraham.kanbantaskmanager.entities.BoardColumn;
import org.abraham.kanbantaskmanager.entities.TaskStatusAndColumnName;

import java.util.ArrayList;
import java.util.List;

@Data
public class BoardColumnResponse {
    private Long id;
    private TaskStatusAndColumnName name;
//    private List<TaskResponse> tasks = new ArrayList<>();
}
