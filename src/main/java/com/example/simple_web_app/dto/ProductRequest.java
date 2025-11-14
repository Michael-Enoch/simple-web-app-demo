package com.example.simple_web_app.dto;

import lombok.Builder;

@Builder
public record ProductRequest(String prodName, Double price) {
}
