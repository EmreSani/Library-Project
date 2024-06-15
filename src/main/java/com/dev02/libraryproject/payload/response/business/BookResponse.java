package com.dev02.libraryproject.payload.response.business;

import com.dev02.libraryproject.entity.concretes.business.Loan;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.File;
import java.time.LocalDateTime;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class BookResponse {


    private String name;

    private String isbn;

    private int pageCount;

    private Long authorId;

    private Long publisherId;

    private int publishDate;

    private Long categoryId;

    private File image;

    private boolean loanable = true;

    private String shelfCode;

    private boolean active = true;

    private boolean featured;


    private LocalDateTime createDate;

    private boolean builtIn;

    private List<Loan> bookId;
}
