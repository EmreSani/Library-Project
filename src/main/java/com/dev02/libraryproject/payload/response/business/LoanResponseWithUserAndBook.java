package com.dev02.libraryproject.payload.response.business;

import com.dev02.libraryproject.entity.concretes.business.Book;
import com.dev02.libraryproject.entity.concretes.user.User;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class LoanResponseWithUserAndBook {


    private Long id;

    private Long userId;

    private Long bookId;

    private User user;

    private Book book;



}
