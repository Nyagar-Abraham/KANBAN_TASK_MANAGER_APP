package org.abraham.kanbantaskmanager.dtos.RestDtos;

import lombok.Data;


import java.util.HashSet;
import java.util.Set;

@Data
public class BoardResponse {
    private Long id;
    private String name;
    private Set<BoardColumnResponse> columns = new HashSet<>();
}
