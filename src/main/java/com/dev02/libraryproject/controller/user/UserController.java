package com.dev02.libraryproject.controller.user;

import com.dev02.libraryproject.payload.request.user.SigninRequest;
import com.dev02.libraryproject.payload.request.user.UserRequest;
import com.dev02.libraryproject.payload.response.user.SigninResponse;
import com.dev02.libraryproject.payload.response.user.UserResponse;
import com.dev02.libraryproject.service.user.UserService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;


@RestController
@RequestMapping("/user")
@RequiredArgsConstructor
public class UserController {

    private final UserService userService;

    //signin
    @PostMapping("/signin")
    public ResponseEntity<SigninResponse> signIn (@RequestBody @Valid SigninRequest signInRequest){
        return userService.authenticateUser(signInRequest);
    }



    @PostMapping("/register")
    public ResponseEntity<UserResponse> register (@RequestBody @Valid UserRequest userRequest, String userRole){
        return userService.register(userRequest,userRole);
    }




}
