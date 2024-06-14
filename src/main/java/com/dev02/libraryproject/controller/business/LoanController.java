package com.dev02.libraryproject.controller.business;

import com.dev02.libraryproject.payload.request.business.LoanRequest;
import com.dev02.libraryproject.payload.response.business.LoanResponse;
import com.dev02.libraryproject.service.business.LoanService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.RequestEntity;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/loans")
@RequiredArgsConstructor
public class LoanController {

    private final LoanService loanService;

    @PostMapping
    @PreAuthorize("hasAnyAuthority('Admin','Employee')") // http://localhost:8080/loans
    public ResponseEntity<LoanResponse> createLoan(@RequestBody @Valid LoanRequest loanRequest){

        return ResponseEntity.ok(loanService.createLoan(loanRequest));
    }
}
