package com.web.store.dto.product;

import lombok.Data;

import java.math.BigDecimal;

@Data
public class ProductRequest {
    private String name;
    private String description;
    private Long categoryId;
    private BigDecimal price;
}
