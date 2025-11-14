package com.example.simple_web_app.service;

import com.example.simple_web_app.dto.ProductRequest;
import com.example.simple_web_app.dto.ProductResponse;
import com.example.simple_web_app.model.Product;

import java.util.List;

public interface ProductService {
    List<ProductResponse> getProducts();
    ProductResponse getProductById(Long id);
    ProductResponse createProduct(ProductRequest request);

}
