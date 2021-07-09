package com.albercafe.rabbitmarket.repository;

import com.albercafe.rabbitmarket.entity.Product;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ProductRepository extends JpaRepository<Product, Long> {
}
