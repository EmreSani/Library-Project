package com.dev02.libraryproject.payload.response.business;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class LoanResponseForUpdate {
    private Long id;

    private Long userId;

    private Long bookId;
}
