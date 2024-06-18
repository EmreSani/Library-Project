package com.dev02.libraryproject.service.business;

import com.dev02.libraryproject.entity.concretes.business.Author;
import com.dev02.libraryproject.entity.concretes.business.Book;
import com.dev02.libraryproject.entity.concretes.business.Category;
import com.dev02.libraryproject.payload.response.business.BookResponse;
import com.dev02.libraryproject.repository.business.BookRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class BookService {

    private final AuthorService authorService;
    private final BookRepository bookRepository;
    private final CategoryService categoryService;
    private  final  PublisherService publisherService;

    public Page<BookResponse> getBooks(String query, Integer category, Long author, Integer publisher, Integer page, Integer size, String sort, String type) {
       authorService.isAuthorExistsById(author);

        return null;

    }
}
