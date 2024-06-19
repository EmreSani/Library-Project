package com.dev02.libraryproject.payload.messages;

public class ErrorMessages {

    public static final String USER_NOT_ADMIN = "User is not Admin";
    public static final String CATEGORY_NOT_FOUND = "Category is not found";
    public static final String PUBLISHER_NOT_FOUND = "Publisher is not found";

    private ErrorMessages() {
    }

    public static final String ROLE_NOT_FOUND = "Role doesn't exist";

    public static final String AUTHOR_NOT_FOUND="Author is not found by id : %s.";
    public static final String BOOK_NOT_LOANABLE="Book is not loanable";

    public static final String ALREADY_REGISTER_MESSAGE_EMAIL= "Email is exists already : %s";
    public static final String ALREADY_REGISTER_MESSAGE_PHONE= "Email is exists already : %s";

    public static final String USER_HAS_EXPIRE_LOAN = "User has expired date loan by id : %s";
    public static final String USER_HAS_LOAN = "User has loan, so user cant be deleted";
    public static final String NOT_FOUND_USER_MESSAGE = "Error: User not found with id %s";



}
