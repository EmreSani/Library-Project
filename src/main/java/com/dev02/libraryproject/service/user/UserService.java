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
import com.dev02.libraryproject.payload.request.user.UserRequestForCreateOrUpdate;
import com.dev02.libraryproject.payload.request.user.UserRequestForRegister;
import com.dev02.libraryproject.payload.response.business.LoanResponse;
import com.dev02.libraryproject.payload.response.business.ResponseMessage;
import com.dev02.libraryproject.payload.response.user.SigninResponse;
import com.dev02.libraryproject.payload.response.user.UserResponse;
import com.dev02.libraryproject.repository.user.UserRepository;
import com.dev02.libraryproject.repository.user.UserRoleRepository;
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
import org.springframework.transaction.annotation.Transactional;

import javax.servlet.http.HttpServletRequest;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Objects;
import java.util.Set;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Transactional
public class UserService {
    private final UserRepository userRepository;
    private final UserRoleRepository userRoleRepository;
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
                .email(userDetails.getEmail())
                .token(token.substring(7))
                .name(userDetails.getName())
                .roles(roles)
                .build();

        // SigninResponse nesnesi ResponseEntity ile donduruluyor
        return ResponseEntity.ok(signinResponse);
    }

    public ResponseEntity<UserResponse> register(UserRequestForRegister userRequestForRegister) {

        //!!! username - ssn- phoneNumber unique mi kontrolu ??
        uniquePropertyValidator.checkDuplicate(userRequestForRegister.getEmail(),
                userRequestForRegister.getPhone());
        //!!! DTO --> POJO
        User user = userMapper.mapUserRequestToUser(userRequestForRegister);
        if (user.getRoles() == null) {
            user.setRoles(new ArrayList<>());
        }

        user.setBuiltIn(false);
        // !!! Rol bilgisi setleniyor
        user.getRoles().add(userRoleService.getUserRole(RoleType.MEMBER));

        // !!! password encode ediliyor
        user.setPassword(passwordEncoder.encode(userRequestForRegister.getPassword()));

        user.setCreateDate(LocalDateTime.now()); // Automatically set on create

        user.setScore(0);
        User savedUser = userRepository.save(user);

        return ResponseEntity.ok(userMapper.mapUserToUserResponse(savedUser));

    }


    public ResponseMessage<UserResponse> getAuthenticatedUser(HttpServletRequest httpServletRequest) {

        String email = (String) httpServletRequest.getAttribute("username");

        User foundUser = userRepository.findByEmail(email);

        return ResponseMessage.<UserResponse>builder().message(SuccessMessages.USER_FOUND)
                .httpStatus(HttpStatus.OK)
                .object(userMapper.mapUserToUserResponse(foundUser)).build();

    }

    public ResponseEntity<Page<LoanResponse>> getAllLoansByUserByPage(HttpServletRequest httpServletRequest,
                                                                      int page, int size,
                                                                      String sort, String type) {

        String email = (String) httpServletRequest.getAttribute("username");

        User foundUser = userRepository.findByEmail(email);

        return loanService.getAllLoansByUserIdByPage(foundUser.getId(), page, size, sort, type);

    }

    public ResponseMessage<Page<UserResponse>> getAllUsersByPage(int page, int size, String sort, String type) {
        Pageable pageable = pageableHelper.getPageableWithProperties(page, size, sort, type);


        return ResponseMessage.<Page<UserResponse>>builder().message(SuccessMessages.SUCCESS)
                .httpStatus(HttpStatus.OK)
                .object(userRepository.findAll(pageable).map(userMapper::mapUserToUserResponse))
                .build();

    }

    @Transactional
    public ResponseEntity<UserResponse> getUserById(Long userId) {

        User user = methodHelper.isUserExist(userId);

        return ResponseEntity.ok(userMapper.mapUserToUserResponse(user));

    }

    public ResponseEntity<UserResponse> deleteUserById(Long userId) {
        User user = methodHelper.isUserExist(userId);

        methodHelper.checkBuiltIn(user);

        if (!user.getLoanList().isEmpty()) {
            throw new BadRequestException(ErrorMessages.USER_HAS_LOAN);
        }

        userRepository.delete(user);

        return ResponseEntity.ok(userMapper.mapUserToUserResponse(user));   //Aslında no content 204 kodu
        // döndürmek daha mantıklı olabilir

    }


    public ResponseEntity<UserResponse> createUser(UserRequestForCreateOrUpdate userRequestForCreateOrUpdate, HttpServletRequest httpServletRequest, String userRole) {

        String email = (String) httpServletRequest.getAttribute("username");

        User foundUser = userRepository.findByEmail(email);

        //!!! email - phoneNumber unique mi kontrolu ??
        uniquePropertyValidator.checkDuplicate(userRequestForCreateOrUpdate.getEmail(),
                userRequestForCreateOrUpdate.getPhone());
        //!!! DTO --> POJO
        User userToCreate = userMapper.mapUserRequestToUser(userRequestForCreateOrUpdate);

        if (userToCreate.getRoles() == null) {
            userToCreate.setRoles(new ArrayList<>());
        }

        setRoleForNewUser(foundUser, userToCreate, userRole);

        userToCreate.setCreateDate(LocalDateTime.now());

        // !!! password encode ediliyor
        userToCreate.setPassword(passwordEncoder.encode(userRequestForCreateOrUpdate.getPassword()));

        User savedUser = userRepository.save(userToCreate);

        return ResponseEntity.ok(userMapper.mapUserToUserResponse(savedUser));


    }

    //create user methodunda role bilgisi setlemek için yazıldı, yardımcı
    private void setRoleForNewUser(User foundUser, User userToCreate, String userRole) {
        if (foundUser.getRoles().contains(userRoleService.getUserRole(RoleType.ADMIN))) {
            switch (userRole.toLowerCase()) {
                case "member":
                    userToCreate.getRoles().add(userRoleService.getUserRole(RoleType.MEMBER));
                    break;
                case "employee":
                    userToCreate.getRoles().add(userRoleService.getUserRole(RoleType.EMPLOYEE));
                    break;
                case "admin":
                    userToCreate.getRoles().add(userRoleService.getUserRole(RoleType.ADMIN));
                    break;
                default:
                    throw new ResourceNotFoundException((ErrorMessages.ROLE_NOT_FOUND));
            }
        } else if (foundUser.getRoles().contains(userRoleService.getUserRole(RoleType.EMPLOYEE))) {
            if (userRole.equalsIgnoreCase("Member")) {
                userToCreate.getRoles().add(userRoleService.getUserRole(RoleType.MEMBER));
            } else {
                throw new BadRequestException(ErrorMessages.DONT_HAVE_AUTHORITY);
            }
        }
    }

    public ResponseEntity<UserResponse> updateUser(UserRequestForCreateOrUpdate userRequestForCreateOrUpdate, Long userId, HttpServletRequest httpServletRequest) {
        String email = (String) httpServletRequest.getAttribute("username");
        // işlemi yapan user
        User foundUser = userRepository.findByEmail(email);

        // güncellenecek user
        User userToUpdate = methodHelper.isUserExist(userId);

        // Role based update permission ve built-in kontrolü
        checkUpdatePermission(foundUser, userToUpdate);

        // email - phoneNumber unique mi kontrolü
        uniquePropertyValidator.checkUniqueProperties(userToUpdate, userRequestForCreateOrUpdate);

        User updatedUser = userMapper.mapUserRequestToUpdatedUser(userRequestForCreateOrUpdate, userId);

        updatedUser.setPassword(passwordEncoder.encode(userRequestForCreateOrUpdate.getPassword()));
        updatedUser.setRoles(userToUpdate.getRoles());
        updatedUser.setCreateDate(foundUser.getCreateDate());

        User savedUser = userRepository.save(updatedUser);

        return ResponseEntity.ok(userMapper.mapUserToUserResponse(savedUser));

    }

    //updateUser için yazıldı controller bağlantısı yok , yardımcı
    private void checkUpdatePermission(User foundUser, User userToUpdate) {
        methodHelper.checkBuiltIn(userToUpdate);

        if (foundUser.getRoles().contains(userRoleService.getUserRole(RoleType.ADMIN))) {
            if (userToUpdate.getBuiltIn()) {
                throw new BadRequestException(ErrorMessages.NOT_PERMITTED_METHOD_MESSAGE);
            }
        } else if (foundUser.getRoles().contains(userRoleService.getUserRole(RoleType.EMPLOYEE))) {
            if (userToUpdate.getRoles().contains(userRoleService.getUserRole(RoleType.EMPLOYEE)) ||
                    userToUpdate.getRoles().contains(userRoleService.getUserRole(RoleType.ADMIN))) {
                throw new BadRequestException("Employee can only update member users.");
            }
        } else {
            throw new BadRequestException("You do not have permission to update this user.");
        }
    }

    public ResponseEntity<Page<UserResponse>> getAllUsersMostBorrowersByPage(int page, int size) {
        Pageable pageable = pageableHelper.getPageableWithProperties(page, size);

        return ResponseEntity.ok(userRepository.findByUsersMostBorrowers(pageable).map(userMapper::mapUserToUserResponse));
    }

    public long countAllAdmins() {
        return userRepository.countAdmin(RoleType.ADMIN);
    }

    public ResponseMessage<UserResponse> saveUser(UserRequest adminRequest, String userRole) {

        //!!! username - ssn- phoneNumber unique mi kontrolu ??
        uniquePropertyValidator.checkDuplicate(adminRequest.getPhone(), adminRequest.getEmail());
        //!!! DTO --> POJO
        User user = userMapper.mapUserRequestForAdminToUser(adminRequest);
        // Initialize roles list if null
        if (user.getRoles() == null) {
            user.setRoles(new ArrayList<>());
        }
        // !!! Rol bilgisi setleniyor
        if (userRole.equalsIgnoreCase(RoleType.ADMIN.name())) {
            if (Objects.equals(adminRequest.getEmail(), "admin@admin.com")) {
                user.setBuiltIn(true);
            }
            user.getRoles().add(userRoleService.getUserRole(RoleType.ADMIN));
        }
        // !!! password encode ediliyor
        user.setPassword(passwordEncoder.encode(user.getPassword()));

        user.setCreateDate(LocalDateTime.now()); // Automatically set on create

        user.setScore(2);
        User savedUser = userRepository.save(user);

        return ResponseMessage.<UserResponse>builder()
                .message(SuccessMessages.ADMIN_CREATE)
                .object(userMapper.mapUserToUserResponse(savedUser))
                .build();
    }
}
