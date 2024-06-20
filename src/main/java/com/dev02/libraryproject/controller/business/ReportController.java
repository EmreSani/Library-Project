package com.dev02.libraryproject.controller.business;

import com.dev02.libraryproject.payload.response.business.BookResponse;
import com.dev02.libraryproject.payload.response.business.BookResponseForReport;
import com.dev02.libraryproject.payload.response.business.LoanResponse;
import com.dev02.libraryproject.service.business.ReportService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;

@RestController
@RequestMapping("/report")
@RequiredArgsConstructor
public class ReportController {

    private final ReportService reportService;

    @GetMapping("/expired-books")
    @PreAuthorize("hasAnyAuthority('ADMIN','EMPLOYEE')") // http://localhost:8080/report/expired-books
    public ResponseEntity<Page<BookResponseForReport>> getAllExpiredBooksByPage(
            @RequestParam(value = "page", defaultValue = "0") int page,
            @RequestParam(value = "size", defaultValue = "20") int size,
            @RequestParam(value = "sort", defaultValue = "expiredDate") String sort,
            @RequestParam(value = "type", defaultValue = "desc") String type) {
        return reportService.getAllExpiredBooksByPage(page, size, sort, type);
    }

}
