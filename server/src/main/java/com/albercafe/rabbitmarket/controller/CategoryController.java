package com.albercafe.rabbitmarket.controller;

import com.albercafe.rabbitmarket.dto.CategoryDTO;
import com.albercafe.rabbitmarket.entity.Category;
import com.albercafe.rabbitmarket.service.CategoryService;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/categories")
@AllArgsConstructor
public class CategoryController {

    private final CategoryService categoryService;

    @GetMapping
    public List<Category> getCategories() {
        return categoryService.getAll();
    }

    @GetMapping("/{id}")
    public ResponseEntity<Map<Object, Object>> getCategory(@PathVariable Long id) {
        return categoryService.getCategory(id);
    }

    @PostMapping
    public ResponseEntity<Map<Object, Object>> createCategory(@Valid @RequestBody CategoryDTO categoryDTO) {
        return categoryService.createCategory(categoryDTO);
    }

    @PatchMapping
    public ResponseEntity<Map<Object, Object>> updateCategory(@Valid @RequestBody CategoryDTO categoryDTO) {
        return categoryService.updateCategory(categoryDTO);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Map<Object, Object>> deleteCategory(@PathVariable Long id) {
        return categoryService.deleteCategory(id);
    }
}
