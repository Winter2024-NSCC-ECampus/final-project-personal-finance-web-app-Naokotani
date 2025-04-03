package com.web.store.dto.purchase;

import lombok.Data;

import java.math.BigDecimal;

@Data
public class PurchaseResponse {
    private long productId;
    private String productName;
    private BigDecimal price;
    private int quantity;
}
