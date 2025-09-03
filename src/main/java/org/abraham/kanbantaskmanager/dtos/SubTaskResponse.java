package org.abraham.kanbantaskmanager.dtos;



import lombok.Data;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Data
public class SubTaskResponse {
    private Long id;
    private String title;
    private Boolean isCompleted;
    private int position;
    private String description;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
    private LocalDate dueDate;
    private UserResponse assignedTo;
}
