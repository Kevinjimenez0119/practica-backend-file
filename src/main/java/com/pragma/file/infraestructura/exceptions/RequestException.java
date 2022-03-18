package com.pragma.file.infraestructura.exceptions;

import lombok.Data;
import lombok.EqualsAndHashCode;
import org.springframework.http.HttpStatus;

@EqualsAndHashCode(callSuper = true)
@Data
public class RequestException extends RuntimeException{

    private Integer code;

    public RequestException(Integer code, String message) {
        super(message);
        this.code = code;
    }
}
