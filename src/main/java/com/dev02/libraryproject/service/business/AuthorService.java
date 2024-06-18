package com.dev02.libraryproject.service.business;

import com.dev02.libraryproject.entity.concretes.business.Author;
import com.dev02.libraryproject.exception.ResourceNotFoundException;
import com.dev02.libraryproject.payload.mappers.AuthorMapper;
import com.dev02.libraryproject.payload.messages.ErrorMessages;
import com.dev02.libraryproject.payload.messages.SuccessMessages;
import com.dev02.libraryproject.payload.request.business.AuthorRequest;
import com.dev02.libraryproject.payload.response.business.AuthorResponse;
import com.dev02.libraryproject.payload.response.business.ResponseMessage;
import com.dev02.libraryproject.repository.business.AuthorRepository;
import com.dev02.libraryproject.service.helper.PageableHelper;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;

public class AuthorService {

    private PageableHelper pageableHelper;
    private AuthorRepository authorRepository;
    private AuthorMapper authorMapper;
    private ErrorMessages errorMessages;


    public Page<AuthorResponse> getAuthorByPage(int page, int size, String sort, String type) {
        Pageable pageable = pageableHelper.getPageableWithProperties(page, size, sort, type);
        return authorRepository.findByAuthor(pageable)
                .map(authorMapper::mapAuthorToAuthorResponse) ;
    }

    public ResponseMessage<AuthorResponse> getAuthorById(Long id) {
        isAuthorExistsById(id);
        Author author=authorRepository.findByAuthorId(id);
        return ResponseMessage.<AuthorResponse>builder()
                .message(SuccessMessages.AUTHOR_FOUND)
                .httpStatus(HttpStatus.OK)
                .object(authorMapper.mapAuthorToAuthorResponse(author))
                .build();
    }



    public Author isAuthorExistsById(Long id){
        return  authorRepository.findById(id).orElseThrow(()->
                new ResourceNotFoundException(String.format(ErrorMessages.AUTHOR_NOT_FOUND, id)));
    }

    public ResponseMessage<AuthorResponse> saveAuthor(AuthorRequest authorRequest) {

        Author author=authorRepository.save(authorMapper.mapAuthorRequestToAuthor(authorRequest));

        return ResponseMessage.<AuthorResponse>builder()
                .message(SuccessMessages.AUTHOR_CREATED)
                .httpStatus(HttpStatus.OK)
                .object(authorMapper.mapAuthorToAuthorResponse(author))
                .build();
    }

    public ResponseMessage<AuthorResponse> updateAuthor(Long id, AuthorRequest authorRequest) {
        isAuthorExistsById(id);
        Author author=authorRepository.save(authorMapper.mapAuthorRequestToAuthor(authorRequest));

        return ResponseMessage.<AuthorResponse>builder()
                .message(SuccessMessages.AUTHOR_UPDATED)
                .httpStatus(HttpStatus.OK)
                .object(authorMapper.mapAuthorToAuthorResponse(author))
                .build();
    }

    public ResponseMessage<AuthorResponse> deleteAuthor(Long id) {
        Author author=isAuthorExistsById(id);
        authorRepository.deleteById(id);

        return ResponseMessage.<AuthorResponse>builder()
                .message(SuccessMessages.AUTHOR_DELETED)
                .httpStatus(HttpStatus.OK)
                .object(authorMapper.mapAuthorToAuthorResponse(author))
                .build();
    }

}
