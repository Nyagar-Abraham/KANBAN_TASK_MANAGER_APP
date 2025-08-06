package org.abraham.kanbantaskmanager.dtos.GraphqlDtos;

import lombok.Data;

import java.util.Set;

@Data
public class BoardResponse {
    private Long id;
    private String name;
    private Set<BoardColumnResponse> columns;
}
