package org.abraham.kanbantaskmanager.dtos.requests;

import jakarta.validation.constraints.FutureOrPresent;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;
import org.abraham.kanbantaskmanager.dtos.TaskPriority;
import org.abraham.kanbantaskmanager.entities.TaskStatusAndColumnName;
import org.springframework.format.annotation.DateTimeFormat;

import java.time.LocalDate;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Data
public class CreateTaskRequest {
    @NotNull(message = "boardId is required")
    private Long boardId;

    @NotNull(message = "createBy is required")
    private Long createdBy;

    @NotBlank(message = "title is required")
    private String title;

    private String description;

    @NotNull(message = "status is required")
    private TaskStatusAndColumnName status;

    @NotNull(message = "priority is required")
    private TaskPriority priority;

    @NotNull(message = "dueDate is required")
    @DateTimeFormat(pattern = "yyyy-MM-dd" )
    @FutureOrPresent(message = "dueDate date must be in the present or future")
    private LocalDate dueDate;

    @NotNull(message = "assignedTo is required")
    private Long assignedTo;

    private int estimatedHours;

    @NotNull(message = "tags is required")
    private List<String> tags;

    private Set<CreateSubtaskRequest> subtasks = new HashSet<>();
}
//boardId:ID!
//createdBy: Int!
//title:String!
//description:String
//status:TaskStatusAndColumnName!
//priority: TaskPriority!
//dueDate: Date
//assignedTo: Int!
//estimatedHours: Int
//tags: String
//subtasks: [CreateSubtaskRequest!]!
