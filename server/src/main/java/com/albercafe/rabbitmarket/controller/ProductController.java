package com.albercafe.rabbitmarket.controller;

import com.albercafe.rabbitmarket.dto.CustomResponse;
import com.albercafe.rabbitmarket.dto.ProductRequest;
import com.albercafe.rabbitmarket.service.ProductService;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RestController
@RequestMapping("/api/products")
@AllArgsConstructor
public class ProductController {

    private final ProductService productService;

    @ApiOperation(httpMethod = "GET", value = "Get All Products", notes = "Gets all the information associated with the product")
    @ApiResponse(code = 200, message = "OK", response = CustomResponse.class)
    @GetMapping
    public ResponseEntity<CustomResponse> getProducts() {
        return productService.getAll();
    }

    @ApiOperation(httpMethod = "GET", value = "Gets specific product information", notes = "Gets only product that match a given id")
    @ApiResponse(code = 200, message = "OK", response = CustomResponse.class)
    @GetMapping("/{id}")
    public ResponseEntity<CustomResponse> getProductById(@PathVariable Long id) {
        return productService.getProduct(id);
    }

    @ApiOperation(httpMethod = "POST", value = "Create Product", notes = "When create product, you must check ProductRequest Object !")
    @ApiResponse(code = 200, message = "OK", response = CustomResponse.class)
    @PostMapping
    public ResponseEntity<CustomResponse> createProduct(@RequestBody @Valid ProductRequest productRequest) {
        return productService.createProduct(productRequest);
    }

    @ApiOperation(httpMethod = "PATCH", value = "Update Specific Product", notes = "Modify only product that match a given id")
    @ApiResponse(code = 200, message = "OK", response = CustomResponse.class)
    @PatchMapping("/{id}")
    public ResponseEntity<CustomResponse> updateProduct(@PathVariable Long id, @RequestBody @Valid ProductRequest productRequest) {
        return productService.updateProduct(id, productRequest);
    }

    @ApiOperation(httpMethod = "DELETE", value = "Delete Specific Product", notes = "Delete only product that match a given id")
    @ApiResponse(code = 200, message = "OK", response = CustomResponse.class)
    @DeleteMapping("/{id}")
    public ResponseEntity<CustomResponse> deleteProduct(@PathVariable Long id) {
        return productService.deleteProduct(id);
    }
}
