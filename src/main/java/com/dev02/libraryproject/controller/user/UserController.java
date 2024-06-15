package com.dev02.libraryproject.controller.user;

import com.dev02.libraryproject.payload.request.user.SigninRequest;
import com.dev02.libraryproject.payload.request.user.UserRequest;
import com.dev02.libraryproject.payload.response.business.LoanResponse;
import com.dev02.libraryproject.payload.response.business.ResponseMessage;
import com.dev02.libraryproject.payload.response.user.SigninResponse;
import com.dev02.libraryproject.payload.response.user.UserResponse;
import com.dev02.libraryproject.service.user.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;

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
    public ResponseEntity<UserResponse> register (@RequestBody @Valid UserRequest userRequest){
        return userService.register(userRequest);
    }

    @PostMapping
    @PreAuthorize("hasAnyAuthority('ADMIN','EMPLOYEE','MEMBER')")
    public ResponseMessage <UserResponse> getAuthenticatedUser (HttpServletRequest httpServletRequest){
        return userService.getAuthenticatedUser(httpServletRequest);
    }

    @PostMapping("/loans")
    @PreAuthorize("hasAnyAuthority('ADMIN','EMPLOYEE','MEMBER')")
    public ResponseMessage<Page<LoanResponse>> getAllLoansByUserByPage (
            HttpServletRequest httpServletRequest,
    @RequestParam(value = "page", defaultValue = "0") int page,
    @RequestParam(value = "size",defaultValue = "20") int size,
    @RequestParam(value = "sort",defaultValue = "createDate") String sort,
    @RequestParam(value = "type",defaultValue = "desc") String type){

        return userService.getAllLoansByUserByPage(httpServletRequest,page,size,sort,type);
    }

    @PostMapping("/s")
    @PreAuthorize("hasAnyAuthority('ADMIN','EMPLOYEE')")
    public ResponseMessage<Page<UserResponse>> getAllUsers (
            @RequestParam(value = "page", defaultValue = "0") int page,
            @RequestParam(value = "size",defaultValue = "20") int size,
            @RequestParam(value = "sort",defaultValue = "createDate") String sort,
            @RequestParam(value = "type",defaultValue = "desc") String type){

        return userService.getAllUsersByPage(page,size,sort,type);
    }

    @GetMapping("/s/{userId}")
    @PreAuthorize("hasAnyAuthority('ADMIN','EMPLOYEE')")
    public ResponseEntity<UserResponse> getUserById (@PathVariable Long userId){
        return userService.getUserById(userId);
    }

    @DeleteMapping("/s/{userId}")
    @PreAuthorize("hasAnyAuthority('ADMIN')")
    public ResponseEntity<UserResponse> deleteUserById(@PathVariable Long userId){
        return userService.deleteUserById(userId);
    }






}
