package com.example.simple_web_app.exception;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
public class BadRequestException extends RuntimeException{

    private final String code;
    public BadRequestException(String message) {
        super(message);
        this.code = "BAD_REQUEST";
    }

    public BadRequestException(String message, String code) {
        super(message);
        this.code = code;
    }
}
