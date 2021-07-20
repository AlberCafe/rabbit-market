package com.albercafe.rabbitmarket.service;

import com.albercafe.rabbitmarket.dto.CategoryDTO;
import com.albercafe.rabbitmarket.entity.Category;
import com.albercafe.rabbitmarket.exception.CategoryNotFoundException;
import com.albercafe.rabbitmarket.repository.CategoryRepository;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@Service
@AllArgsConstructor
@Slf4j
public class CategoryService {

    private final CategoryRepository categoryRepository;
    private final AuthService authService;

    @Transactional(readOnly = true)
    public List<Category> getAll() {
        return categoryRepository.findAll();
    }

    @Transactional
    public ResponseEntity<Map<Object, Object>> getCategory(Long id) {
        Map<Object, Object> responseBody = new HashMap<>();

        Optional<Category> category = categoryRepository.findById(id);

        if (!category.isPresent()) {
            responseBody.put("data", null);
            responseBody.put("error", "category isn't exist !");
            return ResponseEntity.badRequest().body(responseBody);
        }

        Category categoryResponse = category.get();

        responseBody.put("data", categoryResponse);
        responseBody.put("error", null);

        return ResponseEntity.ok().body(responseBody);
    }

    @Transactional
    public ResponseEntity<Map<Object, Object>> createCategory(CategoryDTO categoryDTO) {
        Map<Object, Object> responseBody = new HashMap<>();

        if (authService.getCurrentUser() == null) {
            responseBody.put("data", null);
            responseBody.put("error", "you must need to login first !");
            return ResponseEntity.status(401).body(responseBody);
        }

        if (categoryRepository.findByName(categoryDTO.getName()).isPresent()) {
            responseBody.put("data", null);
            responseBody.put("error", "category already exists !");
            return ResponseEntity.badRequest().body(responseBody);
        }

        Category category = new Category();
        category.setName(categoryDTO.getName());

        categoryRepository.save(category);

        responseBody.put("data", category);
        responseBody.put("error", null);

        return ResponseEntity.ok().body(responseBody);
    }

    @Transactional
    public ResponseEntity<Map<Object, Object>> updateCategory(CategoryDTO categoryDTO) {
        Map<Object, Object> responseBody = new HashMap<>();

        Optional<Category> category = categoryRepository.findById(categoryDTO.getId());

        if (!category.isPresent()) {
            responseBody.put("data", null);
            responseBody.put("error", "category isn't exist !");
            return ResponseEntity.badRequest().body(responseBody);
        }

        Category categoryResponse = category.get();
        categoryResponse.setName(categoryDTO.getName());
        categoryRepository.save(categoryResponse);

        responseBody.put("data", categoryResponse);
        responseBody.put("error", null);

        return ResponseEntity.ok().body(responseBody);
    }

    @Transactional
    public ResponseEntity<Map<Object, Object>> deleteCategory(Long id) {
        Map<Object, Object> responseBody = new HashMap<>();
        Optional<Category> category = categoryRepository.findById(id);

        if (!category.isPresent()) {
            responseBody.put("data", null);
            responseBody.put("error", "category isn't exist ! ");
            return ResponseEntity.badRequest().body(responseBody);
        }

        Category categoryResponse = category.get();
        responseBody.put("data", categoryResponse);
        responseBody.put("error", null);

        return ResponseEntity.ok().body(responseBody);
    }
}
