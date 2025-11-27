package com.example.simple_web_app.exception.business;

import com.example.simple_web_app.exception.BadRequestException;

public class DuplicateProductException extends BadRequestException {
    private final String code;
    public DuplicateProductException(String message) {
        super(message);
        this.code = "DUPLICATE_PRODUCT";
    }

    public DuplicateProductException(String message, String code) {
        super(message);
        this.code = code;
    }
}
