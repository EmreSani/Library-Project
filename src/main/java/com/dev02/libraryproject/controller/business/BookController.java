package com.dev02.libraryproject.controller.business;

import com.dev02.libraryproject.payload.response.business.BookResponse;
import com.dev02.libraryproject.service.business.BookService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/books")
@RequiredArgsConstructor
public class BookController {

    private final BookService bookService;


    @GetMapping("/q") // http://localhost:8080/books?q=sefiller&cat=4&author=34&publisher=42&page=1&size=10&sort=name&type=asc
    public Page<BookResponse> getAllBookByPage(
            @RequestParam(name = "q", defaultValue="sefiller") String query,
            @RequestParam(name = "cat", defaultValue="4") Integer category,
            @RequestParam(name = "author", defaultValue="34") Long author,
            @RequestParam(name = "publisher", defaultValue="42") Integer publisher,
            @RequestParam(name = "page", defaultValue = "1") Integer page,
            @RequestParam(name = "size", defaultValue = "10") Integer size,
            @RequestParam(name = "sort", defaultValue = "name") String sort,
            @RequestParam(name = "type", defaultValue = "asc") String type)
    {

        return bookService.getBooks(query, category, author, publisher, page, size, sort, type);
    }




}
