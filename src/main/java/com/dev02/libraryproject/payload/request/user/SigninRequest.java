package com.dev02.libraryproject.payload.request.user;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotNull;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class SigninRequest {
    @NotNull(message = "email must not be empty")
    private String email;

    @NotNull(message = "Password must not be empty")
    private String password;
}
