package com.albercafe.rabbitmarket.mapper;

import com.albercafe.rabbitmarket.dto.ProductRequest;
import com.albercafe.rabbitmarket.dto.ProductResponse;
import com.albercafe.rabbitmarket.entity.Category;
import com.albercafe.rabbitmarket.entity.Product;
import com.albercafe.rabbitmarket.entity.User;
import com.albercafe.rabbitmarket.exception.CategoryNotFoundException;
import com.albercafe.rabbitmarket.repository.CategoryRepository;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.springframework.beans.factory.annotation.Autowired;

@Mapper(componentModel = "spring")
public abstract class ProductMapper {

    @Autowired
    private CategoryRepository categoryRepository;

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "category", expression = "java(findCategoryByName(productRequest))")
    @Mapping(target = "user", source = "user")
    @Mapping(target = "createdDate", expression = "java(java.time.OffsetDateTime.now())")
    public abstract Product mapRequestToEntity(ProductRequest productRequest, User user);

    @Mapping(target = "categoryName", expression = "java(findCategoryName(product))")
    public abstract ProductResponse mapEntityToResponse(Product product);

    Category findCategoryByName(ProductRequest productRequest) {
        return categoryRepository
                .findByName(productRequest.getCategoryName())
                .orElseThrow(() -> new CategoryNotFoundException("can't find cateogry : " + productRequest.getCategoryName()));
    }

    String findCategoryName(Product product) {
        Category category = product.getCategory();

        return category.getName();
    }
}
