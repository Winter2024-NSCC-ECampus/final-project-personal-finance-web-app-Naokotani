package com.web.store.controller.admin;

import com.web.store.dto.product.ProductDeleted;
import com.web.store.dto.product.ProductRequest;
import com.web.store.dto.product.ProductResponse;
import com.web.store.mapper.ProductMapper;
import com.web.store.model.Category;
import com.web.store.model.Product;
import com.web.store.repository.CategoryRepository;
import com.web.store.repository.ProductRepository;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;
import java.util.Set;

@RestController
@RequestMapping("/api/admin/products")
public class AdminProductController {
    private final ProductRepository productRepository;
    private final CategoryRepository categoryRepository;
    private final ProductMapper productMapper;

    public AdminProductController(ProductRepository productRepository, CategoryRepository categoryRepository, ProductMapper productMapper) {
        this.productRepository = productRepository;
        this.categoryRepository = categoryRepository;
        this.productMapper = productMapper;
    }

    @PostMapping
    public ResponseEntity<ProductResponse> addProduct(@RequestBody ProductRequest productRequest) throws ResponseStatusException {
        Category category = categoryRepository.findById(productRequest.getCategoryId())
                .orElseThrow(()-> new ResponseStatusException(HttpStatus.NOT_FOUND, "Category Not Found"));
        Product product = productMapper.newProductToProduct(productRequest);
        product.setCategory(category);
        product = productRepository.save(product);
        category.getProducts().add(product);
        categoryRepository.save(category);
        ProductResponse productResponse = productMapper.productToProductResponse(product);
        return new ResponseEntity<>(productResponse, HttpStatus.CREATED);
    }

    @PutMapping
    public ResponseEntity<ProductResponse> updateProduct(@RequestBody ProductRequest productRequest, @RequestParam Long productId) throws ResponseStatusException {
        Category category = categoryRepository.findById(productRequest.getCategoryId())
                .orElseThrow(()-> new ResponseStatusException(HttpStatus.NOT_FOUND, "Category Not Found"));
        Product oldProduct = productRepository.findById(productId).orElseThrow(()-> new ResponseStatusException(HttpStatus.NOT_FOUND, "Product Not Found"));
        Product updatedProduct = productMapper.newProductToProduct(productRequest);
        updatedProduct.setId(oldProduct.getId());
        updatedProduct.setCategory(category);
        category.getProducts().remove(oldProduct);
        category.getProducts().add(updatedProduct);
        updatedProduct = productRepository.save(updatedProduct);
        categoryRepository.save(category);
        return new ResponseEntity<>(productMapper.productToProductResponse(updatedProduct), HttpStatus.OK);
    }

    @GetMapping("/category")
    public ResponseEntity<List<ProductResponse>> getProductsByCategoryId(@RequestParam Long categoryId) {
        Category category = categoryRepository.findById(categoryId).orElseThrow(()-> new ResponseStatusException(HttpStatus.NOT_FOUND, "Category Not Found"));
        Set<Product> products = category.getProducts();
        List<ProductResponse> productResponses = products.stream().map(productMapper::productToProductResponse).toList();
        return new ResponseEntity<>(productResponses, HttpStatus.OK);
    }

    @DeleteMapping
    public ResponseEntity<ProductDeleted> deleteProductById(@RequestParam Long id) throws ResponseStatusException {
        Product product = productRepository.findById(id).orElseThrow(()-> new ResponseStatusException(HttpStatus.NOT_FOUND, "Product with id " + id + " not found"));
        Category category = product.getCategory();
        category.getProducts().remove(product);
        categoryRepository.save(category);
        productRepository.deleteById(id);
        return new ResponseEntity<>(productMapper.productToProductDeleted(product), HttpStatus.OK);
    }
}