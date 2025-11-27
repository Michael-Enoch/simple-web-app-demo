package com.example.simple_web_app.service.Impl;

import com.example.simple_web_app.dto.ProductRequest;
import com.example.simple_web_app.dto.ProductResponse;
import com.example.simple_web_app.dto.UpdateProductRequest;
import com.example.simple_web_app.exception.ProductNotFoundException;
import com.example.simple_web_app.exception.business.DuplicateProductException;
import com.example.simple_web_app.exception.business.InvalidProductStateException;
import com.example.simple_web_app.mapper.ProductMapper;
import com.example.simple_web_app.model.Product;
import com.example.simple_web_app.repository.ProductRepo;
import com.example.simple_web_app.service.ProductService;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Transactional
public class ProductServiceImpl implements ProductService {
    private final ProductRepo repo;
    private final ProductMapper mapper;


    @Override
    public List<ProductResponse> getProducts() {
        return repo.findAll().stream()
                .map(mapper::toResponse)
                .collect(Collectors.toList());
    }

    @Override
    public ProductResponse getProductById(Long id) {
        Product product = repo.findById(id).orElseThrow(() -> new ProductNotFoundException(id));
        return mapper.toResponse(product);
    }

    @Override
    public ProductResponse createProduct(ProductRequest request) {
        if (repo.existsByProdName(request.prodName())){
            throw new DuplicateProductException(
                    "A product with the name " + request.prodName() + " already exists"
            );
        }
        validatePrice(request);
        Product product = mapper.toEntity(request);
        Product save = repo.save(product);
        return mapper.toResponse(save);
    }

    @Override
    public ProductResponse updateProduct(UpdateProductRequest request, Long id) {
        Product product = repo.findById(id).orElseThrow(()->
                new ProductNotFoundException(id));

        if (request.prodName() != null &&
                repo.existsByProdName(request.prodName()) &&
                !Objects.equals(product.getProdName(), request.prodName()))
        {

            throw new DuplicateProductException("A product with this name already exists");
        }

        validatePrice(request);

        mapper.updateProductFromRequest(request, product);

        Product updated = repo.save(product);
        return mapper.toResponse(updated);
    }

    private void validatePrice(ProductRequest request){
        if (request.price() != null && request.price() <= 0) {
            throw new InvalidProductStateException("Price must be greater than 0");
        }
    }

    private void validatePrice(UpdateProductRequest request){
        if (request.price() != null && request.price() <= 0) {
            throw new InvalidProductStateException("Price must be greater than 0");
        }
    }
}
