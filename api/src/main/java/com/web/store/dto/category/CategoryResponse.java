package com.web.store.dto.category;

import lombok.Data;

import java.math.BigDecimal;

@Data
public class CategoryResponse {
    private Long id;
    private String name;
    private String description;
}
