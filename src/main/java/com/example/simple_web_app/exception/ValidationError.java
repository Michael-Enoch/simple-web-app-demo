package com.example.simple_web_app.exception;

import lombok.Builder;

@Builder
public record ValidationError(String field, Object rejectedValue, String message) {
}
