package com.dev02.libraryproject.controller.business;

import com.dev02.libraryproject.payload.request.business.BookRequest;
import com.dev02.libraryproject.payload.response.business.BookResponse;
import com.dev02.libraryproject.payload.response.business.ResponseMessage;
import com.dev02.libraryproject.service.business.BookService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;


@RestController
@RequestMapping("/books")
@RequiredArgsConstructor
public class BookController {

    private final BookService bookService;


    @GetMapping("/q")

    // http://localhost:8080/books?q=sefiller&cat=4&author=34&publisher=42&page=1&size=10&sort=name&type=asc
    public Page<BookResponse> getAllBookByPage(
            HttpServletRequest httpServletRequest,
            @RequestParam(name = "q", defaultValue = "sefiller") String query,
            @RequestParam(name = "cat", defaultValue = "4") Long categoryId,
            @RequestParam(name = "author", defaultValue = "34") Long authorId,
            @RequestParam(name = "publisher", defaultValue = "42") Long publisherId,
            @RequestParam(name = "page", defaultValue = "1") Integer page,
            @RequestParam(name = "size", defaultValue = "10") Integer size,
            @RequestParam(name = "sort", defaultValue = "name") String sort,
            @RequestParam(name = "type", defaultValue = "asc") String type) {

        return bookService.getBooks(httpServletRequest, query, categoryId, authorId, publisherId, page, size, sort, type);
    }


    @GetMapping("{/id}") // http://localhost:8080/books/5
    public ResponseMessage<BookResponse> getBookById(@RequestParam Long id) {

        return bookService.findBookById(id);

    }

    @PostMapping // http://localhost:8080/books
    @PreAuthorize("hasAnyAuthority('ADMIN')")
    public ResponseMessage<BookResponse> saveBook(HttpServletRequest httpServletRequest,
                                                  @RequestBody @Valid BookRequest bookRequest) {
        return bookService.saveBook(httpServletRequest, bookRequest);


    }

    @PutMapping("/{id}") // http://localhost:8080/books/5
    @PreAuthorize("hasAnyAuthority('ADMIN')")
    public ResponseMessage<BookResponse> updateBook(HttpServletRequest httpServletRequest,
                                                    @PathVariable Long bookId,
                                                    @Valid @RequestBody BookRequest bookRequest) {
        return bookService.updateBook(httpServletRequest, bookId, bookRequest);
    }

    @DeleteMapping("/{id}") // http://localhost:8080/books/5
    @PreAuthorize("hasAnyAuthority('ADMIN')")

    public ResponseMessage<BookResponse> deleteBook(@PathVariable Long bookId) {


        return bookService.deleteBook(bookId);
    }
}

