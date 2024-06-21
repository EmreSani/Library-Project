package com.dev02.libraryproject.payload.mappers;

import com.dev02.libraryproject.entity.concretes.business.Author;
import com.dev02.libraryproject.payload.request.business.AuthorRequest;
import com.dev02.libraryproject.payload.response.business.AuthorResponse;
import org.springframework.stereotype.Component;

@Component
public class AuthorMapper {

    public AuthorResponse mapAuthorToAuthorResponse(Author author){
        return AuthorResponse.builder()
                .name(author.getName())
                .bookList(author.getBookList())
                .build();
    }

    public Author mapAuthorRequestToAuthor(AuthorRequest authorRequest) {
        return Author.builder()
                .name(authorRequest.getName())
                .builtIn(authorRequest.getBuiltIn())
                .build();
    }
}
