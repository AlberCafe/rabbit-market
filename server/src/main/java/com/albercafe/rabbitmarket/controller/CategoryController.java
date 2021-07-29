package com.albercafe.rabbitmarket.controller;

import com.albercafe.rabbitmarket.dto.CategoryRequest;
import com.albercafe.rabbitmarket.dto.CustomResponse;
import com.albercafe.rabbitmarket.service.CategoryService;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RestController
@RequestMapping("/api/categories")
@AllArgsConstructor
public class CategoryController {

    private final CategoryService categoryService;

    @ApiOperation(httpMethod = "GET", value = "Get All Categories", notes = "Gets all the information associated with the category")
    @ApiResponse(code = 200, message = "OK", response = CustomResponse.class)
    @GetMapping
    public ResponseEntity<CustomResponse> getCategories() {
        return categoryService.getAll();
    }

    @ApiOperation(httpMethod = "GET", value = "Gets specific category information", notes = "Gets only category that match a given id")
    @ApiResponse(code = 200, message = "OK", response = CustomResponse.class)
    @GetMapping("/{id}")
    public ResponseEntity<CustomResponse> getCategory(@PathVariable Long id) {
        return categoryService.getCategory(id);
    }

    @ApiOperation(httpMethod = "POST", value = "Create Category", notes = "Only name values are required when creating categories")
    @ApiResponse(code = 200, message = "OK", response = CustomResponse.class)
    @PostMapping
    public ResponseEntity<CustomResponse> createCategory(@Valid @RequestBody CategoryRequest categoryRequest) {
        return categoryService.createCategory(categoryRequest);
    }

    @ApiOperation(httpMethod = "PATCH", value = "Update Specific Category", notes = "Modify only category that match a given id")
    @ApiResponse(code = 200, message = "OK", response = CustomResponse.class)
    @PatchMapping("/{id}")
    public ResponseEntity<CustomResponse> updateCategory(@PathVariable Long id, @Valid @RequestBody CategoryRequest categoryRequest) {
        return categoryService.updateCategory(id, categoryRequest);
    }

    @ApiOperation(httpMethod = "DELETE", value = "Delete Specific Category", notes = "Delete only category that match a given id")
    @ApiResponse(code = 200, message = "OK", response = CustomResponse.class)
    @DeleteMapping("/{id}")
    public ResponseEntity<CustomResponse> deleteCategory(@PathVariable Long id) {
        return categoryService.deleteCategory(id);
    }
}
