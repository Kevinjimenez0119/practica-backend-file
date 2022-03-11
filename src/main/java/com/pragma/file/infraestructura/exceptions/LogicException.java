package com.pragma.file.infraestructura.exceptions;

import lombok.Data;
import org.springframework.http.HttpStatus;

@Data
public class LogicException extends RuntimeException{

    private String code;

    private HttpStatus status;

    public LogicException(String code, HttpStatus status, String message) {
        super(message);
        this.code = code;
        this.status = status;
    }
}
