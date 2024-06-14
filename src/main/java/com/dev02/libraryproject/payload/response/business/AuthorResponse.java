package com.dev02.libraryproject.payload.response.business;


import com.dev02.libraryproject.entity.concretes.business.Book;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;


@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder(toBuilder = true)
public class AuthorResponse {

    private String name;
    private Boolean builtIn;
    private List<Book> bookList;


}
