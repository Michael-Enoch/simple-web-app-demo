    package com.example.simple_web_app.mapper;

    import com.example.simple_web_app.dto.ProductRequest;
    import com.example.simple_web_app.dto.ProductResponse;
    import com.example.simple_web_app.dto.UpdateProductRequest;
    import com.example.simple_web_app.model.Product;
    import org.mapstruct.Mapper;
    import org.mapstruct.MappingTarget;

    @Mapper(componentModel = "spring")
    public interface ProductMapper {
        Product toEntity(ProductRequest dto);
        ProductResponse toResponse(Product entity);

        void updateProductFromRequest(UpdateProductRequest request, @MappingTarget Product entity);
    }
