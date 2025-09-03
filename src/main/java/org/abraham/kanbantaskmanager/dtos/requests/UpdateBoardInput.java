package org.abraham.kanbantaskmanager.dtos.requests;

import jakarta.validation.constraints.Max;

import jakarta.validation.constraints.NotNull;
import lombok.Data;
import org.abraham.kanbantaskmanager.entities.TaskStatusAndColumnName;

import java.util.HashSet;
import java.util.Set;

@Data
public class UpdateBoardInput {
    private String name;
    private String description;
    private Set<TaskStatusAndColumnName> columns =  new HashSet<>();
    private String color;
}
