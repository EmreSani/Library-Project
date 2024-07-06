package com.dev02.libraryproject.service.business;


import com.dev02.libraryproject.entity.concretes.business.Book;
import com.dev02.libraryproject.entity.concretes.business.Loan;
import com.dev02.libraryproject.entity.enums.RoleType;
import com.dev02.libraryproject.exception.ResourceNotFoundException;
import com.dev02.libraryproject.payload.mappers.BookMapper;
import com.dev02.libraryproject.payload.messages.ErrorMessages;
import com.dev02.libraryproject.payload.response.business.BookResponseForReport;

import com.dev02.libraryproject.payload.response.business.ReportResponse;
import com.dev02.libraryproject.payload.response.user.UserResponse;

import com.dev02.libraryproject.service.helper.MethodHelper;
import com.dev02.libraryproject.service.helper.PageableHelper;
import com.dev02.libraryproject.service.user.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class ReportService {

    private final PageableHelper pageableHelper;
    private final MethodHelper methodHelper;
    private final BookMapper bookMapper;
    private final UserService userService;
    private final BookService bookService;
    private final AuthorService authorService;
    private final PublisherService publisherService;
    private final CategoryService categoryService;
    private final LoanService loanService;


    public ResponseEntity<Page<BookResponseForReport>> getAllExpiredBooksByPage(int page, int size, String sort, String type) {

        Pageable pageable = pageableHelper.getPageableWithProperties(page, size, sort, type);

        List<BookResponseForReport> expiredBooks = new ArrayList<>();

        for (Loan loan : methodHelper.getAllLoans()) {

            if (loan.getExpireDate().isBefore(LocalDateTime.now()) && loan.getReturnDate() == null) {

                Book expiredBook = methodHelper.isBookExists(loan.getBook().getId());
                BookResponseForReport expiredBookForReport = bookMapper.mapBookToBookResponseForReport(expiredBook);
                expiredBooks.add(expiredBookForReport);


            }
        }
        if(expiredBooks.size()==0){
            throw new ResourceNotFoundException(ErrorMessages.EXRPIRED_BOOK_NOT_FOUND);
        }

        Page<BookResponseForReport> expiredBooksPage = new PageImpl<>(expiredBooks, pageable, expiredBooks.size());

        return ResponseEntity.ok(expiredBooksPage);

    }


    public ResponseEntity<Page<BookResponseForReport>> getAllUnreturnedBooksByPage(int page, int size, String sort, String type) {

        Pageable pageable = pageableHelper.getPageableWithProperties(page, size, sort, type);

        List<BookResponseForReport> unreturnedBooks = new ArrayList<>();

        for (Loan loan : methodHelper.getAllLoans()) {

            if (loan.getExpireDate().isAfter(LocalDateTime.now()) && loan.getReturnDate() == null) {

                Book unreturnedBook = methodHelper.isBookExists(loan.getBook().getId());
                BookResponseForReport unreturnedBookForReport = bookMapper.mapBookToBookResponseForReport(unreturnedBook);
                unreturnedBooks.add(unreturnedBookForReport);
            }

        }

        if(unreturnedBooks.isEmpty()){
            throw new ResourceNotFoundException(ErrorMessages.EXRPIRED_BOOK_NOT_FOUND);
        }

        Page<BookResponseForReport> unreturnedBooksPage = new PageImpl<>(unreturnedBooks, pageable, unreturnedBooks.size());

        return ResponseEntity.ok(unreturnedBooksPage);

    }

    public ResponseEntity<Page<UserResponse>> getAllUsersMostBorrowersByPage(int page, int size) {
      return userService.getAllUsersMostBorrowersByPage(page, size);

    }

    public ResponseEntity<Page<BookResponseForReport>> getAllBookMostPopularByPage(int page, int size) {
        return bookService.getAllBookMostPopularByPage(page,size);
    }

    public ReportResponse getReport() {
        long numberOfBooks = bookService.countBooks();
        long numberOfAuthors = authorService.countAuthors();
        long numberOfPublishers = publisherService.countPublishers();
        long numberOfCategories = categoryService.countCategories();
        long numberOfLoans = loanService.countLoans();
        long numberOfUnreturnedBooks = loanService.countUnreturnedBooks();
        long numberOfExpiredBooks = loanService.countExpiredBooks();
        long numberOfMembers = userService.countAllMembers();

        return new ReportResponse((int) numberOfBooks, (int) numberOfAuthors, (int) numberOfPublishers, (int) numberOfCategories,
                (int) numberOfLoans, (int) numberOfUnreturnedBooks, (int) numberOfExpiredBooks, (int) numberOfMembers);
    }
}


