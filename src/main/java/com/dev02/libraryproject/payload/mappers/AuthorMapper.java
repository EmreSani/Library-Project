package com.dev02.libraryproject.payload.mappers;

import com.dev02.libraryproject.entity.concretes.business.Author;
import com.dev02.libraryproject.payload.response.business.AuthorResponse;

public class AuthorMapper {

    public AuthorResponse mapAuthorToAuthorResponse(Author author){
        return AuthorResponse.builder()
                .name(author.getName())
                .builtIn(author.getBuiltIn())
                .bookList(author.getBookList())
                .build();
    }


}
