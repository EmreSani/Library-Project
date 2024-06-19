package com.dev02.libraryproject.controller.business;

import com.dev02.libraryproject.payload.mappers.AuthorMapper;
import com.dev02.libraryproject.payload.request.business.AuthorRequest;
import com.dev02.libraryproject.payload.response.business.AuthorResponse;
import com.dev02.libraryproject.payload.response.business.ResponseMessage;
import com.dev02.libraryproject.service.business.AuthorService;
import javax.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/authors")
@RequiredArgsConstructor
public class AuthorController {
    private final AuthorService authorService;
    private  final AuthorMapper authorMapper;


    @GetMapping// http://localhost:8080/authors/ +GET
    @PreAuthorize("hasAnyAuthority('Anonymous')")
    public ResponseEntity<Page<AuthorResponse>> getAuthorsByPage(
            @RequestParam(value = "page", defaultValue = "1") int page,
            @RequestParam(value = "size", defaultValue = "10") int size,
            @RequestParam(value = "sort", defaultValue = "name") String sort,
            @RequestParam(value = "type", defaultValue = "asc") String type
    ){

        Page<AuthorResponse> authorResponse = authorService.getAuthorByPage(page,size,sort,type);
        return new ResponseEntity<>(authorResponse, HttpStatus.OK) ;
    }

    @GetMapping("/{id}") // http://localhost:8080/authors/4 + GET
    @PreAuthorize("hasAnyAuthority('Anonymous')")
    public ResponseMessage<AuthorResponse> getAuthorById(@PathVariable Long id){
        return authorService.getAuthorById(id);
    }

    @PreAuthorize("hasAnyAuthority('Admin')")
    @PostMapping("/publishers")     //http://localhost:8080/authors/publishers  +POST +JSON
    public ResponseMessage<AuthorResponse> saveAuthor(@RequestBody @Valid AuthorRequest authorRequest){

        return authorService.saveAuthor(authorRequest);
    }

    @PreAuthorize("hasAnyAuthority('Admin')")
    @PutMapping("/{id}")
    public ResponseMessage<AuthorResponse> updateAuthor(@PathVariable Long id,
                                                        @RequestBody @Valid AuthorRequest authorRequest){
        return authorService.updateAuthor(id,authorRequest);
    }

    @PreAuthorize("hasAnyAuthority('Admin')")
    @DeleteMapping("/{id}")
    public ResponseMessage<AuthorResponse> deleteAuthor(@PathVariable Long id){
        return authorService.deleteAuthor(id);
    }

}
