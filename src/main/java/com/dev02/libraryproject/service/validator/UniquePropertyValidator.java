package com.dev02.libraryproject.service.validator;

import com.dev02.libraryproject.exception.ConflictException;
import com.dev02.libraryproject.payload.messages.ErrorMessages;
import com.dev02.libraryproject.repository.user.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class UniquePropertyValidator {

    private final UserRepository userRepository;

    public void checkDuplicate(String email, String phone){


        if(userRepository.existByEmail(email)){
            throw new ConflictException(String.format(ErrorMessages.ALREADY_REGISTER_MESSAGE_EMAIL, email));
        }

        if(userRepository.existsByPhone(phone)){
            throw new ConflictException(String.format(ErrorMessages.ALREADY_REGISTER_MESSAGE_PHONE, phone));
        }

    }


}