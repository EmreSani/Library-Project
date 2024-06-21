package com.dev02.libraryproject.payload.mappers;

import com.dev02.libraryproject.entity.concretes.business.Loan;
import com.dev02.libraryproject.payload.request.business.LoanRequest;
import com.dev02.libraryproject.payload.response.business.*;
import com.dev02.libraryproject.service.business.BookService;
import com.dev02.libraryproject.service.helper.MethodHelper;
import com.dev02.libraryproject.service.user.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@RequiredArgsConstructor
@Component
public class LoanMapper {

    private final BookResponse bookResponse;
    private final BookService bookService;
    private final UserService userService;
    private final MethodHelper methodHelper;

    public Loan mapLoanRequestToLoan(LoanRequest loanRequest){
        return Loan.builder().user(loanRequest.getUserId())
                .book(loanRequest.getBookId())
                .notes(loanRequest.getNotes())
                .build();
    }

    public LoanResponse mapLoanToLoanResponse(Loan loan){
        return LoanResponse.builder()
                .id(loan.getId())
                .userId(loan.getUser())
                .bookId(loan.getBook())
                .book(methodHelper.isBookExists(loan.getBook())) //her response içinde Book gönderiliyor
                                                                  //Eğer Loan Response larda gerekmeyen varsa ayrı bir mapper oluşturulacak
                .build();
    }

    public LoanResponseWithUser mapLoanToLoanResponseWithUser(Loan loan){
        return LoanResponseWithUser.builder()
                .id(loan.getId())
                .bookId(loan.getBook())
                .user(methodHelper.isUserExist(loan.getUser()))
                .build();
    }

    public LoanResponseWithUserAndBook mapLoanToLoanResponseWithUserAndBook(Loan loan){
        return LoanResponseWithUserAndBook.builder()
                .id(loan.getId())
                .userId(loan.getUser())
                .bookId(loan.getBook())
                .user(methodHelper.isUserExist(loan.getUser()))
                .book(methodHelper.isBookExists(loan.getBook()))
                .build();
    }

    public LoanResponseForUpdate mapLoanToLoanResponseForUpdate(Loan loan){
        return LoanResponseForUpdate.builder()
                .id(loan.getId())
                .userId(loan.getUser())
                .bookId(loan.getBook())
                .build();
    }


}
