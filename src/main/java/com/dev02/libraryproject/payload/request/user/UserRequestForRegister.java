package com.dev02.libraryproject.payload.request.user;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.*;


import javax.validation.constraints.*;
import java.time.LocalDate;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor

public class UserRequestForRegister {

    @NotNull
    @Size(min = 2, max = 30, message = "(${validatedValue}) {min} and {max} lengths allowed!")
    private String firstName;

    @NotNull
    @Size(min = 2, max = 30, message = "(${validatedValue}) {min} and {max} lengths allowed!")
    private String lastName;

    @NotNull
    @Size(min = 10, max = 100, message = "(${validatedValue}) {min} and {max} lengths allowed!")
    private String address;

    @NotNull
    @Size(min = 12, max = 12, message = "Your phone number should be 12 characters long")
    @Pattern(regexp = "^\\d{3}-\\d{3}-\\d{4}$", message = "Please enter a valid phone number in the format 999-999-9999")
    private String phone;

    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd")
    @Past(message = "Your birthday can not be in the future")
    private LocalDate birthDate;

    @NotNull
    @Size(min = 10, max = 80, message = "(${validatedValue}) {min} and {max} lengths allowed!")
    @Email
    private String email;

    @NotNull
    private String password;

}
