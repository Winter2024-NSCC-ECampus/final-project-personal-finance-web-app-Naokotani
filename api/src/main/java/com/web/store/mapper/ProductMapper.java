package com.web.store.mapper;

import com.web.store.dto.product.ProductDeleted;
import com.web.store.dto.product.ProductRequest;
import com.web.store.dto.product.ProductResponse;
import com.web.store.model.Product;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingConstants;

@Mapper(componentModel = MappingConstants.ComponentModel.SPRING)
public interface ProductMapper {
    @Mapping(source="product.name", target="name")
    @Mapping(source="product.description", target="description")
    Product newProductToProduct(ProductRequest product);
    @Mapping(source="product.category.name", target="category")
    @Mapping(target="stock", source="product.stock")
    ProductResponse productToProductResponse(Product product);
    ProductDeleted productToProductDeleted(Product product);
}
