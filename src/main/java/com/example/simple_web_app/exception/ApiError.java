package com.example.simple_web_app.exception;

public record ApiError(int status, String message) {
}
