package com.dev02.libraryproject.payload.response.business;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ReportResponseWithBookName {
    private String bookName;
    private long loanCount;
}
