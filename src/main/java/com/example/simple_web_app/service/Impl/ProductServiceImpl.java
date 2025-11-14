package com.example.simple_web_app.service.Impl;

import com.example.simple_web_app.dto.ProductRequest;
import com.example.simple_web_app.dto.ProductResponse;
import com.example.simple_web_app.exception.ProductNotFoundException;
import com.example.simple_web_app.mapper.ProductMapper;
import com.example.simple_web_app.model.Product;
import com.example.simple_web_app.repository.ProductRepo;
import com.example.simple_web_app.service.ProductService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class ProductServiceImpl implements ProductService {
    private final ProductRepo repo;
    private final ProductMapper mapper;


    @Override
    public List<ProductResponse> getProducts() {
        return repo.findAll().stream()
                .map(mapper::toResponse)
                .toList();
    }

    @Override
    public ProductResponse getProductById(Long id) {
        Product product = repo.findById(id).orElseThrow(() -> new ProductNotFoundException(id));
        return mapper.toResponse(product);
    }


    @Override
    public ProductResponse createProduct(ProductRequest request) {
        Product product = mapper.toEntity(request);
        Product save = repo.save(product);
        return mapper.toResponse(save);
    }

}
