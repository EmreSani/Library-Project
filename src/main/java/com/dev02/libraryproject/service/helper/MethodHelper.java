package com.dev02.libraryproject.service.helper;

import com.dev02.libraryproject.entity.concretes.business.*;

import com.dev02.libraryproject.entity.concretes.user.User;
import com.dev02.libraryproject.entity.enums.RoleType;
import com.dev02.libraryproject.exception.BadRequestException;
import com.dev02.libraryproject.exception.ResourceNotFoundException;
import com.dev02.libraryproject.payload.messages.ErrorMessages;

import com.dev02.libraryproject.repository.business.*;
import com.dev02.libraryproject.repository.user.UserRepository;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

@Component
@RequiredArgsConstructor
public class MethodHelper {
    private final UserRepository userRepository;
    private final AuthorRepository authorRepository;
    private final CategoryRepository categoryRepository;
    private final PublisherRepository publisherRepository;
    private final BookRepository bookRepository;
    private final LoanRepository loanRepository;


    @Transactional
    public User isUserExist(Long userId) {
        return userRepository.findById(userId).orElseThrow(() ->
                new ResourceNotFoundException(String.format(ErrorMessages.NOT_FOUND_USER_MESSAGE,
                        userId)));
    }


    public boolean isAdmin(HttpServletRequest httpServletRequest) {
        if (httpServletRequest.getAttribute("name")!="Admin") {
            throw new ResourceNotFoundException(String.format(ErrorMessages.USER_NOT_ADMIN));
        }
        return true;
    }


    public Author isAuthorExistsById(Long id){
        return  authorRepository.findById(id).orElseThrow(()->
                new ResourceNotFoundException(String.format(ErrorMessages.AUTHOR_NOT_FOUND, id)));
    }

    public Category isCategoryExists(Long categoryId) {
        return  categoryRepository.findById(categoryId).orElseThrow(()->
                new ResourceNotFoundException(String.format(ErrorMessages.CATEGORY_NOT_FOUND, categoryId)));
    }

    public Publisher isPublisherExists(Long publisherId) {
        return publisherRepository.findById(publisherId).orElseThrow(()->
                new ResourceNotFoundException(String.format(ErrorMessages.PUBLISHER_NOT_FOUND, publisherId)));

    }


    public void checkBuiltIn(User user) {
        if (Boolean.TRUE.equals(user.getBuiltIn())) {
            throw new BadRequestException(ErrorMessages.NOT_PERMITTED_METHOD_MESSAGE);
        }
    }

    //!!! isUserExistWithUsername
    public User isUserExistByEmail(String username) {

        User user = userRepository.findByEmail(username);
        if (user.getId() == null) {
            throw new ResourceNotFoundException(ErrorMessages.NOT_FOUND_USER_MESSAGE);
        }

        return user;

    }
    //!!! Check the role
    public void checkRole(User user, RoleType roleType){
        if (!user.getRoles().equals(roleType)) {
            throw new ResourceNotFoundException(
                    String.format(ErrorMessages.ROLE_NOT_FOUND, user.getId(),roleType));
        }
    }
    public Book isBookExists(Long id) {
        return bookRepository.findById(id).orElseThrow(() ->
                new ResourceNotFoundException(String.format(ErrorMessages.BOOK_NOT_FOUND_MESSAGE, id)));

    }

    public List<Loan> getAllLoans(){

        return loanRepository.findAll();

    }

    public List<Book> getAllBooks(){

        return bookRepository.findAll();

    }

}