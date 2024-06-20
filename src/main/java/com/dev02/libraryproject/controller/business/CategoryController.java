package com.dev02.libraryproject.controller.business;

import com.dev02.libraryproject.payload.response.business.CategoryResponse;
import com.dev02.libraryproject.payload.response.business.LoanResponseWithUser;
import com.dev02.libraryproject.service.business.CategoryService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

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

    @GetMapping("/{id}")
    public ResponseEntity<CategoryResponse> getCategoryById(@PathVariable Long categoryId){
        return ResponseEntity.ok(categoryService.getCategoryById(categoryId));
    }



}
