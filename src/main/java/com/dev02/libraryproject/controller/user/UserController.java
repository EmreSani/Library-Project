package com.dev02.libraryproject.controller.user;

import com.dev02.libraryproject.payload.request.user.SigninRequest;
import com.dev02.libraryproject.payload.request.user.UserRequestForCreateOrUpdate;
import com.dev02.libraryproject.payload.request.user.UserRequestForRegister;
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

    // http://localhost:8080/user/signin + POST
    @PostMapping("/signin")
    public ResponseEntity<SigninResponse> signIn(@RequestBody @Valid SigninRequest signInRequest) {
        return userService.authenticateUser(signInRequest);
    }

    // http://localhost:8080/user/register + POST
    @PostMapping("/register")
    public ResponseEntity<UserResponse> register(@RequestBody @Valid UserRequestForRegister userRequestForRegister) {
        return userService.register(userRequestForRegister);
    }

    // http://localhost:8080/user + POST
    @PostMapping
  //  @PreAuthorize("hasAnyAuthority('ADMIN','EMPLOYEE','MEMBER')")
    public ResponseMessage<UserResponse> getAuthenticatedUser(HttpServletRequest httpServletRequest) {
        return userService.getAuthenticatedUser(httpServletRequest);
    }

    // http://localhost:8080/user/loans + POST
    @PostMapping("/loans")
    @PreAuthorize("hasAnyAuthority('ADMIN','EMPLOYEE','MEMBER')")
    public ResponseEntity<Page<LoanResponse>> getAllLoansByUserByPage(
            HttpServletRequest httpServletRequest,
            @RequestParam(value = "page", defaultValue = "0") int page,
            @RequestParam(value = "size", defaultValue = "20") int size,
            @RequestParam(value = "sort", defaultValue = "createDate") String sort,
            @RequestParam(value = "type", defaultValue = "desc") String type) {

        return userService.getAllLoansByUserByPage(httpServletRequest, page, size, sort, type);
    }

    // http://localhost:8080/user + Get
    @GetMapping
    @PreAuthorize("hasAnyAuthority('ADMIN','EMPLOYEE')")
    public ResponseMessage<Page<UserResponse>> getAllUsers(
            @RequestParam(value = "page", defaultValue = "0") int page,
            @RequestParam(value = "size", defaultValue = "20") int size,
            @RequestParam(value = "sort", defaultValue = "createDate") String sort,
            @RequestParam(value = "type", defaultValue = "desc") String type) {

        return userService.getAllUsersByPage(page, size, sort, type);
    }

    // http://localhost:8080/user/{userId} + GET
    @GetMapping("/{userId}")
    @PreAuthorize("hasAnyAuthority('ROLE_ADMIN','ROLE_EMPLOYEE')")
    public ResponseEntity<UserResponse> getUserById(@PathVariable Long userId) {
        return userService.getUserById(userId);
    }

    // http://localhost:8080/user/{userId} + DELETE
    @DeleteMapping("/{userId}")
    @PreAuthorize("hasAnyAuthority('ADMIN')")
    public ResponseEntity<UserResponse> deleteUserById(@PathVariable Long userId) {
        return userService.deleteUserById(userId);
    }

    // http://localhost:8080/user/{userRole} + POST
    @PostMapping("/{userRole}") //Dökümantasyona göre kıyasla
    @PreAuthorize("hasAnyAuthority('ADMIN','EMPLOYEE')")
    public ResponseEntity<UserResponse> createUser(@RequestBody @Valid UserRequestForCreateOrUpdate userRequestForCreateOrUpdate,
                                                   HttpServletRequest httpServletRequest,
                                                   @PathVariable String userRole) {
        return userService.createUser(userRequestForCreateOrUpdate, httpServletRequest, userRole);
    }

    // http://localhost:8080/user/{userId} + PUT
    @PutMapping("/{userId}")
    @PreAuthorize("hasAnyAuthority('ADMIN','EMPLOYEE')")
    public ResponseEntity<UserResponse> updateUser(@RequestBody @Valid UserRequestForCreateOrUpdate userRequestForCreateOrUpdate, //farklı dto düşünülebilir create-->update
                                                   @PathVariable Long userId,
                                                   HttpServletRequest httpServletRequest) {

        return userService.updateUser(userRequestForCreateOrUpdate, userId, httpServletRequest);

    }


}
