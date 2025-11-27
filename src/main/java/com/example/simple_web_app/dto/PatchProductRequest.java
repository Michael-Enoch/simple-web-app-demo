package com.example.simple_web_app.dto;

import com.fasterxml.jackson.annotation.JsonMerge;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

public record PatchProductRequest(
        @JsonMerge
        @Size(max = 50, message = "Name must not exceed 50 characters")
        String prodName,

        @JsonMerge

        String description,
        @JsonMerge
        Double price
) {
}
