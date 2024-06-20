package com.dev02.libraryproject.service.business;

import com.dev02.libraryproject.entity.concretes.business.Category;
import com.dev02.libraryproject.payload.mappers.CategoryMapper;
import com.dev02.libraryproject.payload.response.business.CategoryResponse;
import com.dev02.libraryproject.payload.response.business.LoanResponseWithUser;
import com.dev02.libraryproject.repository.business.CategoryRepository;
import com.dev02.libraryproject.service.helper.MethodHelper;
import com.dev02.libraryproject.service.helper.PageableHelper;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class CategoryService {

    private final CategoryRepository categoryRepository;
    private final PageableHelper pageableHelper;
    private final CategoryMapper categoryMapper;
    private final MethodHelper methodHelper;


    public ResponseEntity<Page<CategoryResponse>> getAllCategoriesByPage(int page, int size, String sort, String type) {

        Pageable pageable = pageableHelper.getPageableWithProperties(page, size, sort, type);

        return ResponseEntity.ok(categoryRepository.findAll(pageable).map(categoryMapper::mapCategoryToCategoryResponse));



    }


    public CategoryResponse getCategoryById(Long categoryId) {
        Category foundCategory = methodHelper.isCategoryExist(categoryId);
        return categoryMapper.mapCategoryToCategoryResponse(foundCategory);
    }
}
