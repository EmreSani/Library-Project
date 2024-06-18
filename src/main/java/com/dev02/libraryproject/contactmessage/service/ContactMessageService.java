package com.dev02.libraryproject.contactmessage.service;

import com.dev02.libraryproject.contactmessage.dto.ContactMessageRequest;
import com.dev02.libraryproject.contactmessage.dto.ContactMessageResponse;
import com.dev02.libraryproject.contactmessage.entity.ContactMessage;
import com.dev02.libraryproject.contactmessage.mapper.ContactMessageMapper;
import com.dev02.libraryproject.contactmessage.messages.Messages;
import com.dev02.libraryproject.contactmessage.repository.ContactMessageRepository;
import com.dev02.libraryproject.exception.ConflictException;
import com.dev02.libraryproject.exception.ResourceNotFoundException;
import com.dev02.libraryproject.payload.response.business.ResponseMessage;
import com.dev02.libraryproject.service.helper.PageableHelper;
import lombok.RequiredArgsConstructor;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.format.DateTimeParseException;
import java.util.List;


@Service
@RequiredArgsConstructor
public class ContactMessageService {

    private final ContactMessageRepository contactMessageRepository;
    private final ContactMessageMapper contactMessageMapper;
    private final PageableHelper pageableHelper;

    public ResponseMessage<ContactMessageResponse> save(ContactMessageRequest contactMessageRequest) {

        ContactMessage contactMessage = contactMessageMapper.requestToContactMessage(contactMessageRequest);
        ContactMessage savedData = contactMessageRepository.save(contactMessage);

        return ResponseMessage.<ContactMessageResponse>builder()
                .message("Contact Message Created Successfully")
                .httpStatus(HttpStatus.CREATED) // 201
                .object(contactMessageMapper.contactMessageToResponse(savedData))
                .build();
    }

    public Page<ContactMessageResponse> getAll(int page, int size, String sort, String type) {

        Pageable pageable = pageableHelper.getPageableWithProperties(page, size, sort, type);

        return contactMessageRepository.findAll(pageable).map(contactMessageMapper::contactMessageToResponse);
    }

    public Page<ContactMessageResponse> searchByEmail(String email, int page, int size, String sort, String type) {

       Pageable pageable = pageableHelper.getPageableWithProperties(page, size, sort, type);

        return contactMessageRepository.findByEmailEquals(email, pageable).
                map(contactMessageMapper::contactMessageToResponse);
    }

    public String deleteById(Long id) {
        getContactMessageById(id);
        contactMessageRepository.deleteById(id);
        return Messages.CONTACT_MESSAGE_DELETED_SUCCESSFULLY;
    }

    public ContactMessage getContactMessageById(Long id){
        return contactMessageRepository.findById(id).orElseThrow(()->
                new ResourceNotFoundException(Messages.NOT_FOUND_MESSAGE));
    }

    public Page<ContactMessageResponse> searchBySubject(String subject, int page, int size, String sort, String type) {

        Pageable pageable = pageableHelper.getPageableWithProperties(page, size, sort, type);

        return contactMessageRepository.findBySubjectEquals(subject, pageable). // Derived
                map(contactMessageMapper::contactMessageToResponse);
    }

    public List<ContactMessage> searchBetweenDates(String beginDateString, String endDateString) {

        try {
            LocalDate beginDate = LocalDate.parse(beginDateString);
            LocalDate endDate = LocalDate.parse(endDateString);
            return contactMessageRepository.findMessagesBetweenDates(beginDate, endDate);
        } catch (DateTimeParseException e) {
            throw new ConflictException(Messages.WRONG_DATE_MESSAGE);
        }
    }


    public List<ContactMessage> searchBetweenTimes(String startHourString, String startMinuteString, String endHourString, String endMinuteString) {

        try {
            int startHour = Integer.parseInt(startHourString);
            int startMinute = Integer.parseInt(startMinuteString);
            int endHour = Integer.parseInt(endHourString);
            int endMinute = Integer.parseInt(endMinuteString);

            return contactMessageRepository.findMessagesBetweenTimes(startHour,startMinute,endHour,endMinute);
        } catch (NumberFormatException e) {
            throw new ConflictException(Messages.WRONG_TIME_MESSAGE);
        }
    }
}
