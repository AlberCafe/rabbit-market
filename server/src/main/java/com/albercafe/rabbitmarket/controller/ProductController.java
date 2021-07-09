package com.albercafe.rabbitmarket.controller;

import com.albercafe.rabbitmarket.entity.Product;
import com.albercafe.rabbitmarket.service.ProductService;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/products")
@AllArgsConstructor
public class ProductController {

    private ProductService productService;

    @GetMapping
    public List<Product> getProducts() { return productService.getAll(); }

    @GetMapping("/{id}")
    public Product getProduct(@PathVariable Long id) { return productService.getProduct(id); }

    @PostMapping
    public Product createProduct(@RequestBody Product product) { return productService.createProduct(product); }

    @PatchMapping
    public Product updateProduct(@RequestBody Product product) {
        return productService.updateProduct(product);
    }

    @DeleteMapping("/{id}")
    public void deleteProduct(@PathVariable Long id) { productService.deleteProduct(id); }
}
