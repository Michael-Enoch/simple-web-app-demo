package com.example.simple_web_app.exception;

import lombok.Getter;

@Getter
public class InternalServerErrorException extends RuntimeException {
    private final String code;
    public InternalServerErrorException(String message) {
        super(message);
        this.code = "INTERNAL_SERVER_ERROR";
    }

    public InternalServerErrorException(String message, String code) {
        super(message);
        this.code = code;
    }
}
