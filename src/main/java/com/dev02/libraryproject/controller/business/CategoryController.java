package com.dev02.libraryproject.controller.business;

import com.dev02.libraryproject.payload.request.business.CategoryRequest;
import com.dev02.libraryproject.payload.response.business.CategoryResponse;
import com.dev02.libraryproject.payload.response.business.ResponseMessage;
import com.dev02.libraryproject.service.business.CategoryService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RestController
@RequestMapping("/categories")
@RequiredArgsConstructor
public class CategoryController {

    private final CategoryService categoryService;

    @GetMapping // http://localhost:8080/categories
    public ResponseEntity<Page<CategoryResponse>> getAllCategoriesByPage(
            @RequestParam(value = "page", defaultValue = "0") int page,
            @RequestParam(value = "size",defaultValue = "20") int size,
            @RequestParam(value = "sort",defaultValue = "name") String sort,
            @RequestParam(value = "type",defaultValue = "asc") String type){
        return categoryService.getAllCategoriesByPage(page,size,sort,type);
    }

    @GetMapping("/{categoryId}")
    public ResponseEntity<CategoryResponse> getCategoryById(@PathVariable Long categoryId){
        return ResponseEntity.ok(categoryService.getCategoryById(categoryId));
    }

    @PostMapping
    @PreAuthorize("hasAnyAuthority('ADMIN')")
    public ResponseEntity<CategoryResponse> createCategory(@RequestBody @Valid CategoryRequest categoryRequest){
        return new ResponseEntity<>(categoryService.createCategory(categoryRequest), HttpStatus.CREATED);
    }

    @PutMapping("/{categoryId}")
    @PreAuthorize("hasAnyAuthority('ADMIN')")
    public ResponseEntity<CategoryResponse> updateCategoryById(@PathVariable Long categoryId, @RequestBody @Valid CategoryRequest categoryRequest){
        return ResponseEntity.ok(categoryService.updateCategoryById(categoryId,categoryRequest));
    }

    @DeleteMapping("/{categoryId}")
    @PreAuthorize("hasAnyAuthority('ADMIN')")
    public ResponseMessage<CategoryResponse> deleteCategoryById(@PathVariable Long categoryId){
        return categoryService.deleteCategoryById(categoryId);
    }




}
