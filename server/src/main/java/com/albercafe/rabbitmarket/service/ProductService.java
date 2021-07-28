package com.albercafe.rabbitmarket.service;

import com.albercafe.rabbitmarket.dto.ProductRequest;
import com.albercafe.rabbitmarket.dto.ProductResponse;
import com.albercafe.rabbitmarket.entity.Category;
import com.albercafe.rabbitmarket.entity.Product;
import com.albercafe.rabbitmarket.mapper.ProductMapper;
import com.albercafe.rabbitmarket.repository.CategoryRepository;
import com.albercafe.rabbitmarket.repository.ProductRepository;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor
@Slf4j
public class ProductService {

    private final ProductRepository productRepository;
    private final CategoryRepository categoryRepository;
    private final AuthService authService;
    private final ProductMapper productMapper;

    @Transactional(readOnly = true)
    public ResponseEntity<Map<Object, Object>> getAll() {
        Map<Object, Object> responseBody = new HashMap<>();

        List<ProductResponse> productResponseList = productRepository
                .findAll().stream().map(productMapper::mapEntityToResponse).collect(Collectors.toList());

        responseBody.put("data", productResponseList);
        responseBody.put("error", null);

        return ResponseEntity.status(200).body(responseBody);
    }

    @Transactional
    public ResponseEntity<Map<Object, Object>> getProduct(Long id) {
        Optional<Product> product = productRepository.findById(id);

        Map<Object, Object> responseBody = new HashMap<>();

        if (product.isEmpty()) {
            responseBody.put("data", null);
            responseBody.put("error", "wrong product id !");
            return ResponseEntity.status(400).body(responseBody);
        }

        ProductResponse productResponse = productMapper.mapEntityToResponse(product.get());

        responseBody.put("data", productResponse);
        responseBody.put("error", null);

        return ResponseEntity.status(200).body(responseBody);
    }

    @Transactional
    public ResponseEntity<Map<Object, Object>> createProduct(ProductRequest productRequest) {
        Map<Object, Object> responseBody = new HashMap<>();

        if (authService.getCurrentUser() == null) {
            responseBody.put("data", null);
            responseBody.put("error", "you must login first !");
            return ResponseEntity.status(401).body(responseBody);
        }

        Optional<Category> category = categoryRepository.findByName(productRequest.getCategoryName());

        if (category.isEmpty()) {
            responseBody.put("data", null);
            responseBody.put("error", "category isn't exist !");
            return ResponseEntity.status(400).body(responseBody);
        }

        Category tempCategory = category.get();

        Product product = productMapper.mapRequestToEntity(productRequest, authService.getCurrentUser());

        List<Product> productList = tempCategory.getProductList();

        productList.add(product);

        tempCategory.setProductList(productList);

        productRepository.save(product);

        ProductResponse productResponse = productMapper.mapEntityToResponse(product);

        responseBody.put("data", productResponse);
        responseBody.put("error", null);

        return ResponseEntity.status(200).body(responseBody);
    }

    @Transactional
    public ResponseEntity<Map<Object, Object>> updateProduct(Long id, ProductRequest productRequest) {
        Map<Object, Object> responseBody = new HashMap<>();

        Optional<Product> product = productRepository.findById(id);

        if (product.isEmpty()) {
            responseBody.put("data", null);
            responseBody.put("error", "product id is wrong !!");
            return ResponseEntity.status(400).body(responseBody);
        }

        Product tempProduct = product.get();

        Optional<Category> category = categoryRepository.findByName(productRequest.getCategoryName());

        if (category.isEmpty()) {
            responseBody.put("data", null);
            responseBody.put("error", "category could not found !!, check out selected category name ");
            return ResponseEntity.status(400).body(responseBody);
        }

        Category tempCategory = category.get();

        tempProduct.setCategory(tempCategory);
        tempProduct.setTitle(productRequest.getTitle());
        tempProduct.setContent(productRequest.getContent());

        if (productRequest.getPrice() != null) {
            tempProduct.setPrice(productRequest.getPrice());
        }

        if (productRequest.getPhoto() != null) {
            tempProduct.setPhoto(productRequest.getPhoto());
        }

        productRepository.save(tempProduct);

        ProductResponse productResponse = productMapper.mapEntityToResponse(tempProduct);

        responseBody.put("data", productResponse);
        responseBody.put("error", null);

        return ResponseEntity.status(200).body(responseBody);
    }

    @Transactional
    public ResponseEntity<Map<Object, Object>> deleteProduct(Long id) {
        Map<Object, Object> responseBody = new HashMap<>();

        Optional<Product> product = productRepository.findById(id);

        if (product.isEmpty()) {
            responseBody.put("data", null);
            responseBody.put("error", "wrong product id !");
            return ResponseEntity.status(400).body(responseBody);
        }

        productRepository.deleteById(id);

        responseBody.put("data", "product removed !");
        responseBody.put("error", null);

        return ResponseEntity.status(200).body(responseBody);
    }
}
