package com.dev02.libraryproject.payload.response.business;


import com.dev02.libraryproject.entity.concretes.user.User;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class LoanResponseWithUser {

    private Long id;

    private Long bookId;

    private User user;

}
