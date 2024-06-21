package com.dev02.libraryproject.controller.business;


import com.dev02.libraryproject.payload.response.business.BookResponse;
import com.dev02.libraryproject.payload.response.business.BookResponseForReport;
import com.dev02.libraryproject.payload.response.business.LoanResponse;
import com.dev02.libraryproject.payload.response.business.ResponseMessage;
import com.dev02.libraryproject.payload.response.user.UserResponse;

import com.dev02.libraryproject.service.business.ReportService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;

@RestController
@RequestMapping("/report")
@RequiredArgsConstructor
public class ReportController {

    private final ReportService reportService;

    //4. endpoint
    @GetMapping("/expired-books")
    @PreAuthorize("hasAnyAuthority('ADMIN','EMPLOYEE')") // http://localhost:8080/report/expired-books
    public ResponseEntity<Page<BookResponseForReport>> getAllExpiredBooksByPage(
            @RequestParam(value = "page", defaultValue = "0") int page,
            @RequestParam(value = "size", defaultValue = "20") int size,
            @RequestParam(value = "sort", defaultValue = "expiredDate") String sort,
            @RequestParam(value = "type", defaultValue = "desc") String type) {
        return reportService.getAllExpiredBooksByPage(page, size, sort, type);
    }

    //3.endpoint
    // http://localhost:8080/report/unreturned-books? page=1&size=10&sort=expireDate&type=desc
    @PostMapping("/report/unreturned-books")
    @PreAuthorize("hasAnyAuthority('ADMIN','EMPLOYEE')")
    public ResponseEntity<Page<BookResponseForReport>> getAllUnreturnedBooksByPage(
            @RequestParam(value = "page", defaultValue = "0") int page,
            @RequestParam(value = "size", defaultValue = "20") int size,
            @RequestParam(value = "sort", defaultValue = "expireDate") String sort,
            @RequestParam(value = "type", defaultValue = "desc") String type) {

        return reportService.getAllUnreturnedBooksByPage(page, size, sort, type);
    }

    //5.endpoint
    @GetMapping("/most-borrowers")
    @PreAuthorize("hasAnyAuthority('ADMIN','EMPLOYEE')") // http://localhost:8080/report/most-borrowers
    public ResponseEntity<Page<UserResponse>> getAllUsersMostBorrowersByPage(
            @RequestParam(value = "page", defaultValue = "0") int page,
            @RequestParam(value = "size", defaultValue = "20") int size)
            {
        return reportService.getAllUsersMostBorrowersByPage(page, size);
    }

    //1.ENDPOINT
    @GetMapping("/getObjects")
    @PreAuthorize("hasAnyAuthority('ADMIN','EMPLOYEE')")
    public ResponseMessage<ReportResponse> getReportObjects() {
        ReportResponse reportResponse = reportService.getReportObject();
        return ResponseMessage.<ReportResponse>builder()
                .object(reportResponse)
                .message("Report fetched successfully")
                .httpStatus(HttpStatus.OK)
                .build();
    }
//2.ENDPOINT
    @GetMapping("/most-popular-books/{amount}")
    @PreAuthorize("hasAnyAuthority('ADMIN','EMPLOYEE')")
    public ResponseMessage2<Page<ReportResponse,Integer>> getMostPopularBooks(
            @RequestParam(value = "amount", defaultValue = "10") int amount,
            @RequestParam(value = "page", defaultValue = "0") int page,
            @RequestParam(value = "size", defaultValue = "20") int size

//todo duzenlenecek
    ){
        return reportService.getMostPopularBooks(amount, page, size);
    }
}
