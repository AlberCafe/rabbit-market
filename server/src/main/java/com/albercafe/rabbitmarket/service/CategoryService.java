package com.albercafe.rabbitmarket.service;

import com.albercafe.rabbitmarket.dto.CategoryRequest;
import com.albercafe.rabbitmarket.entity.Category;
import com.albercafe.rabbitmarket.mapper.CategoryMapper;
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
    private final CategoryMapper categoryMapper;
    private final AuthService authService;

    @Transactional(readOnly = true)
    public ResponseEntity<Map<Object, Object>> getAll() {
        Map<Object, Object> responseBody = new HashMap<>();

        List<Category> categories = categoryRepository.findAll();

        responseBody.put("data", categories);
        responseBody.put("error", null);

        return ResponseEntity.status(200).body(responseBody);
    }

    @Transactional
    public ResponseEntity<Map<Object, Object>> getCategory(Long id) {
        Map<Object, Object> responseBody = new HashMap<>();

        Optional<Category> category = categoryRepository.findById(id);

        if (category.isEmpty()) {
            responseBody.put("data", null);
            responseBody.put("error", "wrong category id !");
            return ResponseEntity.status(400).body(responseBody);
        }

        responseBody.put("data", category.get());
        responseBody.put("error", null);

        return ResponseEntity.status(200).body(responseBody);
    }

    @Transactional
    public ResponseEntity<Map<Object, Object>> createCategory(CategoryRequest categoryRequest) {
        Map<Object, Object> responseBody = new HashMap<>();

        if (authService.getCurrentUser() == null) {
            responseBody.put("data", null);
            responseBody.put("error", "you must login first !");
            return ResponseEntity.status(401).body(responseBody);
        }

        Optional<Category> tempCategory = categoryRepository.findByName(categoryRequest.getName());

        if (tempCategory.isPresent()) {
            responseBody.put("data", null);
            responseBody.put("error", "category already exists !");
            return ResponseEntity.status(400).body(responseBody);
        }

        Category category = categoryMapper.mapRequestToEntity(categoryRequest);

        categoryRepository.save(category);

        responseBody.put("data", category);
        responseBody.put("error", null);

        return ResponseEntity.status(200).body(responseBody);
    }

    @Transactional
    public ResponseEntity<Map<Object, Object>> updateCategory(Long id, CategoryRequest categoryRequest) {

        Optional<Category> category = categoryRepository.findById(id);

        Map<Object, Object> responseBody = new HashMap<>();

        if (category.isEmpty()) {
            responseBody.put("data", null);
            responseBody.put("error", "wrong category id !");
            return ResponseEntity.status(400).body(responseBody);
        }

        Category tempCategory = category.get();

        tempCategory.setName(categoryRequest.getName());

        categoryRepository.save(tempCategory);

        responseBody.put("data", tempCategory);
        responseBody.put("error", null);

        return ResponseEntity.status(200).body(responseBody);
    }

    @Transactional
    public ResponseEntity<Map<Object, Object>> deleteCategory(Long id) {
        Map<Object, Object> responseBody = new HashMap<>();

        Optional<Category> category = categoryRepository.findById(id);

        if (category.isEmpty()) {
            responseBody.put("data", null);
            responseBody.put("error", "category isn't exist ! ");
            return ResponseEntity.status(400).body(responseBody);
        }

        responseBody.put("data", "category id : " + id + " is removed !");
        responseBody.put("error", null);

        categoryRepository.deleteById(id);

        return ResponseEntity.status(200).body(responseBody);
    }
}
