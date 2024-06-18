package com.dev02.libraryproject.service.helper;

import com.dev02.libraryproject.entity.concretes.user.User;
import com.dev02.libraryproject.entity.enums.RoleType;
import com.dev02.libraryproject.exception.BadRequestException;
import com.dev02.libraryproject.exception.ResourceNotFoundException;
import com.dev02.libraryproject.payload.messages.ErrorMessages;
import com.dev02.libraryproject.repository.user.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class MethodHelper {
    private final UserRepository userRepository;

    public User isUserExist(Long userId) {
        return userRepository.findById(userId).orElseThrow(() ->
                new ResourceNotFoundException(String.format(ErrorMessages.NOT_FOUND_USER_MESSAGE,
                        userId)));
    }

    public void checkBuiltIn(User user) {
        if (Boolean.TRUE.equals(user.getBuiltIn())) {
            throw new BadRequestException(ErrorMessages.NOT_PERMITTED_METHOD_MESSAGE);
        }
    }

    //!!! isUserExistWithUsername
    public User isUserExistByUsername(String username) {

        User user = userRepository.findByUsernameEquals(username);
        if (user.getId() == null) {
            throw new ResourceNotFoundException(ErrorMessages.NOT_FOUND_USER_MESSAGE);
        }

        return user;

    }
    //!!! Check the role
    public void checkRole(User user, RoleType roleType){
        if (!user.getRoles().equals(roleType)) {
            throw new ResourceNotFoundException(
                    String.format(ErrorMessages.ROLE_NOT_FOUND, user.getId(),roleType));
        }
    }
}