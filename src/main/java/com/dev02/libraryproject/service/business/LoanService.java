package com.dev02.libraryproject.service.business;

import com.dev02.libraryproject.entity.concretes.business.Book;
import com.dev02.libraryproject.entity.concretes.business.Loan;
import com.dev02.libraryproject.entity.concretes.user.User;
import com.dev02.libraryproject.exception.BadRequestException;
import com.dev02.libraryproject.payload.messages.ErrorMessages;
import com.dev02.libraryproject.payload.request.business.LoanRequest;
import com.dev02.libraryproject.payload.response.business.LoanResponse;
import com.dev02.libraryproject.repository.business.LoanRepository;
import com.dev02.libraryproject.service.user.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@Service
@RequiredArgsConstructor
public class LoanService {

    private final LoanRepository loanRepository;
    private final BookService bookService;
    private final UserService userService;

    public LoanResponse createLoan(LoanRequest loanRequest) {

        Book book = bookService.getBookById(loanRequest.getBookId());
        User user = userService.getUserById(loanRequest.getUserId());

        if(!book.isLoanable()){
            throw new BadRequestException(ErrorMessages.BOOK_NOT_LOANABLE);
        }

        for(Loan loan : user.getLoanList()){
            if(LocalDateTime.now().isAfter(loan.getExpireDate())){
                throw new BadRequestException(String.format(ErrorMessages.USER_HAS_EXPIRE_LOAN, String.valueOf(loan.getId())));
            }
        }

        int userScore = user.getScore();
        int userLoanListSize = user.getLoanList().size();

        //Mapper oluşturulup devam edilecek


        if((userScore==2 && userLoanListSize<5)){
            book.setLoanable(false);
            //loan.setExpireDate(LocalDateTime().now()+20);

        } else if (userScore==1 && userLoanListSize<4) {
            book.setLoanable(false);
            //loan.setExpireDate(LocalDateTime().now()+15);

        } else if (userScore==0 && userLoanListSize<3) {
            book.setLoanable(false);

        } else if (userScore==-1 && userLoanListSize<2) {
            book.setLoanable(false);

        } else if (userScore==-2 && userLoanListSize<1) {
            book.setLoanable(false);

        }

    }
}
