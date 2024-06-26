package com.dev02.libraryproject.payload.request.business;

import com.dev02.libraryproject.entity.concretes.business.Book;
import javax.persistence.*;

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
public class AuthorRequest {


    @NotNull(message="Name can not be null...")
    @Size(min=4, max=70,message="name must min=4 and max=70 consist of the characters.")
    private String name;

    @NotNull(message="BuiltIn can not be null...")
    private Boolean builtIn;

}
