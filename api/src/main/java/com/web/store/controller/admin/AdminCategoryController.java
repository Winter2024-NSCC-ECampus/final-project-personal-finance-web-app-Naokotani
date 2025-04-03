package com.web.store.controller.admin;

import com.web.store.dto.category.CategoryDeleted;
import com.web.store.dto.category.CategoryRequest;
import com.web.store.dto.category.CategoryResponse;
import com.web.store.mapper.CategoryMapper;
import com.web.store.model.Category;
import com.web.store.repository.CategoryRepository;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/admin/category")
public class AdminCategoryController {
    private final CategoryRepository categoryRepository;
    private final CategoryMapper categoryMapper;

    public AdminCategoryController(CategoryRepository categoryRepository, CategoryMapper categoryMapper) {
        this.categoryRepository = categoryRepository;
        this.categoryMapper = categoryMapper;
    }

    @PostMapping
    public ResponseEntity<CategoryResponse> newCategory(@RequestBody CategoryRequest categoryRequest) {
        Category category = categoryMapper.catetegoryResponseToCategory(categoryRequest);
        categoryRepository.save(category);
        CategoryResponse res = categoryMapper.categoryToCategoryResponse(category);
        return new ResponseEntity<>(res, HttpStatus.CREATED);
    }

    @PutMapping
    public ResponseEntity<CategoryResponse> updateCategory(@RequestBody CategoryRequest categoryRequest, @RequestParam Long id) {
        Category oldCategory = categoryRepository.findById(id).orElseThrow(()-> new ResponseStatusException(HttpStatus.NOT_FOUND, "Category with id " + id + " not found"));
        Category updatedCategory = categoryMapper.catetegoryResponseToCategory(categoryRequest);
        updatedCategory.setId(oldCategory.getId());
        updatedCategory = categoryRepository.save(updatedCategory);
        return new ResponseEntity<>(categoryMapper.categoryToCategoryResponse(updatedCategory), HttpStatus.OK);
    }

    @DeleteMapping
    public ResponseEntity<CategoryDeleted> deleteCategory(@RequestParam Long id) {
        CategoryDeleted res = categoryMapper.categoryToCategoryDeleted(categoryRepository.findById(id)
                .orElseThrow(() -> new  ResponseStatusException(HttpStatus.NOT_FOUND, "Category with id " + id + " not found")));
        categoryRepository.deleteById(id);
        return new ResponseEntity<>(res, HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<CategoryResponse> getCategoryById(@PathVariable Long id) {
        Category category = categoryRepository.findById(id).orElseThrow(()-> new ResponseStatusException(HttpStatus.NOT_FOUND, "Category with id " + id + " not found"));
        CategoryResponse res = categoryMapper.categoryToCategoryResponse(category);
        return new ResponseEntity<>(res, HttpStatus.OK);
    }

    @GetMapping
    public ResponseEntity<List<CategoryResponse>> getAllCategory() {
        List<CategoryResponse> categories = categoryRepository.findAll()
                .stream().map(categoryMapper::categoryToCategoryResponse).collect(Collectors.toList());
        return new ResponseEntity<>(categories, HttpStatus.OK);
    }
}
