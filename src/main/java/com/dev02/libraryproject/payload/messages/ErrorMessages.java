package com.dev02.libraryproject.payload.messages;


public class ErrorMessages {


    private ErrorMessages() {
    }

//7-15 author
    public static final String AUTHOR_NOT_FOUND="Author is not found by id : %s.";
    public static final String USER_NOT_ADMIN = "User is not Admin";
    public static final String CATEGORY_NOT_FOUND = "Category is not found";




    public static final String ROLE_NOT_FOUND = "Role doesn't exist";

    public static final String BOOK_NOT_FOUND_MESSAGE ="Book is not found by id : %s.";
    public static final String BOOK_NOT_LOANABLE="Book is not loanable";
    public static final String BOOK_ALREADY_EXISTS_WITH_NAME ="Book is already exists" ;
    public static final String BOOK_CAN_NOT_BE_DELETED ="Book can not be deleted" ;
    public static final String ALREADY_REGISTER_MESSAGE_EMAIL= "Email is exists already : %s";
    public static final String ALREADY_REGISTER_MESSAGE_PHONE= "Email is exists already : %s";

    public static final String USER_HAS_EXPIRE_LOAN = "User has expired date loan by id : %s";
    public static final String USER_HAS_LOAN = "User has loan, so user cant be deleted";
    public static final String USER_CAN_NOT_LOAN = "User can not loan this book now. Check loanlist and score";
    public static final String NOT_FOUND_USER_MESSAGE = "Error: User not found with id %s";

    public static final String LOAN_NOT_FOUND="Loan is not found by id : %s";
    public static final String LOAN_NOT_FOUND_BY_USER="This loan does not belong to this user id: %s";

    public static final String DONT_HAVE_AUTHORITY="You don't have permission to do that";
    public static final String NOT_PERMITTED_METHOD_MESSAGE = "You do not have any permission to do this operation";

    public static final String PUBLISHER_NOT_FOUND = "Publisher is not found by id : %s";
    public static final String CANT_DELETE_PUBLISHER = "Publisher can not deleted";








    //16-25 loan






}
