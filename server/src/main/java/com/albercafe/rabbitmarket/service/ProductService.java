package com.albercafe.rabbitmarket.service;

import com.albercafe.rabbitmarket.dto.CustomResponse;
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

import java.util.List;
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
    public ResponseEntity<CustomResponse> getAll() {
        CustomResponse responseBody = new CustomResponse();

        List<ProductResponse> productResponseList = productRepository
                .findAll().stream().map(productMapper::mapEntityToResponse).collect(Collectors.toList());

        responseBody.setData(productResponseList);
        responseBody.setError(null);

        return ResponseEntity.status(200).body(responseBody);
    }

    @Transactional
    public ResponseEntity<CustomResponse> getProduct(Long id) {
        Optional<Product> product = productRepository.findById(id);

        CustomResponse responseBody = new CustomResponse();

        if (product.isEmpty()) {
            responseBody.setData(null);
            responseBody.setError("Wrong Product Id !");
            return ResponseEntity.status(400).body(responseBody);
        }

        ProductResponse productResponse = productMapper.mapEntityToResponse(product.get());

        responseBody.setData(productResponse);
        responseBody.setError(null);

        return ResponseEntity.status(200).body(responseBody);
    }

    @Transactional
    public ResponseEntity<CustomResponse> createProduct(ProductRequest productRequest) {
        CustomResponse responseBody = new CustomResponse();

        if (authService.getCurrentUser() == null) {
            responseBody.setData(null);
            responseBody.setError("you must login first !");
            return ResponseEntity.status(401).body(responseBody);
        }

        Optional<Category> category = categoryRepository.findByName(productRequest.getCategoryName());

        if (category.isEmpty()) {
            responseBody.setData(null);
            responseBody.setError("Category doesn't exist !");
            return ResponseEntity.status(400).body(responseBody);
        }

        Category tempCategory = category.get();

        Product product = productMapper.mapRequestToEntity(productRequest, authService.getCurrentUser());

        List<Product> productList = tempCategory.getProductList();

        productList.add(product);

        tempCategory.setProductList(productList);

        productRepository.save(product);

        ProductResponse productResponse = productMapper.mapEntityToResponse(product);

        responseBody.setData(productResponse);
        responseBody.setError(null);

        return ResponseEntity.status(200).body(responseBody);
    }

    @Transactional
    public ResponseEntity<CustomResponse> updateProduct(Long id, ProductRequest productRequest) {
        CustomResponse responseBody = new CustomResponse();

        Optional<Product> product = productRepository.findById(id);

        if (product.isEmpty()) {
            responseBody.setData(null);
            responseBody.setError("Product's id wrong !");
            return ResponseEntity.status(400).body(responseBody);
        }

        Product tempProduct = product.get();

        Optional<Category> category = categoryRepository.findByName(productRequest.getCategoryName());

        if (category.isEmpty()) {
            responseBody.setData(null);
            responseBody.setError("Category could not found !!");
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

        responseBody.setData(productResponse);
        responseBody.setError(null);

        return ResponseEntity.status(200).body(responseBody);
    }

    @Transactional
    public ResponseEntity<CustomResponse> deleteProduct(Long id) {
        CustomResponse responseBody = new CustomResponse();

        Optional<Product> product = productRepository.findById(id);

        if (product.isEmpty()) {
            responseBody.setData(null);
            responseBody.setError("Wrong Product Id !");
            return ResponseEntity.status(400).body(responseBody);
        }

        productRepository.deleteById(id);

        responseBody.setData("Product removed !");
        responseBody.setError(null);

        return ResponseEntity.status(200).body(responseBody);
    }
}
