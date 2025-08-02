package org.abraham.kanbantaskmanager.dtos;

import lombok.Data;
import org.abraham.kanbantaskmanager.entities.TaskStatusAndColumnName;

import java.util.HashSet;
import java.util.Set;

@Data
public class TaskResponse {
    private Long id;
    private String title;
    private String description;
    private TaskStatusAndColumnName status;
    private Set<SubTaskResponse> subTasks = new HashSet<>();
}
