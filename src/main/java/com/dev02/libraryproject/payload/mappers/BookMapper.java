package com.dev02.libraryproject.payload.mappers;

import com.dev02.libraryproject.entity.concretes.business.Book;
import com.dev02.libraryproject.payload.request.business.BookRequest;
import com.dev02.libraryproject.payload.response.business.BookResponse;

import com.dev02.libraryproject.payload.response.business.BookResponseForReport;
import lombok.Data;
import org.springframework.stereotype.Component;

@Data
@Component
public class BookMapper {

    public BookResponse mapBookToBookResponse(Book book) {
        return BookResponse.builder()
                .name(book.getName())
                .isbn(book.getIsbn())
                .pageCount(book.getPageCount())
                .authorId(book.getAuthorId())
                .publisherId(book.getPublisherId())
                .publishDate(book.getPublishDate())
                .categoryId(book.getCategoryId())
                .image(book.getImage())
                .loanable(book.isLoanable())
                .shelfCode(book.getShelfCode())
                .active(book.isActive())
                .featured(book.isFeatured())
                .createDate(book.getCreateDate())
                .builtIn(book.isBuiltIn())
                .bookId(book.getBookId())
                .build();


    }

    public Book mapBookResponseToBook(BookResponse bookResponse) {
        return Book.builder()
                .name(bookResponse.getName())
                .isbn(bookResponse.getIsbn())
                .pageCount(bookResponse.getPageCount())
                .authorId(bookResponse.getAuthorId())
                .publisherId(bookResponse.getPublisherId())
                .publishDate(bookResponse.getPublishDate())
                .categoryId(bookResponse.getCategoryId())
                .image(bookResponse.getImage())
                .loanable(bookResponse.isLoanable())
                .shelfCode(bookResponse.getShelfCode())
                .active(bookResponse.isActive())
                .featured(bookResponse.isFeatured())
                .createDate(bookResponse.getCreateDate())
                .builtIn(bookResponse.isBuiltIn())
                .bookId(bookResponse.getBookId())
                .build();


    }

    public Book mapBookRequestToBook(BookRequest bookRequest) {
        return Book.builder()
                .name(bookRequest.getName())
                .isbn(bookRequest.getIsbn())
                .pageCount(bookRequest.getPageCount())
                .authorId(bookRequest.getAuthorId())
                .publisherId(bookRequest.getPublisherId())
                .publishDate(bookRequest.getPublishDate())
                .categoryId(bookRequest.getCategoryId())
                .image(bookRequest.getImage())
                .loanable(bookRequest.isLoanable())
                .shelfCode(bookRequest.getShelfCode())
                .active(bookRequest.isActive())
                .featured(bookRequest.isFeatured())
                .createDate(bookRequest.getCreateDate())
                .builtIn(bookRequest.isBuiltIn())
                .build();

    }


    public Book mapBookUpdateRequestToBook(BookRequest bookRequest, Long bookId) {

        return Book.builder()
                .id(bookId)
                .name(bookRequest.getName())
                .isbn(bookRequest.getIsbn())
                .pageCount(bookRequest.getPageCount())
                .authorId(bookRequest.getAuthorId())
                .publisherId(bookRequest.getPublisherId())
                .publishDate(bookRequest.getPublishDate())
                .categoryId(bookRequest.getCategoryId())
                .image(bookRequest.getImage())
                .loanable(bookRequest.isLoanable())
                .shelfCode(bookRequest.getShelfCode())
                .active(bookRequest.isActive())
                .featured(bookRequest.isFeatured())
                .createDate(bookRequest.getCreateDate())
                .builtIn(bookRequest.isBuiltIn())
                .build();

    }

    public BookResponseForReport mapBookToBookResponseForReport(Book book) {
        return BookResponseForReport.builder()
                .id(book.getId())
                .name(book.getName())
                .isbn(book.getIsbn())
                .build();
    }
}
