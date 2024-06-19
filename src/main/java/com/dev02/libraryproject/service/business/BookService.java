package com.dev02.libraryproject.service.business;

import com.dev02.libraryproject.entity.concretes.business.Book;

import com.dev02.libraryproject.entity.concretes.user.User;
import com.dev02.libraryproject.entity.enums.RoleType;
import com.dev02.libraryproject.exception.ConflictException;
import com.dev02.libraryproject.payload.mappers.BookMapper;
import com.dev02.libraryproject.payload.messages.ErrorMessages;
import com.dev02.libraryproject.payload.messages.SuccessMessages;
import com.dev02.libraryproject.payload.request.business.BookRequest;
import com.dev02.libraryproject.payload.response.business.BookResponse;
import com.dev02.libraryproject.payload.response.business.ResponseMessage;
import com.dev02.libraryproject.repository.business.BookRepository;
import com.dev02.libraryproject.service.helper.MethodHelper;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletRequest;
import java.time.LocalDateTime;

@Service
@RequiredArgsConstructor
public class BookService {

    private final AuthorService authorService;
    private final BookRepository bookRepository;
    private final CategoryService categoryService;
    private final PublisherService publisherService;
    private final BookMapper bookMapper;
    private final MethodHelper methodHelper;

    public Page<BookResponse> getBooks(String query, Integer category, Long author, Integer publisher, Integer page, Integer size, String sort, String type) {
        authorService.isAuthorExistsById(author);

        return null;

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

        User admin = methodHelper.isUserExist(id);
        methodHelper.checkRole(admin, RoleType.ADMIN);


        categoryService.isCategoryExists(bookRequest.getCategoryId());
        authorService.isAuthorExistsById(bookRequest.getAuthorId());
        publisherService.isPublisherExists(bookRequest.getPublisherId());
        bookRequest.setCreateDate(LocalDateTime.now());

        Book savedBook = bookRepository.save(bookMapper.mapBookRequestToBook(bookRequest));
        //todo: check

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
//todo devam edilecek method -> update te neler kontrol edilecek?


    }

    public ResponseMessage<BookResponse> deleteBook(HttpServletRequest httpServlet, Long bookId) {
        Book book = methodHelper.isBookExists(bookId);

        bookRepository.deleteById(bookId);
        return ResponseMessage.<BookResponse>builder()
                .object(bookMapper.mapBookToBookResponse(book))
                .message(SuccessMessages.BOOK_DELETED)
                .httpStatus(HttpStatus.OK)
                .build();
    }
}