package org.abraham.kanbantaskmanager.dtos;

import lombok.Data;
import org.abraham.kanbantaskmanager.entities.Status;

import java.util.HashSet;
import java.util.Set;

@Data
public class CreateTaskResponse {
    private Long id;
    private String title;
    private String description;
    private Status status;
    private Set<SubTaskResponse> subTasks = new HashSet<>();
}
