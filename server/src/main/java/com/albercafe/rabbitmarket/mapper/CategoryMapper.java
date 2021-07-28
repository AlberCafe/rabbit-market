package com.albercafe.rabbitmarket.mapper;

import com.albercafe.rabbitmarket.dto.CategoryRequest;
import com.albercafe.rabbitmarket.entity.Category;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface CategoryMapper {

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "icon", ignore = true)
    @Mapping(target = "productList", ignore = true)
    Category mapRequestToEntity(CategoryRequest categoryRequest);
}
