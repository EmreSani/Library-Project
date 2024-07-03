package com.dev02.libraryproject.repository.business;


import com.dev02.libraryproject.payload.response.business.ReportResponse;


import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import org.springframework.stereotype.Repository;

//@Repository
//public interface ReportRepository extends JpaRepository<ReportResponse, Long> {
//
////    @Query("SELECT ReportResponse(" +
////            "(SELECT COUNT(b) FROM Book b), " +
////            "(SELECT COUNT(DISTINCT b.authorId) FROM Book b), " +
////            "(SELECT COUNT(DISTINCT b.publisherId) FROM Book b), " +
////            "(SELECT COUNT(DISTINCT b.categoryId) FROM Book b), " +
////            "(SELECT COUNT(l) FROM Loan l), " +
////            "(SELECT COUNT(b) FROM Book b WHERE b.loanable = false), " +
////            "(SELECT COUNT(b) FROM Book b WHERE b.active = false), " +
////            "(SELECT COUNT(u) FROM User u JOIN u.roles r WHERE r.roleType = :roleType)" +
////            ") " +
////            "FROM Book b")
////    ReportResponse getReportObject();
//
//}

