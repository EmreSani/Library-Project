package com.dev02.libraryproject.payload.mappers;


import com.dev02.libraryproject.entity.concretes.user.User;
import com.dev02.libraryproject.payload.request.user.UserRequest;

public class UserMapper {

    public User mapUserRequestToUser(UserRequest userRequest) {
        return User.builder().email(userRequest.getEmail())
                .firstName(userRequest.getFirstName())
                .lastName(userRequest.getLastName())
                .address(userRequest.getAddress())
                .phone(userRequest.getPhone())
                .birthDate(userRequest.getBirthDate())//?
                .build();
    }
}
