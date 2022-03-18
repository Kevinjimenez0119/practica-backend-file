package com.pragma.file.infraestructura.exceptions;

import lombok.Data;
import lombok.EqualsAndHashCode;
import org.springframework.http.HttpStatus;

@EqualsAndHashCode(callSuper = true)
@Data
public class LogicException extends RuntimeException{

    private Integer code;

    public LogicException(Integer code, String message) {
        super(message);
        this.code = code;
    }
}
