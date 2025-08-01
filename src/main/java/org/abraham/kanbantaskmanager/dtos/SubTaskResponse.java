package org.abraham.kanbantaskmanager.dtos;

import lombok.Data;

@Data
public class SubTaskResponse {
    private Long id;
    private String title;
    private Boolean isCompleted;
}
