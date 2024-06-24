
package com.dev02.libraryproject.service.business;

import com.dev02.libraryproject.entity.concretes.business.Author;
import com.dev02.libraryproject.entity.concretes.business.Publisher;
import com.dev02.libraryproject.exception.ConflictException;
import com.dev02.libraryproject.exception.ResourceNotFoundException;
import com.dev02.libraryproject.payload.mappers.PublisherMapper;
import com.dev02.libraryproject.payload.messages.ErrorMessages;
import com.dev02.libraryproject.payload.messages.SuccessMessages;
import com.dev02.libraryproject.payload.request.business.PublisherRequest;
import com.dev02.libraryproject.payload.response.business.AuthorResponse;
import com.dev02.libraryproject.payload.response.business.PublisherResponse;
import com.dev02.libraryproject.payload.response.business.ResponseMessage;
import com.dev02.libraryproject.repository.business.PublisherRepository;
import com.dev02.libraryproject.service.helper.PageableHelper;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class PublisherService {

    private final PageableHelper pageableHelper;
    private final PublisherRepository publisherRepository;
    private final PublisherMapper publisherMapper;

    public Page<PublisherResponse> getPublishersByPage(int page, int size, String sort, String type) {

            Pageable pageable = pageableHelper.getPageableWithProperties(page, size, sort, type);

        return publisherRepository.findAll(pageable)
                    .map(publisherMapper::mapPublisherToPublisherResponse) ;

    }

    public ResponseMessage<PublisherResponse> getPublisherById(Long id) {

        Publisher publisher = isPublisherExistsById(id);
        return ResponseMessage.<PublisherResponse>builder()
                .message(SuccessMessages.PUBLISHER_FOUND)
                .httpStatus(HttpStatus.OK)
                .object(publisherMapper.mapPublisherToPublisherResponse(publisher))
                .build();
    }

        public Publisher isPublisherExistsById(Long id){
            return  publisherRepository.findById(id).orElseThrow(()->
                    new ResourceNotFoundException(String.format(ErrorMessages.PUBLISHER_NOT_FOUND, id)));
        }


    public ResponseMessage<PublisherResponse> savePublisher(PublisherRequest publisherRequest) {

        Publisher publisher= publisherRepository.save(publisherMapper.mapPublisherRequestToPublisher(publisherRequest));

        return ResponseMessage.<PublisherResponse>builder()
                .message(SuccessMessages.PUBLISHER_CREATED)
                .httpStatus(HttpStatus.OK)
                .object(publisherMapper.mapPublisherToPublisherResponse(publisher))
                .build();
    }

    public ResponseMessage<PublisherResponse> updatePublisher(Long id, PublisherRequest publisherRequest) {
        isPublisherExistsById(id);
        Publisher publisher=publisherRepository.save(publisherMapper.mapPublisherRequestToUpdatedPublisher(publisherRequest,id));

        return ResponseMessage.<PublisherResponse>builder()
                .message(SuccessMessages.PUBLISHER_UPDATED)
                .httpStatus(HttpStatus.OK)
                .object(publisherMapper.mapPublisherToPublisherResponse(publisher))
                .build();
    }


    public ResponseMessage<PublisherResponse> deletePublisher(Long id) {

        Publisher publisher =isPublisherExistsById(id);

        if (! publisher.getBookList().isEmpty()){
            throw new ConflictException(ErrorMessages.CANT_DELETE_PUBLISHER);
        }

        publisherRepository.delete(publisher);

        return ResponseMessage.<PublisherResponse>builder()
                .message(SuccessMessages.PUBLISHER_DELETED)
                .httpStatus(HttpStatus.OK)
                .object(publisherMapper.mapPublisherToPublisherResponse(publisher))
                .build();
    }
    public long countPublishers() {
        return publisherRepository.count();
    }
}

