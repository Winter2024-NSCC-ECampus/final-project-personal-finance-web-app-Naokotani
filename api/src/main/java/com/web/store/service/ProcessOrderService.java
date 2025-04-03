package com.web.store.service;

import com.web.store.dto.order.OrderRequest;
import com.web.store.dto.order.OrderResponse;

public interface ProcessOrderService {
    OrderResponse processOrder(OrderRequest orderRequest);
}
