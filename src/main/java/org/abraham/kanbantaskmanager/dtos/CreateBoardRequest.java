package org.abraham.kanbantaskmanager.dtos;

import jakarta.validation.constraints.NotBlank;
import lombok.Data;

import java.util.ArrayList;
import java.util.List;

@Data
public class CreateBoardRequest {
    @NotBlank(message = "Name is required!")
    private String name;
    private List<ColmnsEnum> columns =  new ArrayList<>();
}
