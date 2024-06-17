package com.dev02.libraryproject.service.user;

import com.dev02.libraryproject.entity.concretes.user.User;
import com.dev02.libraryproject.entity.enums.RoleType;
import com.dev02.libraryproject.exception.BadRequestException;
import com.dev02.libraryproject.exception.ResourceNotFoundException;
import com.dev02.libraryproject.payload.mappers.UserMapper;
import com.dev02.libraryproject.payload.messages.ErrorMessages;
import com.dev02.libraryproject.payload.messages.SuccessMessages;
import com.dev02.libraryproject.payload.request.user.SigninRequest;
import com.dev02.libraryproject.payload.request.user.UserRequest;
import com.dev02.libraryproject.payload.response.business.LoanResponse;
import com.dev02.libraryproject.payload.response.business.ResponseMessage;
import com.dev02.libraryproject.payload.response.user.SigninResponse;
import com.dev02.libraryproject.payload.response.user.UserResponse;
import com.dev02.libraryproject.repository.user.UserRepository;
import com.dev02.libraryproject.security.jwt.JwtUtils;
import com.dev02.libraryproject.security.service.UserDetailsImpl;
import com.dev02.libraryproject.service.business.LoanService;
import com.dev02.libraryproject.service.helper.MethodHelper;
import com.dev02.libraryproject.service.helper.PageableHelper;
import com.dev02.libraryproject.service.validator.UniquePropertyValidator;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletRequest;
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
    private final PageableHelper pageableHelper;
    private final UserRoleService userRoleService;
    private final LoanService loanService;
    private final MethodHelper methodHelper;


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

    public ResponseEntity<UserResponse> register(UserRequest userRequest) {

        //!!! username - ssn- phoneNumber unique mi kontrolu ??
        uniquePropertyValidator.checkDuplicate(userRequest.getEmail(),
                userRequest.getPhone());
        //!!! DTO --> POJO
        User user = userMapper.mapUserRequestToUser(userRequest);

        // !!! Rol bilgisi setleniyor
        user.getRoles().add(userRoleService.getUserRole(RoleType.MEMBER));

        // !!! password encode ediliyor
        user.setPassword(passwordEncoder.encode(userRequest.getPassword()));

        User savedUser = userRepository.save(user);

        return ResponseEntity.ok(userMapper.mapUserToUserResponse(savedUser));

    }


    public ResponseMessage<UserResponse> getAuthenticatedUser(HttpServletRequest httpServletRequest) {

        String email = (String) httpServletRequest.getAttribute("email");

        User foundUser = userRepository.findByEmailEquals(email);

        return ResponseMessage.<UserResponse>builder().message(SuccessMessages.USER_FOUND)
                .httpStatus(HttpStatus.OK)
                .object(userMapper.mapUserToUserResponse(foundUser)).build();

    }

    public ResponseMessage<Page<LoanResponse>> getAllLoansByUserByPage(HttpServletRequest httpServletRequest,
                                                                       int page, int size,
                                                                       String sort, String type) {

        Pageable pageable = pageableHelper.getPageableWithProperties(page, size, sort, type);

        String email = (String) httpServletRequest.getAttribute("email");

        //     Page<LoanResponse> loans = loanService.getAllLoansByUsersEmail(email);
        // loan servicedeki methodun yazılmasını bekliyoruz

        //Loanları pojodan dtoya dönüştürmek için loanmapper gerekli.
        return ResponseMessage.<Page<LoanResponse>>builder().message(SuccessMessages.SUCCESS)
                .httpStatus(HttpStatus.OK)
                //      .object(loans)
                .build();

    }

    public ResponseMessage<Page<UserResponse>> getAllUsersByPage(int page, int size, String sort, String type) {
        Pageable pageable = pageableHelper.getPageableWithProperties(page, size, sort, type);


        return ResponseMessage.<Page<UserResponse>>builder().message(SuccessMessages.SUCCESS)
                .httpStatus(HttpStatus.OK)
                .object(userRepository.findAll(pageable).map(userMapper::mapUserToUserResponse))
                .build();

    }

    public ResponseEntity<UserResponse> getUserById(Long userId) {

        User user = methodHelper.isUserExist(userId);

        return ResponseEntity.ok(userMapper.mapUserToUserResponse(user));

    }

    public ResponseEntity<UserResponse> deleteUserById(Long userId) {
        User user = methodHelper.isUserExist(userId);

        if (!user.getLoanList().isEmpty()) {
            throw new BadRequestException(ErrorMessages.USER_HAS_LOAN);
        }

        userRepository.delete(user);

        return ResponseEntity.ok(userMapper.mapUserToUserResponse(user));   //Aslında no content 204 kodu
                                                                            // döndürmek daha mantıklı olabilir

    }
}
