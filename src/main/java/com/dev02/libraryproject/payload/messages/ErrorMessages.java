package com.dev02.libraryproject.payload.messages;

public class ErrorMessages {

    private ErrorMessages() {
    }

    public static final String AUTHOR_NOT_FOUND="Author is not found by id : %s.";
    public static final String BOOK_NOT_LOANABLE="Book is not loanable";
    public static final String USER_HAS_EXPIRE_LOAN = "User has expired date loan by id : %s";
    public static final String ALREADY_REGISTER_MESSAGE_EMAIL= "Email is exists already : %s";
    public static final String ALREADY_REGISTER_MESSAGE_PHONE= "Email is exists already : %s";



}
