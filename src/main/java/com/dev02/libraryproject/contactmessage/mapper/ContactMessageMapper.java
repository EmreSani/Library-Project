package com.dev02.libraryproject.contactmessage.mapper;

import com.dev02.libraryproject.contactmessage.dto.ContactMessageRequest;
import com.dev02.libraryproject.contactmessage.dto.ContactMessageResponse;
import com.dev02.libraryproject.contactmessage.entity.ContactMessage;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;

@Component
public class ContactMessageMapper {

    // !!! Request --> POJO
    public ContactMessage requestToContactMessage(ContactMessageRequest contactMessageRequest){
        return ContactMessage.builder()
                .name(contactMessageRequest.getName())
                .subject(contactMessageRequest.getSubject())
                .message(contactMessageRequest.getMessage())
                .email(contactMessageRequest.getEmail())
                .dateTime(LocalDateTime.now())
                .build();
    }

    // !!! pojo --> Response
    public ContactMessageResponse contactMessageToResponse(ContactMessage contactMessage){

        return ContactMessageResponse.builder()
                .name(contactMessage.getName())
                .subject(contactMessage.getSubject())
                .message(contactMessage.getMessage())
                .email(contactMessage.getEmail())
                .dateTime(LocalDateTime.now())
                .build();

    }
}
