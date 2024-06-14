package com.dev02.libraryproject.payload.response.business;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToOne;

public class LoanResponse {

    private Long id;

    private Long userId;

    private Long bookId;
}
