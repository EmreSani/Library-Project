package com.dev02.libraryproject.service.business;

import com.dev02.libraryproject.entity.concretes.business.Book;
import com.dev02.libraryproject.entity.concretes.business.Loan;
import com.dev02.libraryproject.entity.concretes.user.User;
import com.dev02.libraryproject.exception.BadRequestException;
import com.dev02.libraryproject.exception.ResourceNotFoundException;
import com.dev02.libraryproject.payload.mappers.LoanMapper;
import com.dev02.libraryproject.payload.messages.ErrorMessages;
import com.dev02.libraryproject.payload.messages.SuccessMessages;
import com.dev02.libraryproject.payload.request.business.LoanRequest;
import com.dev02.libraryproject.payload.response.business.LoanResponse;
import com.dev02.libraryproject.payload.response.business.ResponseMessage;
import com.dev02.libraryproject.repository.business.LoanRepository;
import com.dev02.libraryproject.service.helper.MethodHelper;
import com.dev02.libraryproject.service.helper.PageableHelper;
import com.dev02.libraryproject.service.user.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletRequest;
import java.time.LocalDateTime;
import java.util.List;

@Service
@RequiredArgsConstructor
public class LoanService {

    private final LoanRepository loanRepository;
    private final BookService bookService;
    private final UserService userService;
    private final MethodHelper methodHelper;
    private final LoanMapper loanMapper;
    private final PageableHelper pageableHelper;

    public LoanResponse createLoan(LoanRequest loanRequest) {

        Book book = bookService.getBookById(loanRequest.getBookId());
        User user = methodHelper.isUserExist(loanRequest.getUserId());

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
        Loan loan = loanMapper.mapLoanRequestToLoan(loanRequest);

        if((userScore==2 && userLoanListSize<5)){
            book.setLoanable(false);
            //loan.setExpireDate(LocalDateTime().now()+20);
            loan.setExpireDate(LocalDateTime.now().plusDays(20));

        } else if (userScore==1 && userLoanListSize<4) {
            book.setLoanable(false);
            //loan.setExpireDate(LocalDateTime().now()+15);
            loan.setExpireDate(LocalDateTime.now().plusDays(15));

        } else if (userScore==0 && userLoanListSize<3) {
            book.setLoanable(false);
            //loan.setExpireDate(LocalDateTime().now()+10);
            loan.setExpireDate(LocalDateTime.now().plusDays(10));

        } else if (userScore==-1 && userLoanListSize<2) {
            book.setLoanable(false);
            //loan.setExpireDate(LocalDateTime().now()+6);
            loan.setExpireDate(LocalDateTime.now().plusDays(6));

        } else if (userScore==-2 && userLoanListSize<1) {
            book.setLoanable(false);
            //loan.setExpireDate(LocalDateTime().now()+3);
            loan.setExpireDate(LocalDateTime.now().plusDays(3));
        } else {
            throw new BadRequestException(ErrorMessages.USER_CAN_NOT_LOAN);
        }
        user.getLoanList().add(loan); //user's loan list size added this loan
        //Loan is saving by using LoanRepository.save() methods
        loanRepository.save(loan);


        //Loan map to the LoanResponse
        LoanResponse loanResponse = loanMapper.mapLoanToLoanResponse(loan);
        return loanResponse;
        //method finished here.
        //The error is due to the lack of getBookById() methods from Book class.

    }

    public ResponseEntity<Page<LoanResponse>> getAllLoansByMemberByPage(
            HttpServletRequest httpServletRequest,
            int page, int size, String sort, String type) {

        Pageable pageable = pageableHelper.getPageableWithProperties(page, size, sort, type);
        User member = (User) httpServletRequest.getAttribute("email");

        return ResponseEntity.ok(loanRepository.findByUser_IdEquals(member.getId(), pageable).map(loanMapper::mapLoanToLoanResponse));
    }

    public ResponseMessage<LoanResponse> getLoanById(Long id) {
        isLoanExistsById(id);
        Loan loan = loanRepository.findByLoanId(id);
        return ResponseMessage.<LoanResponse>builder()
                .message(SuccessMessages.LOAN_FOUND)
                .httpStatus(HttpStatus.OK)
                .object(loanMapper.mapLoanToLoanResponse(loan))
                .build();
    }

    public Loan isLoanExistsById(Long id) {
        return  loanRepository.findById(id).orElseThrow(()->
                new ResourceNotFoundException(String.format(ErrorMessages.LOAN_NOT_FOUND, id)));
    }

    public ResponseEntity<Page<LoanResponse>> getAllLoansByUserIdByPage(Long userId, int page, int size, String sort, String type) {

        Pageable pageable = pageableHelper.getPageableWithProperties(page, size, sort, type);
        User user = methodHelper.isUserExist(userId);

        return ResponseEntity.ok(loanRepository.findAllLoansByUser_Id(user.getLoanList(), pageable).map(loanMapper::mapLoanToLoanResponse));
    }
}
