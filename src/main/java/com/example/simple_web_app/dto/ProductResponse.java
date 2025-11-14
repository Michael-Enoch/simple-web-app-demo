package com.example.simple_web_app.dto;

import lombok.Builder;

import java.time.LocalDateTime;

@Builder
public record ProductResponse(Long id, String prodName, Double price,
                              LocalDateTime createdAt, LocalDateTime updatedAt) {
}
