package com.dev02.libraryproject.repository.business;

import com.dev02.libraryproject.payload.response.business.BookResponse;
import com.dev02.libraryproject.payload.response.business.ReportResponse;
import com.dev02.libraryproject.payload.response.business.ResponseMessage;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface ReportRepository extends JpaRepository<ReportResponse, Long> {

    @Query("SELECT new com.dev02.libraryproject.payload.response.business.ReportResponse(" +
            "(SELECT COUNT(b) FROM Book b), " +
            "(SELECT COUNT(DISTINCT b.authorId) FROM Book b), " +
            "(SELECT COUNT(DISTINCT b.publisherId) FROM Book b), " +
            "(SELECT COUNT(DISTINCT b.categoryId) FROM Book b), " +
            "(SELECT COUNT(l) FROM Loan l), " +
            "(SELECT COUNT(b) FROM Book b WHERE b.loanable = false), " +
            "(SELECT COUNT(b) FROM Book b WHERE b.active = false), " +
            "(SELECT COUNT(m) FROM Member m)) " +
            "FROM Book b")
    ReportResponse getReportObject();
    @Query("SELECT b, count(b.loanId) From  Book b " +
            "GROUP BY b.name " +
            "ORDER BY COUNT(b.loanId) DESC")
    Page<ReportResponse> findAllPopularBooks(Pageable pageable, int amount);
}

