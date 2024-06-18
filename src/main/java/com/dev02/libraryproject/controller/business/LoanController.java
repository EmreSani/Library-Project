package com.dev02.libraryproject.controller.business;

import com.dev02.libraryproject.payload.request.business.LoanRequest;
import com.dev02.libraryproject.payload.response.business.LoanResponse;
import com.dev02.libraryproject.payload.response.business.LoanResponseWithUser;
import com.dev02.libraryproject.payload.response.business.LoanResponseWithUserAndBook;
import com.dev02.libraryproject.payload.response.business.ResponseMessage;
import com.dev02.libraryproject.service.business.LoanService;

import lombok.RequiredArgsConstructor;
<<<<<<< HEAD
=======
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
>>>>>>> main
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;
import java.util.List;

import javax.validation.Valid;

@RestController
@RequestMapping("/loans")
@RequiredArgsConstructor
public class LoanController {

    private final LoanService loanService;

    @PostMapping
    @PreAuthorize("hasAnyAuthority('ADMIN','EMPLOYEE')") // http://localhost:8080/loans
    public ResponseEntity<LoanResponse> createLoan(@RequestBody @Valid LoanRequest loanRequest){

        return new ResponseEntity<>(loanService.createLoan(loanRequest), HttpStatus.CREATED);
    }

    @GetMapping
    @PreAuthorize("hasAnyAuthority('MEMBER')") // http://localhost:8080/loans
    public ResponseEntity<Page<LoanResponse>> getAllLoansByMemberByPage(
            HttpServletRequest httpServletRequest,
            @RequestParam(value = "page", defaultValue = "0") int page,
            @RequestParam(value = "size",defaultValue = "20") int size,
            @RequestParam(value = "sort",defaultValue = "loanDate") String sort,
            @RequestParam(value = "type",defaultValue = "desc") String type){
        return loanService.getAllLoansByMemberByPage(httpServletRequest,page,size,sort,type);
    }

    @GetMapping("/{id}")
    @PreAuthorize("hasAnyAuthority('MEMBER')") // http://localhost:8080/loans/2
    public ResponseMessage<LoanResponse> getLoanByIdWithMember(@PathVariable Long id, HttpServletRequest httpServletRequest){
        return loanService.getLoanByIdWithMember(id, httpServletRequest);
    }


    @GetMapping("/user/{userId}")
    @PreAuthorize("hasAnyAuthority('EMPLOYEE','ADMIN')") // http://localhost:8080/loans/user/3
    public ResponseEntity<Page<LoanResponse>> getAllLoansByUserIdByPage(
            @PathVariable Long userId,
            @RequestParam(value = "page", defaultValue = "0") int page,
            @RequestParam(value = "size",defaultValue = "20") int size,
            @RequestParam(value = "sort",defaultValue = "loanDate") String sort,
            @RequestParam(value = "type",defaultValue = "desc") String type){
        return loanService.getAllLoansByUserIdByPage(userId,page,size,sort,type);
    }

    @GetMapping("/book/{bookId}")
    @PreAuthorize("hasAnyAuthority('EMPLOYEE','ADMIN')") // http://localhost:8080/loans/book/3
    public ResponseEntity<Page<LoanResponseWithUser>> getAllLoansByBookIdByPage(
            @PathVariable Long bookId,
            @RequestParam(value = "page", defaultValue = "0") int page,
            @RequestParam(value = "size",defaultValue = "20") int size,
            @RequestParam(value = "sort",defaultValue = "loanDate") String sort,
            @RequestParam(value = "type",defaultValue = "desc") String type){
        return loanService.getAllLoansByBookIdByPage(bookId,page,size,sort,type);
    }

    @GetMapping("/auth/{loanId}")
    @PreAuthorize("hasAnyAuthority('EMPLOYEE','ADMIN')") // http://localhost:8080/loans/auth/3
    public ResponseEntity<LoanResponseWithUserAndBook> getLoanById(
            @PathVariable Long loanId){
        return loanService.getLoanById(loanId);
    }






}
