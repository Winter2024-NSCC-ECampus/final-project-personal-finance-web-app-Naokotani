package com.web.store.dto.order;

import lombok.Data;

@Data
public class OrderDeleted {
    private Long orderId;
    private Long userId;
    private String msg = "Order cancelled successfully";
}
