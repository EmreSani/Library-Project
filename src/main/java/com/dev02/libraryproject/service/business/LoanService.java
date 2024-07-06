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
import com.dev02.libraryproject.payload.request.business.LoanRequestForReturnDate;
import com.dev02.libraryproject.payload.request.business.LoanRequestForUpdateExpireDate;
import com.dev02.libraryproject.payload.response.business.*;
import com.dev02.libraryproject.repository.business.LoanRepository;
import com.dev02.libraryproject.repository.user.UserRepository;
import com.dev02.libraryproject.service.helper.MethodHelper;
import com.dev02.libraryproject.service.helper.PageableHelper;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletRequest;
import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;

@Service
@RequiredArgsConstructor
public class LoanService {

    private final LoanRepository loanRepository;
    private final BookService bookService;
    private final MethodHelper methodHelper;
    private final LoanMapper loanMapper;
    private final PageableHelper pageableHelper;
    private final UserRepository userRepository;

    public LoanResponse createLoan(LoanRequest loanRequest) {

        Book book = methodHelper.isBookExists(loanRequest.getBookId());
        User user = methodHelper.isUserExist(loanRequest.getUserId());

        if(!book.isLoanable()){
            throw new BadRequestException(ErrorMessages.BOOK_NOT_LOANABLE);
        }

        for(Loan loan : user.getLoanList()){
            if(LocalDateTime.now().isAfter(loan.getExpireDate())){
                throw new BadRequestException(String.format(ErrorMessages.USER_HAS_EXPIRE_LOAN, loan.getId()));
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
        loan.setLoanDate(LocalDateTime.now()); //todo: pre persist düşünülebilir
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

        String email = (String) httpServletRequest.getAttribute("username");

        User member = userRepository.findByEmail(email); //userrepodan işlem yapmak yerine user service üzerinden işlem yapılabilir.

        return ResponseEntity.ok(loanRepository.findByUserId(member.getId(), pageable).map(loanMapper::mapLoanToLoanResponse));
    }

    public ResponseMessage<LoanResponse> getLoanByIdWithMember(Long loanId, HttpServletRequest httpServletRequest) {
        String email = (String) httpServletRequest.getAttribute("username");

        User foundUser = userRepository.findByEmail(email);

        Loan loan = isLoanExistsById(loanId);

        if(!foundUser.getLoanList().contains(loan)){
            throw new BadRequestException(String.format(ErrorMessages.LOAN_NOT_FOUND_BY_USER,foundUser.getId()));
        }
        return ResponseMessage.<LoanResponse>builder()
                .message(SuccessMessages.LOAN_FOUND)
                .httpStatus(HttpStatus.OK)
                .object(loanMapper.mapLoanToLoanResponse(loan))
                .build();
    }

    //Diger classlarda kullanılacaksa methodHelper a tasınabilir.
    public Loan isLoanExistsById(Long id) {
        return  loanRepository.findById(id).orElseThrow(()->
                new ResourceNotFoundException(String.format(ErrorMessages.LOAN_NOT_FOUND, id)));
    }

    public ResponseEntity<Page<LoanResponse>> getAllLoansByUserIdByPage(Long userId, int page, int size, String sort, String type) {
        Pageable pageable = pageableHelper.getPageableWithProperties(page, size, sort, type);
        methodHelper.isUserExist(userId);

        Page<LoanResponse> loans = loanRepository.findByUserId(userId, pageable).map(loanMapper::mapLoanToLoanResponse);
        return ResponseEntity.ok(loans);
    }

    public ResponseEntity<Page<LoanResponseWithUser>> getAllLoansByBookIdByPage(Long bookId, int page, int size, String sort, String type) {
        Pageable pageable = pageableHelper.getPageableWithProperties(page, size, sort, type);
        methodHelper.isBookExists(bookId);

        return ResponseEntity.ok(loanRepository.findByBookId(bookId, pageable).map(loanMapper::mapLoanToLoanResponseWithUser));

    }


    public ResponseEntity<LoanResponseWithUserAndBook> getLoanById(Long loanId) {
        Loan foundLoan = isLoanExistsById(loanId);
        return ResponseEntity.ok(loanMapper.mapLoanToLoanResponseWithUserAndBook(foundLoan));

    }

    public ResponseEntity<LoanResponseForUpdate> updateLoanForExpireDateById(Long loanId, LoanRequestForUpdateExpireDate loanRequestForUpdateExpireDate) {
        Loan foundLoan = isLoanExistsById(loanId);
        User user = methodHelper.isUserExist(foundLoan.getUser().getId());

        if(foundLoan.getReturnDate()==null){ //teslim tarihini uzatma talebi varsa
            LocalDateTime requestExpDate = loanRequestForUpdateExpireDate.getExpireDate();
            LocalDateTime loanExpDate = foundLoan.getExpireDate();

            // İki tarih-saat arasındaki farkı gün olarak hesapla
            long daysBetween = ChronoUnit.DAYS.between(requestExpDate, loanExpDate);

//           LocalDateTime mevcutExpireDate = foundLoan.getExpireDate();
//           foundLoan.setExpireDate(mevcutExpireDate.plusDays(20));

            if ((foundLoan.getExpireDate().isAfter(LocalDateTime.now())&&daysBetween<20) && loanRequestForUpdateExpireDate.getExpireDate().isAfter(LocalDateTime.now())) {
                foundLoan.setExpireDate(loanRequestForUpdateExpireDate.getExpireDate());
                foundLoan.setNotes(loanRequestForUpdateExpireDate.getNotes());
            } else {
                throw new BadRequestException(ErrorMessages.NOT_PERMITTED_METHOD_MESSAGE);
            }
        } else throw new BadRequestException(ErrorMessages.NOT_PERMITTED_METHOD_MESSAGE);

        loanRepository.save(foundLoan);
        return ResponseEntity.ok(loanMapper.mapLoanToLoanResponseForUpdate(foundLoan));
    }

    public long countLoans() {
        return loanRepository.count();
    }

    public long countUnreturnedBooks() {
        return loanRepository.countByReturnDateIsNull();
    }

    public long countExpiredBooks() {
        return loanRepository.countByExpireDateBefore(LocalDateTime.now());
    }

    //todo: iade edilmiş kitap başkası tarafından ödünç alınmadıysa tekrar iade edilemesin.
    public ResponseEntity<LoanResponseForUpdate> updateLoanForReturnDateById(Long id, LoanRequestForReturnDate loanRequestForReturnDate) {
        Loan foundLoan = isLoanExistsById(id);

        if(foundLoan.getReturnDate()==null){ //kitabı iade ediyorsa veya önceki iade alma işlemini güncelliyorsa
            //User ve book aktif loan olması durumunda getirilsin, aksi takdirde db yorulmasın.
            User user = methodHelper.isUserExist(foundLoan.getUser().getId());
            Book foundBook = methodHelper.isBookExists(foundLoan.getBook().getId());
            foundBook.setLoanable(true);
            foundLoan.setReturnDate(LocalDateTime.now());
            foundLoan.setNotes(loanRequestForReturnDate.getNotes());
            bookService.updateReturnedBook(foundBook);
            if(foundLoan.getExpireDate().isAfter(LocalDateTime.now()) || foundLoan.getExpireDate().equals(LocalDateTime.now())){
                if (user.getScore()<2){
                    user.setScore(user.getScore()+1);
                }
            } else {
                if (user.getScore()>-2){
                    user.setScore(user.getScore()-1);
                }
            }
        } else throw new BadRequestException(ErrorMessages.NOT_PERMITTED_METHOD_MESSAGE);
        loanRepository.save(foundLoan);
        return ResponseEntity.ok(loanMapper.mapLoanToLoanResponseForUpdate(foundLoan));
    }
}
