package com.web.store.controller.admin;

import com.web.store.dto.order.OrderDeleted;
import com.web.store.dto.order.OrderResponse;
import com.web.store.mapper.OrderMapper;
import com.web.store.model.Order;
import com.web.store.model.Purchase;
import com.web.store.model.User;
import com.web.store.repository.OrderRepository;
import com.web.store.repository.PurchaseRepository;
import com.web.store.repository.UserRepository;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.math.BigDecimal;
import java.util.List;

@RestController
@RequestMapping("/api/admin/order")
public class AdminOrderController {
    private final OrderRepository orderRepository;
    private final OrderMapper orderMapper;
    private final UserRepository userRepository;
    private final PurchaseRepository purchaseRepository;

    public AdminOrderController(OrderRepository orderRepository, OrderMapper orderMapper, UserRepository userRepository, PurchaseRepository purchaseRepository) {
        this.orderRepository = orderRepository;
        this.orderMapper = orderMapper;
        this.userRepository = userRepository;
        this.purchaseRepository = purchaseRepository;
    }

    @GetMapping("/{id}")
    public ResponseEntity<OrderResponse> findOrderById(@PathVariable Long id){
        Order order = orderRepository.findById(id).orElseThrow(()-> new ResponseStatusException(HttpStatus.NOT_FOUND, "Order with id " + id + " not found"));
        OrderResponse res = orderMapper.orderToOrderResponse(order);
        return new ResponseEntity<>(res, HttpStatus.OK);
    }

    @GetMapping
    public ResponseEntity<List<OrderResponse>> findAllOrders(){
        List<OrderResponse> orders = orderRepository.findAll().stream().map(orderMapper::orderToOrderResponse).toList();
        return new ResponseEntity<>(orders, HttpStatus.OK);
    }

    @GetMapping("/{userId}")
    public ResponseEntity<List<OrderResponse>> findAllOrdersByUserId(@PathVariable Long userId){
        User user = userRepository.findById(userId).orElseThrow(()-> new ResponseStatusException(HttpStatus.NOT_FOUND, "User with id " + userId + " not found"));
        return new ResponseEntity<>(user.getOrders().stream().map(orderMapper::orderToOrderResponse).toList(), HttpStatus.OK);
    }

    @DeleteMapping
    public ResponseEntity<OrderDeleted> cancelOrderById(@RequestParam Long id){
        Order order = orderRepository.findById(id).orElseThrow(()-> new ResponseStatusException(HttpStatus.NOT_FOUND, "Order with id " + id + " not found"));
        orderRepository.deleteById(id);
        return new ResponseEntity<>(orderMapper.orderToOrderDeleted(order), HttpStatus.OK);
    }

    @PutMapping
    public ResponseEntity<OrderResponse> updateOrder(@RequestParam Long purchaseId, @RequestParam Long orderId, @RequestParam BigDecimal price, @RequestParam BigDecimal quantity){
        Order order = orderRepository.findById(orderId).orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Order with id " + orderId + " not found")) ;
        Purchase purchase = purchaseRepository.findById(purchaseId)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Purchase with id " + purchaseId + " not found"));
        if(quantity.compareTo(BigDecimal.ZERO) < 0){
            return  new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        } else if(price.compareTo(BigDecimal.ZERO) == 0) {
            purchaseRepository.deleteById(purchaseId);
            order.setTotalPrice(order.getTotalPrice().subtract(price));
        } else {
            order.setTotalPrice(order.getTotalPrice().subtract(price));
            order.setTotalPrice(purchase.getPrice().multiply(quantity));
        }
        orderRepository.save(order);
        return new ResponseEntity<>(orderMapper.orderToOrderResponse(order), HttpStatus.OK);
    }
}
