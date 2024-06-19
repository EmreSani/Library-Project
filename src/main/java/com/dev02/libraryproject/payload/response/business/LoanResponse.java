package com.dev02.libraryproject.payload.response.business;

import com.fasterxml.jackson.annotation.JsonIgnore;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;

public class LoanResponse {

    private Long id;

    private Long userId;

    private Long bookId;
}
