package com.Ratatouille23.Server.handler.entity;

import lombok.Data;
import org.springframework.http.HttpStatus;

@Data
public class RatatouilleException {
    private final String message;
    private final Throwable throwable;
    private final HttpStatus httpStatus;

}
