package com.web.store.dto.purchase;

import lombok.Data;

@Data
public class PurchaseRequest {
    private Long productId;
    private int quantity;
}
