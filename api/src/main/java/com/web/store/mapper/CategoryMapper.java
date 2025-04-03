package com.web.store.mapper;


import com.web.store.dto.category.CategoryDeleted;
import com.web.store.dto.category.CategoryResponse;
import com.web.store.dto.category.CategoryRequest;
import com.web.store.model.Category;
import org.mapstruct.Mapper;
import org.mapstruct.MappingConstants;

@Mapper(componentModel = MappingConstants.ComponentModel.SPRING)
public interface CategoryMapper {
    Category catetegoryResponseToCategory(CategoryRequest category);
    CategoryResponse categoryToCategoryResponse(Category category);
    CategoryDeleted categoryToCategoryDeleted(Category category);
}
