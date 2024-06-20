package com.dev02.libraryproject.service.business;

import com.dev02.libraryproject.entity.concretes.business.Category;
import com.dev02.libraryproject.entity.concretes.business.Publisher;
import com.dev02.libraryproject.exception.BadRequestException;
import com.dev02.libraryproject.exception.ConflictException;
import com.dev02.libraryproject.payload.mappers.CategoryMapper;
import com.dev02.libraryproject.payload.messages.ErrorMessages;
import com.dev02.libraryproject.payload.messages.SuccessMessages;
import com.dev02.libraryproject.payload.request.business.CategoryRequest;
import com.dev02.libraryproject.payload.response.business.CategoryResponse;
import com.dev02.libraryproject.payload.response.business.PublisherResponse;
import com.dev02.libraryproject.payload.response.business.ResponseMessage;
import com.dev02.libraryproject.repository.business.CategoryRepository;
import com.dev02.libraryproject.service.helper.MethodHelper;
import com.dev02.libraryproject.service.helper.PageableHelper;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
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
        Category foundCategory = methodHelper.isCategoryExists(categoryId);
        return categoryMapper.mapCategoryToCategoryResponse(foundCategory);
    }

    public CategoryResponse createCategory(CategoryRequest categoryRequest) {
        for (Category c : categoryRepository.findAll()){
            if (categoryRequest.getName().equalsIgnoreCase(c.getName())){
                throw new ConflictException(ErrorMessages.CATEGORY_ALREADY_EXISTS);
            }
        }

        Category category = categoryMapper.mapCategoryRequestToCategory(categoryRequest);

        return categoryMapper.mapCategoryToCategoryResponse(categoryRepository.save(category));
    }

    public CategoryResponse updateCategoryById(Long categoryId, CategoryRequest categoryRequest) {
        Category foundCategory = methodHelper.isCategoryExists(categoryId);
        foundCategory.setId(categoryId);
        foundCategory.setName(categoryRequest.getName());
        foundCategory.setBuiltIn(categoryRequest.getBuiltIn());
        foundCategory.setSequence(categoryRequest.getSequence());

        return categoryMapper.mapCategoryToCategoryResponse(categoryRepository.save(foundCategory));
    }

    public ResponseMessage<CategoryResponse> deleteCategoryById(Long categoryId) {
        Category category = methodHelper.isCategoryExists(categoryId);
        if(!category.getBookList().isEmpty()){
            throw new BadRequestException(ErrorMessages.CATEGORY_CAN_NOT_DELETED);
        }
        categoryRepository.delete(category);
        return ResponseMessage.<CategoryResponse>builder()
                .message(SuccessMessages.CATEGORY_FOUND)
                .httpStatus(HttpStatus.OK)
                .object(categoryMapper.mapCategoryToCategoryResponse(category))
                .build();
    }
}
