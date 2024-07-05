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

    private final MethodHelper methodHelper;
    private final UserMapper userMapper;
    private final BookMapper bookMapper;

    public Loan mapLoanRequestToLoan(LoanRequest loanRequest){
        return Loan.builder().user(methodHelper.isUserExist(loanRequest.getUserId()))
                .book(methodHelper.isBookExists(loanRequest.getBookId()))
                .notes(loanRequest.getNotes())
                .build();
    }

    public LoanResponse mapLoanToLoanResponse(Loan loan){
        return LoanResponse.builder()
                .id(loan.getId())
                .userId(loan.getUser().getId())
                .bookId(loan.getBook().getId())
                .loanDate(loan.getLoanDate())
                .expireDate(loan.getExpireDate())
                .returnDate(loan.getReturnDate())
                .notes(loan.getNotes())
                .build();
    }

    public LoanResponseWithUser mapLoanToLoanResponseWithUser(Loan loan){
        return LoanResponseWithUser.builder()
                .id(loan.getId())
                .bookId(loan.getBook().getId())
                .user(methodHelper.isUserExist(loan.getUser().getId()))
                .build();
    }

    public LoanResponseWithUserAndBook mapLoanToLoanResponseWithUserAndBook(Loan loan){
        return LoanResponseWithUserAndBook.builder()
                .id(loan.getId())
                .userId(loan.getUser().getId())
                .bookId(loan.getBook().getId())
                .userInfos(userMapper.mapUserToUserResponse(methodHelper.isUserExist(loan.getUser().getId())))
                .bookResponseForLoan(bookMapper.mapBookToBookResponseForLoan(methodHelper.isBookExists(loan.getBook().getId())))
                //.user(methodHelper.isUserExist(loan.getUser().getId()))
                //.book(methodHelper.isBookExists(loan.getBook().getId()))
                .build();
    }

    public LoanResponseForUpdate mapLoanToLoanResponseForUpdate(Loan loan){
        return LoanResponseForUpdate.builder()
                .id(loan.getId())
                .userId(loan.getUser().getId())
                .bookId(loan.getBook().getId())
                .build();
    }


}
