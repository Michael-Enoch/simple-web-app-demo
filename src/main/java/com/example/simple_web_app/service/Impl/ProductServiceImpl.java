package com.example.simple_web_app.service.Impl;

import com.example.simple_web_app.dto.PatchProductRequest;
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
import java.util.Optional;
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
        // Validate prodName
        Optional.ofNullable(request.prodName())
                .filter(String::isBlank)
                .ifPresent(name -> { throw new InvalidProductStateException("Product name cannot be blank"); });

        Optional.ofNullable(request.prodName())
                .filter(repo::existsByProdName)
                .ifPresent(name -> { throw new DuplicateProductException("A product with this name already exists"); });

        validatePrice(request);
        Product product = mapper.toEntity(request);
        Product save = repo.save(product);
        return mapper.toResponse(save);
    }

    @Override
    public ProductResponse updateProduct(UpdateProductRequest request, Long id) {
        Product product = repo.findById(id).orElseThrow(()->
                new ProductNotFoundException(id));

        // Validate prodName
        Optional.ofNullable(request.prodName())
                .filter(String::isBlank)
                .ifPresent(name -> { throw new InvalidProductStateException("Product name cannot be blank"); });

        Optional.ofNullable(request.prodName())
                .filter(name -> !name.equals(product.getProdName()) && repo.existsByProdName(name))
                .ifPresent(name -> { throw new DuplicateProductException("A product with this name already exists"); });

        validatePrice(request);

        mapper.updateProductFromRequest(request, product);

        Product updated = repo.save(product);
        return mapper.toResponse(updated);
    }

    @Override
    public ProductResponse patchProduct(PatchProductRequest request, Long id) {
        Product product = repo.findById(id)
                .orElseThrow(() -> new ProductNotFoundException(id));

        // Validate prodName: reject empty strings and duplicates
        Optional.ofNullable(request.prodName())
                .filter(String::isBlank)
                .ifPresent(name -> { throw new InvalidProductStateException("Product name cannot be blank"); });

        Optional.ofNullable(request.prodName())
                .filter(name -> !name.equals(product.getProdName()) && repo.existsByProdName(name))
                .ifPresent(name -> { throw new DuplicateProductException("A product with this name already exists"); });

        // Validate description: reject empty strings
        Optional.ofNullable(request.description())
                .filter(String::isBlank)
                .ifPresent(d -> { throw new InvalidProductStateException("Description cannot be blank"); });

       validatePrice(request);

        mapper.patchProductFromRequest(request, product);

        Product updated = repo.save(product);

        return mapper.toResponse(updated);
    }

    private void validatePrice(PatchProductRequest request) {
        Optional.ofNullable(request.price())
                .filter(p -> p <= 0)
                .ifPresent(p -> { throw new InvalidProductStateException("Price must be greater than 0"); });
    }

    private void validatePrice(ProductRequest request){
        Optional.ofNullable(request.price())
                .filter(p -> p <= 0)
                .ifPresent(p -> { throw new InvalidProductStateException("Price must be greater than 0"); });
    }

    private void validatePrice(UpdateProductRequest request){
        Optional.ofNullable(request.price())
                .filter(p -> p <= 0)
                .ifPresent(p -> { throw new InvalidProductStateException("Price must be greater than 0"); });
    }
}
