package com.dev02.libraryproject.payload.request.business;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.time.LocalDateTime;
@Data

@AllArgsConstructor
@NoArgsConstructor
@Builder(toBuilder = true)
public class LoanRequestForReturnDate {

    @Size(max=300,message="Max of 300 characters can be entered.")
    private String notes;
}
