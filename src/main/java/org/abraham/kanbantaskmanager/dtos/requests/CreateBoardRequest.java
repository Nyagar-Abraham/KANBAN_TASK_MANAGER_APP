package org.abraham.kanbantaskmanager.dtos.requests;

import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;
import org.abraham.kanbantaskmanager.entities.TaskStatusAndColumnName;

import java.util.HashSet;
import java.util.Set;

@Data
public class CreateBoardRequest {
    @NotBlank(message = "Name is required!")
    @NotNull(message = "Name is required!")
    private String name;
    private String description;
    private Set<TaskStatusAndColumnName> columns =  new HashSet<>();
    @NotNull(message = "createdBy is required ")
    @Max(value = 0L, message = "createdBy id cannot be negative" )
    private Long createdBy;
    private String color;
}

//input CreateBoardRequest{
//name:String!
//description: String!
//columns:[TaskStatusAndColumnName]
//createdBy: Int!
//color:  String!
//        }
//
