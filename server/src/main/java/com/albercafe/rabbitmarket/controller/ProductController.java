package com.albercafe.rabbitmarket.controller;

import com.albercafe.rabbitmarket.dto.ProductRequest;
import com.albercafe.rabbitmarket.service.ProductService;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.Map;

@RestController
@RequestMapping("/api/products")
@AllArgsConstructor
public class ProductController {

    private final ProductService productService;

    @GetMapping
    public ResponseEntity<Map<Object, Object>> getProducts() {
        return productService.getAll();
    }

    @GetMapping("/{id}")
    public ResponseEntity<Map<Object, Object>> getProductById(@PathVariable Long id) {
        return productService.getProduct(id);
    }

    @PostMapping
    public ResponseEntity<Map<Object, Object>> createProduct(@RequestBody @Valid ProductRequest productRequest) {
        return productService.createProduct(productRequest);
    }

    @PatchMapping("/{id}")
    public ResponseEntity<Map<Object, Object>> updateProduct(@PathVariable Long id, @RequestBody @Valid ProductRequest productRequest) {
        return productService.updateProduct(id, productRequest);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Map<Object, Object>> deleteProduct(@PathVariable Long id) {
        return productService.deleteProduct(id);
    }
}
