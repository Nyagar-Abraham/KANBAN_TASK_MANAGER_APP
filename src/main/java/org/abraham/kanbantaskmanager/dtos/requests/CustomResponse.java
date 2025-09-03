package org.abraham.kanbantaskmanager.dtos.requests;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor(staticName = "create")
@NoArgsConstructor
public class CustomResponse {
    private int code;
    private boolean success;
    private String message;
}
