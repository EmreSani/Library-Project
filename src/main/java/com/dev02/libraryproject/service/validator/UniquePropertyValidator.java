package com.dev02.libraryproject.service.validator;

import com.dev02.libraryproject.entity.concretes.user.User;
import com.dev02.libraryproject.exception.ConflictException;
import com.dev02.libraryproject.payload.messages.ErrorMessages;
import com.dev02.libraryproject.payload.request.user.UserRequestForCreateOrUpdate;
import com.dev02.libraryproject.repository.user.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class UniquePropertyValidator {

    private final UserRepository userRepository;

    public void checkDuplicate(String email, String phone){


        if(userRepository.existsByEmail(email)){
            throw new ConflictException(String.format(ErrorMessages.ALREADY_REGISTER_MESSAGE_EMAIL, email));
        }

        if(userRepository.existsByPhone(phone)){
            throw new ConflictException(String.format(ErrorMessages.ALREADY_REGISTER_MESSAGE_PHONE, phone));
        }

    }

    public void checkUniqueProperties(User user, UserRequestForCreateOrUpdate userRequestForCreateOrUpdate){
        String updatedEmail = "";
        String updatedPhone = "";
        boolean isChanged = false;


        if(!user.getPhone().equalsIgnoreCase(userRequestForCreateOrUpdate.getPhone())){
            updatedPhone = userRequestForCreateOrUpdate.getPhone();
            isChanged = true;
        }
        if(!user.getEmail().equalsIgnoreCase(userRequestForCreateOrUpdate.getEmail())){
            updatedEmail = userRequestForCreateOrUpdate.getEmail();
            isChanged = true;
        }

        if(isChanged) {
            checkDuplicate(updatedEmail, updatedPhone);
        }

    }


}