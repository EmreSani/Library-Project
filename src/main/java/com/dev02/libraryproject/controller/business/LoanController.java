package com.dev02.libraryproject.controller.business;

import com.dev02.libraryproject.entity.concretes.business.Loan;
import com.dev02.libraryproject.payload.request.business.LoanRequest;
import com.dev02.libraryproject.payload.response.business.LoanResponse;
import com.dev02.libraryproject.payload.response.business.ResponseMessage;
import com.dev02.libraryproject.service.business.LoanService;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("/loans")
@RequiredArgsConstructor
public class LoanController {

    private final LoanService loanService;

    @PostMapping
    @PreAuthorize("hasAnyAuthority('ADMIN','EMPLOYEE')") // http://localhost:8080/loans
    public ResponseEntity<LoanResponse> createLoan(@RequestBody @Valid LoanRequest loanRequest){

        return ResponseEntity.ok(loanService.createLoan(loanRequest));
    }

    @GetMapping
    @PreAuthorize("hasAnyAuthority('MEMBER')") // http://localhost:8080/loans
    public ResponseEntity<Page<LoanResponse>> getAllLoansByMemberByPage(
            HttpServletRequest httpServletRequest,
            @RequestParam(value = "page", defaultValue = "0") int page,
            @RequestParam(value = "size",defaultValue = "20") int size,
            @RequestParam(value = "sort",defaultValue = "createDate") String sort,
            @RequestParam(value = "type",defaultValue = "desc") String type){
        return loanService.getAllLoansByMemberByPage(httpServletRequest,page,size,sort,type);
    }
}
