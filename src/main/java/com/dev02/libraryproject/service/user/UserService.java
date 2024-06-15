package com.dev02.libraryproject.service.user;

import com.dev02.libraryproject.entity.concretes.user.Role;
import com.dev02.libraryproject.entity.concretes.user.User;
import com.dev02.libraryproject.entity.enums.RoleType;
import com.dev02.libraryproject.payload.mappers.UserMapper;
import com.dev02.libraryproject.payload.request.user.SigninRequest;
import com.dev02.libraryproject.payload.request.user.UserRequest;
import com.dev02.libraryproject.payload.response.user.SigninResponse;
import com.dev02.libraryproject.payload.response.user.UserResponse;
import com.dev02.libraryproject.repository.user.UserRepository;
import com.dev02.libraryproject.security.jwt.JwtUtils;
import com.dev02.libraryproject.security.service.UserDetailsImpl;
import com.dev02.libraryproject.service.validator.UniquePropertyValidator;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Objects;
import java.util.Set;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class UserService {
    private final UserRepository userRepository;
    public final JwtUtils jwtUtils;
    public final AuthenticationManager authenticationManager;
    private final PasswordEncoder passwordEncoder;
    private final UniquePropertyValidator uniquePropertyValidator;
    private final UserMapper userMapper;
    //private final RoleService roleService;


    public ResponseEntity<SigninResponse> authenticateUser(SigninRequest signInRequest) {
        String email = signInRequest.getEmail();
        String password = signInRequest.getPassword();

        Authentication authentication = authenticationManager.
                authenticate(new UsernamePasswordAuthenticationToken(email, password));

        SecurityContextHolder.getContext().setAuthentication(authentication);

        String token = "Bearer " + jwtUtils.generateJwtToken(authentication);

        UserDetailsImpl userDetails = (UserDetailsImpl) authentication.getPrincipal();

        // GrantedAuthority turundeki role yapisini String turune ceviriliyor
        Set<String> roles = userDetails.getAuthorities()
                .stream()
                .map(GrantedAuthority::getAuthority)
                .collect(Collectors.toSet());

        // AuthResponse nesnesi olusturuluyor ve gerekli alanlar setleniyor
        SigninResponse signinResponse = SigninResponse.builder()
                .email(userDetails.getUsername())
                .token(token.substring(7))
                .name(userDetails.getName())
                .roles(roles)
                .build();

        // SigninResponse nesnesi ResponseEntity ile donduruluyor
        return ResponseEntity.ok(signinResponse);
    }

    public ResponseEntity<UserResponse> register(UserRequest userRequest,String userRole) {

        //!!! username - ssn- phoneNumber unique mi kontrolu ??
        uniquePropertyValidator.checkDuplicate(userRequest.getEmail(),
                userRequest.getPhone());
        //!!! DTO --> POJO

        User user = userMapper.mapUserRequestToUser(userRequest);

        // !!! Rol bilgisi setleniyor
        if(userRole.equalsIgnoreCase(RoleType.MEMBER.name())){
            user.setUserRole(userRoleService.getUserRole(RoleType.MEMBER));
        }

        // !!! password encode ediliyor
        user.setPassword(passwordEncoder.encode(userRequest.getPassword()));

        User savedUser = userRepository.save(user);

        return ResponseEntity.

    }




}
