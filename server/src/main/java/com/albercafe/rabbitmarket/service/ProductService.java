package com.albercafe.rabbitmarket.service;

import com.albercafe.rabbitmarket.entity.Product;
import com.albercafe.rabbitmarket.exception.ProductNotFoundException;
import com.albercafe.rabbitmarket.repository.ProductRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@AllArgsConstructor
public class ProductService {

    private ProductRepository productRepository;

    @Transactional(readOnly = true)
    public List<Product> getAll() {
        return productRepository.findAll();
    }

    @Transactional
    public Product getProduct(Long id) {
        return productRepository.findById(id).orElseThrow(() -> new ProductNotFoundException("이 아이디로 상품을 찾을 수 없음 " + id));
    }

    @Transactional
    public Product createProduct(Product product) {
        return productRepository.save(product);
    }

    @Transactional
    public Product updateProduct(Product product) {
        // TODO: 수정을 위한 더 나은 로직 필요
        return productRepository.save(product);
    }

    @Transactional
    public void deleteProduct(Long id) { productRepository.deleteById(id); }
}
