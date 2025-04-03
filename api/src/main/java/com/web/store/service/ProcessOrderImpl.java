package com.web.store.service;

import com.web.store.dto.order.OrderRequest;
import com.web.store.dto.order.OrderResponse;
import com.web.store.dto.purchase.PurchaseRequest;
import com.web.store.mapper.OrderMapper;
import com.web.store.model.Order;
import com.web.store.model.Product;
import com.web.store.model.Purchase;
import com.web.store.model.User;
import com.web.store.repository.OrderRepository;
import com.web.store.repository.ProductRepository;
import com.web.store.repository.PurchaseRepository;
import com.web.store.repository.UserRepository;
import jakarta.transaction.Transactional;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.List;

@Service
public class ProcessOrderImpl implements ProcessOrderService {
    private final UserRepository userRepository;
    private final ProductRepository productRepository;
    private final OrderRepository orderRepository;
    private final PurchaseRepository purchaseRepository;
    private final OrderMapper orderMapper;

    public ProcessOrderImpl(UserRepository userRepository, ProductRepository productRepository, OrderRepository orderRepository, PurchaseRepository purchaseRepository, OrderMapper orderMapper) {
        this.userRepository = userRepository;
        this.productRepository = productRepository;
        this.orderRepository = orderRepository;
        this.purchaseRepository = purchaseRepository;
        this.orderMapper = orderMapper;
    }

    @Override
    @Transactional
    public OrderResponse processOrder(OrderRequest orderRequest) throws ResponseStatusException {
        User user = getCurrentUser();
        List<Purchase> purchases = orderRequest.getPurchases().stream()
                .map(this::purchaseRequestToPurchase).toList();
        List<Product> products = updateStock(purchases);
        Order order = new Order();
        order.setPurchaseTime(LocalDateTime.now());
        order.setPurchases(new HashSet<>(purchases));
        order.setTotalPrice(order.getPurchases().stream().map(Purchase::getPrice).reduce(BigDecimal.ZERO, BigDecimal::add));
        order.setUser(user);
        order.getPurchases().forEach(purchase ->
            purchase.setPrice(purchase.getPrice().multiply(BigDecimal.valueOf(purchase.getQuantity()))));
        user.getOrders().add(order);
        order.getPurchases().forEach( p -> p.setOrder(order));
        purchaseRepository.saveAll(purchases);
        productRepository.saveAll(products);
        orderRepository.save(order);
        userRepository.save(user);
        return orderMapper.orderToOrderResponse(order);
    }

    private List<Product> updateStock(List<Purchase> purchases){
        return purchases.stream().map(purchase -> {
            Product product = productRepository.findById(purchase.getProduct().getId())
                    .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND));
            if(product.getStock()  < purchase.getQuantity() || purchase.getQuantity() < 1){
                throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Product stock less than quantity");
            }
            product.setStock(product.getStock() - purchase.getQuantity());
            product.getPurchases().add(purchase);
            return product;
        }).toList();
    }

    private User getCurrentUser() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String email = authentication.getName();
        return userRepository.findByEmail(email);
    }

    private Purchase purchaseRequestToPurchase(PurchaseRequest purchaseRequest){
        Purchase purchase = new Purchase();
        Product product = productRepository.findById(purchaseRequest.getProductId())
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Product with id " + purchaseRequest.getProductId() + " not found"));
        purchase.setProduct(product);
        purchase.setQuantity(purchaseRequest.getQuantity());
        purchase.setPrice(product.getPrice().multiply(BigDecimal.valueOf(purchaseRequest.getQuantity())));
        return purchase;
    }
}
