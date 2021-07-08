package com.albercafe.rabbitmarket.controller;

import com.albercafe.rabbitmarket.entity.Category;
import com.albercafe.rabbitmarket.service.CategoryService;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

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
    public Category getCategory(@PathVariable Long id) { return categoryService.getCategory(id); }

    @PostMapping
    public Category createCategory(@RequestBody Category category) { return categoryService.createCategory(category); }

    @PatchMapping("/{id}")
    public Category updateCategory(@PathVariable Long id, @RequestBody Category category) {
        return categoryService.updateCategory(id, category);
    }

    @DeleteMapping("/{id}")
    public void deleteCategory(@PathVariable Long id) {
        categoryService.deleteCategory(id);
    }
}
