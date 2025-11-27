package com.example.simple_web_app.exception.business;

import com.example.simple_web_app.exception.BadRequestException;

public class InvalidProductStateException extends BadRequestException {
    public InvalidProductStateException(String message) {
        super(message);
    }
}
