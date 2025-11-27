package com.example.simple_web_app.dto;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Builder;

@Builder
public record ProductRequest(

        @NotBlank(message = "Name cannot be blank")
        @Size(max = 50, message = "Name must not exceed 50 characters")
        String prodName,

        @NotBlank(message = "Description cannot be blank")
        @Size(max = 200, message = "Description must not exceed 200 characters")
        String description,
        @NotNull(message = "Price cannot be null")
        @Min(value = 1, message = "Price must be at least 1")
        Double price
) {
}
