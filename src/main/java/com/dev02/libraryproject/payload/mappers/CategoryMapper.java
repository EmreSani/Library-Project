package com.dev02.libraryproject.payload.mappers;

import com.dev02.libraryproject.entity.concretes.business.Category;
import com.dev02.libraryproject.payload.request.business.CategoryRequest;
import com.dev02.libraryproject.payload.response.business.CategoryResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@RequiredArgsConstructor
@Component
public class CategoryMapper {

    public CategoryResponse mapCategoryToCategoryResponse(Category category){

    return CategoryResponse.builder().name(category.getName())
            .id(category.getId())
            .builtIn(category.getBuiltIn())
            .bookList(category.getBookList())
            .sequence(category.getSequence()).build();

    }

    public Category mapCategoryRequestToCategory(CategoryRequest categoryRequest){

        return null;
    }


}
