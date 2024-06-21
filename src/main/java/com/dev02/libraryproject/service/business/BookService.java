package com.dev02.libraryproject.service.business;

import com.dev02.libraryproject.entity.concretes.business.Book;
import com.dev02.libraryproject.entity.concretes.business.Loan;
import com.dev02.libraryproject.payload.mappers.BookMapper;


import com.dev02.libraryproject.exception.ConflictException;
import com.dev02.libraryproject.payload.messages.ErrorMessages;
import com.dev02.libraryproject.payload.messages.SuccessMessages;
import com.dev02.libraryproject.payload.request.business.BookRequest;

import com.dev02.libraryproject.payload.response.business.BookResponse;
import com.dev02.libraryproject.payload.response.business.ResponseMessage;
import com.dev02.libraryproject.repository.business.BookRepository;
import com.dev02.libraryproject.service.helper.MethodHelper;
import com.dev02.libraryproject.service.helper.PageableHelper;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.*;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletRequest;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;

import java.time.LocalDateTime;


@Service
@RequiredArgsConstructor
public class BookService {

    private final AuthorService authorService;
    private final BookRepository bookRepository;
    private final CategoryService categoryService;

    private final PublisherService publisherService;
    private final MethodHelper methodHelper;
    private final BookMapper bookMapper;
    private final PageableHelper pageableHelper;


    public Page<BookResponse> getBooks(HttpServletRequest httpServletRequest, String query, Long categoryId, Long authorId, Long publisherId, Integer page, Integer size, String sort, String type) {
        // En az bir alanın dolu olmasını sağlayalım
        if (query.isEmpty() && categoryId == null && authorId == null && publisherId == null) {
            throw new IllegalArgumentException("At least one of the fields (q, cat, author and publisher) is required");
        }
        String username = (String) httpServletRequest.getAttribute("username");
        methodHelper.isUserExistByUsername(username);

        if (authorId != null) {
            methodHelper.isAuthorExistsById(authorId);
        }
        if (categoryId != null) {
            methodHelper.isCategoryExists(categoryId);
        }
        if (publisherId != null) {
            methodHelper.isPublisherExists(publisherId);
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

    public ResponseMessage<BookResponse> findBookById(Long id) {
        Book foundBook = methodHelper.isBookExists(id);
        return ResponseMessage.<BookResponse>builder()
                .message(SuccessMessages.BOOK_FOUND)
                .httpStatus(HttpStatus.OK)
                .object(bookMapper.mapBookToBookResponse(foundBook))
                .build();
    }


    public ResponseMessage<BookResponse> saveBook(HttpServletRequest httpServletRequest, BookRequest bookRequest) {
        String username = (String) httpServletRequest.getAttribute("username");
        methodHelper.isUserExistByUsername(username);

        Long id = bookMapper.mapBookRequestToBook(bookRequest).getId();
        methodHelper.isBookExists(id);
        methodHelper.isCategoryExists(bookRequest.getCategoryId());
        methodHelper.isAuthorExistsById(bookRequest.getAuthorId());
        methodHelper.isPublisherExists(bookRequest.getPublisherId());

        // User admin = methodHelper.isUserExist(id);
        // methodHelper.checkRole(admin, RoleType.ADMIN);

        methodHelper.isCategoryExists(bookRequest.getCategoryId());
        methodHelper.isAuthorExistsById(bookRequest.getAuthorId());
        methodHelper.isPublisherExists(bookRequest.getPublisherId());

        bookRequest.setCreateDate(LocalDateTime.now());

        Book savedBook = bookRepository.save(bookMapper.mapBookRequestToBook(bookRequest));

        return ResponseMessage.<BookResponse>builder()
                .object(bookMapper.mapBookToBookResponse(savedBook))
                .message(SuccessMessages.BOOK_SAVED)
                .httpStatus(HttpStatus.CREATED)
                .build();
    }


    private boolean isBookExistsByName(String bookName) {
        boolean isBookExists = bookRepository.findByNameIgnoreCase(bookName);

        if (isBookExists) {
            throw new ConflictException(String.format(ErrorMessages.BOOK_ALREADY_EXISTS_WITH_NAME, bookName));
        } else return false;

    }

    public ResponseMessage<BookResponse> updateBook(HttpServletRequest httpServletRequest, Long bookId, BookRequest bookRequest) {

        Book book = methodHelper.isBookExists(bookId);
        methodHelper.isCategoryExists(bookRequest.getCategoryId());
        methodHelper.isAuthorExistsById(bookRequest.getAuthorId());
        methodHelper.isPublisherExists(bookRequest.getPublisherId());

        Book updatedBook = bookRepository.save(bookMapper.mapBookUpdateRequestToBook(bookRequest, bookId));

        return ResponseMessage.<BookResponse>builder()
                .object(bookMapper.mapBookToBookResponse(updatedBook))
                .message(SuccessMessages.UPDATED_BOOK)
                .httpStatus(HttpStatus.CREATED)
                .build();
    }


    public ResponseMessage<BookResponse> deleteBook(Long bookId) {

        Book book = methodHelper.isBookExists(bookId);
        if (book.isLoanable()==false) {
            throw new RuntimeException(ErrorMessages.BOOK_CAN_NOT_BE_DELETED);
        }
        bookRepository.deleteById(bookId);
        return ResponseMessage.<BookResponse>builder()
                .object(bookMapper.mapBookToBookResponse(book))
                .message(SuccessMessages.BOOK_DELETED)
                .httpStatus(HttpStatus.OK)
                .build();
    }

}