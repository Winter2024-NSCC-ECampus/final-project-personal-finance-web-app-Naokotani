package com.web.store.dto.stock;

import lombok.Data;

@Data
public class StockResponse {
    private Long id;
    private String name;
    private int quantity;
}
