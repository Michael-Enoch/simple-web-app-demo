package com.example.simple_web_app.service;

import com.example.simple_web_app.dto.PatchProductRequest;
import com.example.simple_web_app.dto.ProductRequest;
import com.example.simple_web_app.dto.ProductResponse;
import com.example.simple_web_app.dto.UpdateProductRequest;

import java.util.List;

public interface ProductService {
    List<ProductResponse> getProducts();
    ProductResponse getProductById(Long id);
    ProductResponse createProduct(ProductRequest request);
    ProductResponse updateProduct(UpdateProductRequest request, Long id);

    ProductResponse patchProduct(PatchProductRequest request, Long id);
}
