package org.abraham.kanbantaskmanager.dtos;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;
import org.abraham.kanbantaskmanager.entities.TaskStatusAndColumnName;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Data
public class CreateBoardRequest {
    @NotBlank(message = "Name is required!")
    @NotNull(message = "Name is required!")
    private String name;
    private Set<TaskStatusAndColumnName> columns =  new HashSet<>();
}
