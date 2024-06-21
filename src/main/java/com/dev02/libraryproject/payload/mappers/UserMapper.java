package com.dev02.libraryproject.payload.mappers;


import com.dev02.libraryproject.entity.concretes.user.User;
import com.dev02.libraryproject.payload.request.user.UserRequest;
import com.dev02.libraryproject.payload.request.user.UserRequestForCreateOrUpdate;
import com.dev02.libraryproject.payload.request.user.UserRequestForRegister;
import com.dev02.libraryproject.payload.response.user.UserResponse;

public class UserMapper {

    public User mapUserRequestToUser(UserRequestForRegister userRequestForRegister) {
        return User.builder().email(userRequestForRegister.getEmail())
                .firstName(userRequestForRegister.getFirstName())
                .lastName(userRequestForRegister.getLastName())
                .address(userRequestForRegister.getAddress())
                .phone(userRequestForRegister.getPhone())
                .birthDate(userRequestForRegister.getBirthDate())
                .build();
    }

    public User mapUserRequestToUser(UserRequestForCreateOrUpdate userRequestForCreateOrUpdate) {
        return User.builder().email(userRequestForCreateOrUpdate.getEmail())
                .firstName(userRequestForCreateOrUpdate.getFirstName())
                .lastName(userRequestForCreateOrUpdate.getLastName())
                .address(userRequestForCreateOrUpdate.getAddress())
                .phone(userRequestForCreateOrUpdate.getPhone())
                .birthDate(userRequestForCreateOrUpdate.getBirthDate())
                .build();
    }

    public User mapUserRequestForAdminToUser(UserRequest userRequest) {
        return User.builder().email(userRequest.getEmail())
                .firstName(userRequest.getFirstName())
                .lastName(userRequest.getLastName())
                .address(userRequest.getAddress())
                .phone(userRequest.getPhone())
                .birthDate(userRequest.getBirthDate())
                .build();
    }

    public UserResponse mapUserToUserResponse(User user) {
        return UserResponse.builder().email(user.getEmail())
                .firstName(user.getFirstName())
                .lastName(user.getLastName())
                .address(user.getAddress())
                .phone(user.getPhone())
                .birthDate(user.getBirthDate())
                .build();
    }

    public User mapUserRequestToUpdatedUser(UserRequestForCreateOrUpdate userRequestForCreateOrUpdate, Long userId) {
        return User.builder()
                .id(userId)
                .email(userRequestForCreateOrUpdate.getEmail())
                .firstName(userRequestForCreateOrUpdate.getFirstName())
                .lastName(userRequestForCreateOrUpdate.getLastName())
                .address(userRequestForCreateOrUpdate.getAddress())
                .phone(userRequestForCreateOrUpdate.getPhone())
                .birthDate(userRequestForCreateOrUpdate.getBirthDate())
                .build();

    }
}
