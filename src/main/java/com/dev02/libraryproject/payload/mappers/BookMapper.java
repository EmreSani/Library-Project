package com.dev02.libraryproject.payload.mappers;

import com.dev02.libraryproject.entity.concretes.business.Book;
import com.dev02.libraryproject.payload.response.business.BookResponse;

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
                .shelfCode(book.getShelfCode())
                .active(book.isActive())
                .featured(book.isFeatured())
                .loanable(book.isLoanable())
                .bookId(book.getBookId())
                .builtIn(book.isBuiltIn())
                .build();
    }
}
