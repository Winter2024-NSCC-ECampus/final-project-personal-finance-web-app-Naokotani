package com.web.store.controller.admin;

import com.web.store.dto.stock.StockResponse;
import com.web.store.mapper.StockMapper;
import com.web.store.model.Product;
import com.web.store.repository.ProductRepository;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

@RestController
@RequestMapping("/api/admin/stock")
public class AdminStockController {
    private final ProductRepository productRepository;
    private final StockMapper stockMapper;

    public AdminStockController(ProductRepository productRepository, StockMapper stockMapper) {
        this.productRepository = productRepository;
        this.stockMapper = stockMapper;
    }

    @GetMapping
    public ResponseEntity<StockResponse> getStockByProductId(@RequestParam Long productId){
        Product product = productRepository.findById(productId)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Product with id :" + productId + " not found"));
        StockResponse res = stockMapper.productToStockReponse(product);
        return new ResponseEntity<>(res, HttpStatus.OK);
    }

    @PutMapping
    public ResponseEntity<StockResponse> updateStockByProductId(@RequestParam Long productId,@RequestParam Integer quantity){
        Product product = productRepository.findById(productId).orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Product with id :" + productId + " not found"));
        product.setStock(quantity);
        productRepository.save(product);
        StockResponse res = stockMapper.productToStockReponse(product);
        return new ResponseEntity<>(res, HttpStatus.OK);
    }
}
