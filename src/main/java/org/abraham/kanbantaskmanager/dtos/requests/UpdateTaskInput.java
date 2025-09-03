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
public class UpdateTaskInput {
    private String title;
    private String description;
    private TaskStatusAndColumnName status;
    private TaskPriority priority;
    private LocalDate dueDate;
    private Long assignedTo;
    private Integer estimatedHours;
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
