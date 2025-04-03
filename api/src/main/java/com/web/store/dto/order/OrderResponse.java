package com.web.store.dto.order;

import com.web.store.dto.purchase.PurchaseResponse;
import lombok.Data;

import java.time.LocalDateTime;
import java.util.List;

@Data
public class OrderResponse {
    private Long id;
    private LocalDateTime purchaseTime;
    private List<PurchaseResponse> purchases;
    private Long userId;
    private String userName;

}
