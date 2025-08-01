package org.abraham.kanbantaskmanager.dtos;

import lombok.Data;

@Data
public class Error {
    private int code;
    private String message;
}
