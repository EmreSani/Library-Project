package com.dev02.libraryproject.repository.business;

import com.dev02.libraryproject.entity.concretes.business.Loan;
import com.dev02.libraryproject.payload.response.business.LoanResponse;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface LoanRepository extends JpaRepository<Loan, Long> {
    Page<Loan> findByUser_IdEquals(Long id, Pageable pageable);

    Loan findByLoanId(Long id);

    Page<Loan> findAllLoansByUser_Id(List<Loan> userLoanList, Pageable pageable);
}
