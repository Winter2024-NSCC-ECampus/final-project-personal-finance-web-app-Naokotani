package com.web.store.dto.order;

import com.web.store.dto.purchase.PurchaseRequest;
import lombok.Data;

import java.util.List;

@Data
public class OrderRequest {
    private List<PurchaseRequest> purchases;
}
