package com.dev02.libraryproject.payload.response.business;

import com.dev02.libraryproject.entity.concretes.business.Book;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class LoanResponse {

    private Long id;

    private Long userId;

    private Long bookId;
    private Book book;
}
