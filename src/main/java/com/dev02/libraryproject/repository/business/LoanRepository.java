package com.dev02.libraryproject.repository.business;

import com.dev02.libraryproject.entity.concretes.business.Loan;
import org.springframework.data.jpa.repository.JpaRepository;

public interface LoanRepository extends JpaRepository<Loan, Long> {
}
