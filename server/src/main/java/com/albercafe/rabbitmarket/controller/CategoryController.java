package com.albercafe.rabbitmarket.controller;

import com.albercafe.rabbitmarket.dto.CategoryRequest;
import com.albercafe.rabbitmarket.service.CategoryService;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.Map;

@RestController
@RequestMapping("/api/categories")
@AllArgsConstructor
public class CategoryController {

    private final CategoryService categoryService;

    @GetMapping
    public ResponseEntity<Map<Object, Object>> getCategories() {
        return categoryService.getAll();
    }

    @GetMapping("/{id}")
    public ResponseEntity<Map<Object, Object>> getCategory(@PathVariable Long id) {
        return categoryService.getCategory(id);
    }

    @PostMapping
    public ResponseEntity<Map<Object, Object>> createCategory(@Valid @RequestBody CategoryRequest categoryRequest) {
        return categoryService.createCategory(categoryRequest);
    }

    @PatchMapping("/{id}")
    public ResponseEntity<Map<Object, Object>> updateCategory(@PathVariable Long id, @Valid @RequestBody CategoryRequest categoryRequest) {
        return categoryService.updateCategory(id, categoryRequest);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Map<Object, Object>> deleteCategory(@PathVariable Long id) {
        return categoryService.deleteCategory(id);
    }
}
