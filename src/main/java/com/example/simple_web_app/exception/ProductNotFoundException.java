package com.example.simple_web_app.exception;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public class ProductNotFoundException extends RuntimeException{
    private final String code;
    public ProductNotFoundException(Long id) {
        super("Product with id " + id + " not found");
        this.code = "PRODUCT_NOT_FOUND";
    }

}
