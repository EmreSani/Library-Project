package com.dev02.libraryproject.payload.request.business;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnore;
import javax.persistence.ManyToOne;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Past;
import javax.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder(toBuilder = true)
public class LoanRequest {
    @JsonIgnore
    @NotNull
    private Long userId;

    @JsonIgnore
    @NotNull
    private Long bookId;

    @Size(max=300,message="Max of 300 characters can be entered.")
    private String notes;
}
