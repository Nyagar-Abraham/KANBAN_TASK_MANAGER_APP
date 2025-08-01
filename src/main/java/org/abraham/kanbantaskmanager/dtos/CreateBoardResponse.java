package org.abraham.kanbantaskmanager.dtos;

import lombok.Data;

import java.util.ArrayList;
import java.util.List;

@Data
public class CreateBoardResponse {
    private Long id;
    private String name;
    private List<BoardColumnResponse> columns = new ArrayList<>();
}
