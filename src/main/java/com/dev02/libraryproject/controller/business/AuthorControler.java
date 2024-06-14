package com.dev02.libraryproject.controller.business;

import com.dev02.libraryproject.payload.response.business.AuthorResponse;
import com.dev02.libraryproject.payload.response.business.ResponseMessage;
import com.dev02.libraryproject.service.business.AuthorService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/authors")
@RequiredArgsConstructor
public class AuthorControler {
    private final AuthorService authorService;


    @GetMapping// http://localhost:8080/authors/ +GET
    public ResponseEntity<Page<AuthorResponse>> getAuthorsByPage(
            @RequestParam(value = "page", defaultValue = "1") int page,
            @RequestParam(value = "size", defaultValue = "10") int size,
            @RequestParam(value = "sort", defaultValue = "name") String sort,
            @RequestParam(value = "type", defaultValue = "asc") String type
    ){

        Page<AuthorResponse> authorsResponses = authorService.getAuthorsByPage(page,size,sort,type);
        return new ResponseEntity<>(authorsResponses, HttpStatus.OK) ;
    }

    @GetMapping("/{id}") // http://localhost:8080/authors/4 + GET
    public ResponseMessage<AuthorResponse> getAuthorById(@PathVariable Long id){
        return authorService.getAuthorById(id);
    }



}
