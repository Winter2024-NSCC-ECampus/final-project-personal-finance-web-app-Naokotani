package com.web.store.controller;

import com.web.store.dto.product.ProductResponse;
import com.web.store.mapper.ProductMapper;
import com.web.store.model.Product;
import com.web.store.repository.ProductRepository;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/products")
public class ProductController {
    private final ProductRepository productRepository;
    private final ProductMapper productMapper;

    public ProductController(ProductRepository productRepository, ProductMapper productMapper) {
        this.productRepository = productRepository;
        this.productMapper = productMapper;
    }

    @GetMapping
    public ResponseEntity<List<ProductResponse>> getAllProduct() {
        List<Product> products = productRepository.findAll();
        List<ProductResponse> res = products.stream().map(productMapper::productToProductResponse).toList();
        return new ResponseEntity<>(res, HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<ProductResponse> getProductById(@PathVariable Long id) throws ResponseStatusException {
        productRepository.findById(id);
        return new ResponseEntity<>(productMapper.productToProductResponse(productRepository.findById(id)
                .orElseThrow(()-> new ResponseStatusException(HttpStatus.OK, "Product with id " + id + "not found"))), HttpStatus.OK);
    }
}
