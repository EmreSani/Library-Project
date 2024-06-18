package com.dev02.libraryproject.payload.request.business;


import com.fasterxml.jackson.annotation.JsonIgnore;

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
public class LoanRequest {

    @NotNull
    private Long userId;


    @NotNull
    private Long bookId;

    @Size(max=300,message="Max of 300 characters can be entered.")
    private String notes;
}
