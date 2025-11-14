package com.example.simple_web_app.exception;

import lombok.Getter;

@Getter
public class ValidationException extends RuntimeException {
    private final String code;
    public ValidationException(String message) {
        super(message);
        this.code = "VALIDATION_ERROR";
    }

    public ValidationException(String message, String code) {
        super(message);
        this.code = code;
    }
}
