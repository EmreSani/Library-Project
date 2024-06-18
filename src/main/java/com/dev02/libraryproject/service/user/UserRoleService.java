package com.dev02.libraryproject.service.user;

import com.dev02.libraryproject.entity.concretes.user.Role;
import com.dev02.libraryproject.entity.enums.RoleType;
import com.dev02.libraryproject.exception.ResourceNotFoundException;
import com.dev02.libraryproject.payload.messages.ErrorMessages;
import com.dev02.libraryproject.repository.user.UserRoleRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class UserRoleService {

    private final UserRoleRepository userRoleRepository;

    public Role getUserRole(RoleType roleType){
        return userRoleRepository.findByEnumRoleEquals(roleType).orElseThrow(()->
                new ResourceNotFoundException(ErrorMessages.ROLE_NOT_FOUND));
    }

    public List<Role> getAllUserRole(){
        return userRoleRepository.findAll();
    }

}
