package com.web.store.controller.user;

import com.web.store.dto.order.OrderRequest;
import com.web.store.dto.order.OrderResponse;
import com.web.store.service.ProcessOrderService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;


@RestController
@RequestMapping("/api/user/orders")
public class UserOrderController {
    private final ProcessOrderService processOrderService;

    public UserOrderController(ProcessOrderService processOrderService) {
        this.processOrderService = processOrderService;
    }

    @PostMapping
    public ResponseEntity<OrderResponse> placeOrder(@RequestBody OrderRequest orderRequest) {
        OrderResponse res = processOrderService.processOrder(orderRequest);
        return new ResponseEntity<>(res, HttpStatus.OK);
    }
}
