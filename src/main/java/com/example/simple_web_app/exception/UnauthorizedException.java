package com.example.simple_web_app.exception;

import lombok.Getter;

@Getter
public class UnauthorizedException extends RuntimeException{
    private final String code;
    public UnauthorizedException(String message) {
        super(message);
        this.code = "INVALID_ACCESS";
    }

    public UnauthorizedException(String message, String code) {
        super(message);
        this.code = code;
    }
}
