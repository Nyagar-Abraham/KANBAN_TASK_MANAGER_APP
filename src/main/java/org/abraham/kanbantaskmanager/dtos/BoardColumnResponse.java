package org.abraham.kanbantaskmanager.dtos;

import lombok.Data;
import org.abraham.kanbantaskmanager.entities.BoardColumn;

import java.util.ArrayList;
import java.util.List;

@Data
public class BoardColumnResponse {
    private Long id;
    private String name;
//    private List<String> tasks = new ArrayList<>();
}
