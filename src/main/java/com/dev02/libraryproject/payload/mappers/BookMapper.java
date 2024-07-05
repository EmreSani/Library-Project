package com.dev02.libraryproject.payload.mappers;

import com.dev02.libraryproject.entity.concretes.business.Book;
import com.dev02.libraryproject.payload.request.business.BookRequest;
import com.dev02.libraryproject.payload.response.business.BookResponse;

import com.dev02.libraryproject.payload.response.business.BookResponseForLoan;
import com.dev02.libraryproject.payload.response.business.BookResponseForReport;
import com.dev02.libraryproject.service.helper.MethodHelper;
import lombok.Data;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;


@Component
public class BookMapper {

    @Autowired
    private MethodHelper methodHelper;

    public BookResponse mapBookToBookResponse(Book book) {
        return BookResponse.builder()
                .name(book.getName())
                .isbn(book.getIsbn())
                .pageCount(book.getPageCount())
                .authorId(book.getAuthor().getId())
                .publisherId(book.getPublisher().getId())
                .publishDate(book.getPublishDate())
                .categoryId(book.getCategory().getId())
                .image(book.getImage())
                .loanable(book.isLoanable())
                .shelfCode(book.getShelfCode())
                .active(book.isActive())
                .featured(book.isFeatured())
                .createDate(book.getCreateDate())
                .builtIn(book.isBuiltIn())
                .bookId(book.getLoans())
                .build();


    }

    public Book mapBookResponseToBook(BookResponse bookResponse) {
        return Book.builder()
                .name(bookResponse.getName())
                .isbn(bookResponse.getIsbn())
                .pageCount(bookResponse.getPageCount())
                .author(methodHelper.isAuthorExistsById(bookResponse.getAuthorId()))
                .publisher(methodHelper.isPublisherExists(bookResponse.getPublisherId()))
                .publishDate(bookResponse.getPublishDate())
                .category(methodHelper.isCategoryExists(bookResponse.getCategoryId()))
                .image(bookResponse.getImage())
                .loanable(bookResponse.isLoanable())
                .shelfCode(bookResponse.getShelfCode())
                .active(bookResponse.isActive())
                .featured(bookResponse.isFeatured())
                .createDate(bookResponse.getCreateDate())
                .builtIn(bookResponse.isBuiltIn())
                .loans(bookResponse.getBookId())
                .build();


    }

    public Book mapBookRequestToBook(BookRequest bookRequest) {
        return Book.builder()
                .name(bookRequest.getName())
                .isbn(bookRequest.getIsbn())
                .pageCount(bookRequest.getPageCount())
                .author(methodHelper.isAuthorExistsById(bookRequest.getAuthorId()))
                .publisher(methodHelper.isPublisherExists(bookRequest.getPublisherId()))
                .publishDate(bookRequest.getPublishDate())
                .featured(bookRequest.isFeatured())
       //         .createDate(bookRequest.getCreateDate()) entity classında pre persist ile çöz.
                .builtIn(bookRequest.isBuiltIn())
                .category(methodHelper.isCategoryExists(bookRequest.getCategoryId()))
                .image(bookRequest.getImage())
                .loanable(bookRequest.isLoanable())
                .shelfCode(bookRequest.getShelfCode())
                .active(bookRequest.isActive())
                .build();

    }


    public Book mapBookUpdateRequestToBook(BookRequest bookRequest, Long bookId) {

        return Book.builder()
                .id(bookId)
                .name(bookRequest.getName())
                .isbn(bookRequest.getIsbn())
                .pageCount(bookRequest.getPageCount())
                .author(methodHelper.isAuthorExistsById(bookRequest.getAuthorId()))
                .publisher(methodHelper.isPublisherExists(bookRequest.getPublisherId()))
                .publishDate(bookRequest.getPublishDate())
                .featured(bookRequest.isFeatured())
           //     .createDate(bookRequest.getCreateDate())
                .builtIn(bookRequest.isBuiltIn())
                .category(methodHelper.isCategoryExists(bookRequest.getCategoryId()))
                .image(bookRequest.getImage())
                .loanable(bookRequest.isLoanable())
                .shelfCode(bookRequest.getShelfCode())
                .active(bookRequest.isActive())
                .build();

    }

    public BookResponseForReport mapBookToBookResponseForReport(Book book) {
        return BookResponseForReport.builder()
                .id(book.getId())
                .name(book.getName())
                .isbn(book.getIsbn())
                .build();
    }
    public BookResponseForReport mapBookToBookResponseForReportMostPopular(Book book, int amount) {
        return BookResponseForReport.builder()
                .id(book.getId())
                .name(book.getName())
                .isbn(book.getIsbn())
                .amount(amount)
                .build();
    }

    public BookResponseForLoan mapBookToBookResponseForLoan(Book book){

        return BookResponseForLoan.builder()
                .name(book.getName())
                .isbn(book.getIsbn())
                .pageCount(book.getPageCount())
                .authorId(book.getAuthor().getId())
                .publisherId(book.getPublisher().getId())
                .publishDate(book.getPublishDate())
                .categoryId(book.getCategory().getId())
                .image(book.getImage())
                .loanable(book.isLoanable())
                .shelfCode(book.getShelfCode())
                .active(book.isActive())
                .featured(book.isFeatured())
                .createDate(book.getCreateDate())
                .builtIn(book.isBuiltIn())
                .build();

    }
}
