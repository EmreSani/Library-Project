package com.dev02.libraryproject.service.business;

import com.dev02.libraryproject.entity.concretes.business.Author;
import com.dev02.libraryproject.entity.concretes.business.Book;
import com.dev02.libraryproject.entity.concretes.business.Category;
import com.dev02.libraryproject.entity.concretes.business.Publisher;
import com.dev02.libraryproject.entity.concretes.user.User;
import com.dev02.libraryproject.payload.mappers.BookMapper;
import com.dev02.libraryproject.payload.response.business.BookResponse;
import com.dev02.libraryproject.repository.business.BookRepository;
import com.dev02.libraryproject.service.helper.MethodHelper;
import com.dev02.libraryproject.service.helper.PageableHelper;
import lombok.RequiredArgsConstructor;
import org.apache.catalina.mapper.Mapper;
import org.springframework.data.domain.*;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletRequest;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class BookService {

    private final AuthorService authorService;
    private final BookRepository bookRepository;
    private final CategoryService categoryService;
    private  final  PublisherService publisherService;
    private final MethodHelper methodHelper;
    private final BookMapper bookMapper;
    private final PageableHelper pageableHelper;


   public Page<BookResponse> getBooks(HttpServletRequest httpServletRequest, String query, Long categoryId, Long authorId, Long publisherId, Integer page, Integer size, String sort, String type) {
       // En az bir alanın dolu olmasını sağlayalım
       if (query.isEmpty() && categoryId == null && authorId == null && publisherId == null) {
           throw new IllegalArgumentException("At least one of the fields (q, cat, author and publisher) is required");
       }


       if (authorId != null) {
           methodHelper.isAuthorExistsById(authorId);
       }
       if (categoryId != null) {
           methodHelper.isCategoryExist(categoryId);
       }
       if (publisherId != null) {
           methodHelper.isPublisherExist(publisherId);
       }

       boolean isAdmin = methodHelper.isAdmin(httpServletRequest);

       Pageable pageable = pageableHelper.getPageableWithProperties(page, size, sort, type);

       Page<Book> bookPage;

       if (isAdmin) {
           bookPage = bookRepository.findAllBooks(query, categoryId, authorId, publisherId, pageable);
       } else {
           bookPage = bookRepository.findAllActiveBooks(query, categoryId, authorId, publisherId, pageable);
       }


       List<BookResponse> bookResponses = bookPage.stream()
               .map(bookMapper::mapBookToBookResponse)
               .collect(Collectors.toList());

       // Sonuçları Page<BookResponse> olarak döndürme
       return new PageImpl<>(bookResponses, pageable, bookPage.getTotalElements());
   }


}


