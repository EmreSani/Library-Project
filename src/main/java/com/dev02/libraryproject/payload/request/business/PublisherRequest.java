package com.dev02.libraryproject.payload.request.business;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder(toBuilder = true)
public class PublisherRequest {


    @NotNull(message = "Name can not be null")
    @Size(min = 2, max = 30, message = "Publisher name (${validatedValue}) {min} and {max} lengths allowed. !")
    private String name;

    @NotNull
    private Boolean builtIn;
}
