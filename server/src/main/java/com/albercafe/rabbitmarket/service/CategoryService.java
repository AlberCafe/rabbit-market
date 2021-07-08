package com.albercafe.rabbitmarket.service;

import com.albercafe.rabbitmarket.entity.Category;
import com.albercafe.rabbitmarket.exception.CategoryNotFoundException;
import com.albercafe.rabbitmarket.repository.CategoryRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@AllArgsConstructor
public class CategoryService {

    private final CategoryRepository categoryRepository;

    @Transactional(readOnly = true)
    public List<Category> getAll() {
        return categoryRepository.findAll();
    }

    @Transactional
    public Category getCategory(Long id) {
        return categoryRepository.findById(id).orElseThrow(() -> new CategoryNotFoundException("이 아이디로 카테고리를 찾을 수 없음 : " + id));
    }

    @Transactional
    public Category createCategory(Category category) {
        return categoryRepository.save(category);
    }

    @Transactional
    public Category updateCategory(Long id, Category category) {
        Category temp = categoryRepository.findById(id).orElseThrow(() -> new CategoryNotFoundException("이 아이디로 카테고리를 찾을 수 없음 + " + id));
        temp.setName(category.getName());
        if (category.getIcon() != null)
            temp.setIcon(category.getIcon());
        return temp;
    }

    @Transactional
    public void deleteCategory(Long id) {
        categoryRepository.deleteById(id);
    }
}
