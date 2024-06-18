package com.dev02.libraryproject.payload.mappers;

import com.dev02.libraryproject.entity.concretes.business.Publisher;
import com.dev02.libraryproject.payload.request.business.PublisherRequest;
import com.dev02.libraryproject.payload.response.business.PublisherResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class PublisherMapper {


    public PublisherResponse mapPublisherToPublisherResponse(Publisher publisher) {
        return PublisherResponse.builder().id(publisher.getId())
                .name(publisher.getName())
                .builtIn(publisher.getBuiltIn())
                .build();
    }


    public Publisher mapPublisherRequestToPublisher(PublisherRequest publisherRequest) {
        return Publisher.builder()
                .name(publisherRequest.getName())
                .builtIn(publisherRequest.getBuiltIn())
                .build();
    }

    public Publisher mapPublisherRequestToUpdatedPublisher(PublisherRequest publisherRequest,Long publisherId){
        return Publisher.builder()
                .id(publisherId)
                .name(publisherRequest.getName())
                .builtIn(publisherRequest.getBuiltIn())
                .build();
    }

}
