package com.dev02.libraryproject.controller.business;

import com.dev02.libraryproject.payload.request.business.AuthorRequest;
import com.dev02.libraryproject.payload.request.business.PublisherRequest;
import com.dev02.libraryproject.payload.response.business.AuthorResponse;
import com.dev02.libraryproject.payload.response.business.PublisherResponse;
import com.dev02.libraryproject.payload.response.business.ResponseMessage;
import com.dev02.libraryproject.service.business.PublisherService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RestController
@RequestMapping("/publishers")
@RequiredArgsConstructor
public class PublisherController {

    private final PublisherService publisherService;


    @GetMapping// http://localhost:8080/publishers/    +GET
    public ResponseEntity<Page<PublisherResponse>> getAuthorsByPage(
            @RequestParam(value = "page", defaultValue = "0") int page,
            @RequestParam(value = "size", defaultValue = "20") int size,
            @RequestParam(value = "sort", defaultValue = "name") String sort,
            @RequestParam(value = "type", defaultValue = "asc") String type
    ){

        Page<PublisherResponse> publishersResponse = publisherService.getPublishersByPage(page,size,sort,type);
        return ResponseEntity.ok(publishersResponse);
    }

    @GetMapping("/{id}") // http://localhost:8080/publishers/1 + GET
    public ResponseMessage<PublisherResponse> getPublisherById(@PathVariable Long id){
        return publisherService.getPublisherById(id);
    }

    @PreAuthorize("hasAnyAuthority('Admin')")
    @PostMapping     //http://localhost:8080/publishers  +POST +JSON
    public ResponseMessage<PublisherResponse> savePublisher(@RequestBody @Valid PublisherRequest publisherRequest){

        return publisherService.savePublisher(publisherRequest);
    }

    @PreAuthorize("hasAnyAuthority('Admin')")
    @PutMapping("/{id}")
    public ResponseMessage<PublisherResponse> updatePublisher(@PathVariable Long id,
                                                        @RequestBody @Valid PublisherRequest publisherRequest){
        return publisherService.updatePublisher(id,publisherRequest);
    }

    @PreAuthorize("hasAnyAuthority('Admin')")
    @DeleteMapping("/{id}")
    public ResponseMessage<PublisherResponse> deletePublisher(@PathVariable Long id){
        return publisherService.deletePublisher(id);
    }

}
