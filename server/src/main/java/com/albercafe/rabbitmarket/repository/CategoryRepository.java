package com.albercafe.rabbitmarket.repository;

import com.albercafe.rabbitmarket.entity.Category;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CategoryRepository extends JpaRepository<Category, Long> {
}
