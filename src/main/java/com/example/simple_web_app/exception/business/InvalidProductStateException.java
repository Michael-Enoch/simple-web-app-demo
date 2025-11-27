package com.example.simple_web_app.exception.business;

public class InvalidProductStateException extends RuntimeException {
    public InvalidProductStateException(String message) {
        super(message);
    }
}
