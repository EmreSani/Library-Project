package com.dev02.libraryproject.payload.response.business;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ReportResponse {
    private int books;
    private int authors;
    private int publishers;
    private int categories;
    private int loans;
    private int unReturnedBooks;
    private int expiredBooks;
    private int members;
}