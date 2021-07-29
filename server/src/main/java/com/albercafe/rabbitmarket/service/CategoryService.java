package com.albercafe.rabbitmarket.service;

import com.albercafe.rabbitmarket.dto.CategoryRequest;
import com.albercafe.rabbitmarket.dto.CustomResponse;
import com.albercafe.rabbitmarket.entity.Category;
import com.albercafe.rabbitmarket.mapper.CategoryMapper;
import com.albercafe.rabbitmarket.repository.CategoryRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
@Slf4j
public class CategoryService {

    private final CategoryRepository categoryRepository;
    private final CategoryMapper categoryMapper;
    private final AuthService authService;

    @Transactional(readOnly = true)
    public ResponseEntity<CustomResponse> getAll() {
        CustomResponse responseBody = new CustomResponse();

        List<Category> categories = categoryRepository.findAll();

        responseBody.setData(categories);
        responseBody.setError(null);

        return ResponseEntity.status(200).body(responseBody);
    }

    @Transactional
    public ResponseEntity<CustomResponse> getCategory(Long id) {
        CustomResponse responseBody = new CustomResponse();

        Optional<Category> category = categoryRepository.findById(id);

        if (category.isEmpty()) {
            responseBody.setData(null);
            responseBody.setError("Wrong Category Id !");
            return ResponseEntity.status(400).body(responseBody);
        }

        responseBody.setData(category.get());
        responseBody.setError(null);

        return ResponseEntity.status(200).body(responseBody);
    }

    @Transactional
    public ResponseEntity<CustomResponse> createCategory(CategoryRequest categoryRequest) {
        CustomResponse responseBody = new CustomResponse();

        if (authService.getCurrentUser() == null) {
            responseBody.setData(null);
            responseBody.setError("you must login first !");
            return ResponseEntity.status(401).body(responseBody);
        }

        Optional<Category> tempCategory = categoryRepository.findByName(categoryRequest.getName());

        if (tempCategory.isPresent()) {
            responseBody.setData(null);
            responseBody.setError("category already exists !");
            return ResponseEntity.status(400).body(responseBody);
        }

        Category category = categoryMapper.mapRequestToEntity(categoryRequest);

        categoryRepository.save(category);

        responseBody.setData(category);
        responseBody.setError(null);

        return ResponseEntity.status(200).body(responseBody);
    }

    @Transactional
    public ResponseEntity<CustomResponse> updateCategory(Long id, CategoryRequest categoryRequest) {

        Optional<Category> category = categoryRepository.findById(id);

        CustomResponse responseBody = new CustomResponse();

        if (category.isEmpty()) {
            responseBody.setData(null);
            responseBody.setError("Wrong Category Id !");
            return ResponseEntity.status(400).body(responseBody);
        }

        Category tempCategory = category.get();

        tempCategory.setName(categoryRequest.getName());

        categoryRepository.save(tempCategory);

        responseBody.setData(tempCategory);
        responseBody.setError(null);

        return ResponseEntity.status(200).body(responseBody);
    }

    @Transactional
    public ResponseEntity<CustomResponse> deleteCategory(Long id) {
        CustomResponse responseBody = new CustomResponse();

        Optional<Category> category = categoryRepository.findById(id);

        if (category.isEmpty()) {
            responseBody.setData(null);
            responseBody.setError("Category doesn't exist !");
            return ResponseEntity.status(400).body(responseBody);
        }

        categoryRepository.deleteById(id);

        responseBody.setData("category id : " + id + " is removed !");
        responseBody.setError(null);

        return ResponseEntity.status(200).body(responseBody);
    }
}
