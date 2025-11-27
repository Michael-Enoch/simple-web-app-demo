package com.example.simple_web_app.exception;

import lombok.Getter;

@Getter
public class MethodArgumentNotValidException extends RuntimeException {
    private final String code;
    public MethodArgumentNotValidException(String message) {
        super(message);
        this.code = "VALIDATION_ERROR";
    }

    public MethodArgumentNotValidException(String message, String code) {
        super(message);
        this.code = code;
    }
}
