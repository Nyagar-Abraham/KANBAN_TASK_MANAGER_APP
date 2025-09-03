package org.abraham.kanbantaskmanager.dtos;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.abraham.kanbantaskmanager.entities.TaskStatusAndColumnName;

import java.time.LocalDateTime;

@Data
@AllArgsConstructor(staticName = "create")
@NoArgsConstructor
@JsonIgnoreProperties(ignoreUnknown = true)
public class BoardColumnResponse {
    private Long id;
    private TaskStatusAndColumnName name;
    private int position;
    private String color;
    private int taskLimit;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
//    private int tasksCount;
}
