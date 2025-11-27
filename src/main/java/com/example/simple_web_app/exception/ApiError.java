package com.example.simple_web_app.exception;

import lombok.Builder;

import java.time.LocalDateTime;

@Builder
public record ApiError(
        LocalDateTime timestamp,
        int status,
        String error,
        String message,
        String path,
        String code,
        Object details
) {
}
