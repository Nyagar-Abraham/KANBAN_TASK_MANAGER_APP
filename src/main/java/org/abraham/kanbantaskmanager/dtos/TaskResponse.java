package org.abraham.kanbantaskmanager.dtos;

import lombok.Data;
import org.abraham.kanbantaskmanager.entities.TaskStatusAndColumnName;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Date;
import java.util.List;
import java.util.Set;

@Data
public class TaskResponse {
    private Long id;
    private String title;
    private String description;
    private TaskStatusAndColumnName status;
    private int position;
    private TaskPriority priority;
    private LocalDate dueDate;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
    private UserResponse assignedTo;
    private UserResponse createdBy;
    private List<String> tags;
    private Integer estimatedHours;
    private String attachmentUrl;
    private Set<SubTaskResponse> subtasks;
}
