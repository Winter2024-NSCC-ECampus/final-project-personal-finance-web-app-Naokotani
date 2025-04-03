package com.web.store.dto.product;

import lombok.Data;

import java.math.BigDecimal;

@Data
public class ProductResponse {
    private Long id;
    private String name;
    private String description;
    private String category;
    private int stock;
    private BigDecimal price;
}
