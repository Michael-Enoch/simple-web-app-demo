package com.example.simple_web_app.exception;

public class UnathorizedException extends RuntimeException{
    public UnathorizedException(String message) {
        super(message);
    }
}
