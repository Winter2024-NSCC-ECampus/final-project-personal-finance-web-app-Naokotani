package com.web.store.mapper;

import com.web.store.dto.stock.StockResponse;
import com.web.store.model.Product;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingConstants;

@Mapper(componentModel = MappingConstants.ComponentModel.SPRING)
public interface StockMapper {

    @Mapping(target="name", source="product.name")
    @Mapping(target="id", source="product.id")
    @Mapping(target="quantity", source="product.stock")
    StockResponse productToStockReponse(Product product);
}
