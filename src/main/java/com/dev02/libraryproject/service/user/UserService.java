package com.dev02.libraryproject.service.user;

import com.dev02.libraryproject.payload.request.user.SigninRequest;
import com.dev02.libraryproject.payload.response.user.SigninResponse;
import com.dev02.libraryproject.repository.user.UserRepository;
import com.dev02.libraryproject.security.jwt.JwtUtils;
import com.dev02.libraryproject.security.service.UserDetailsImpl;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Set;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class UserService {
    private final UserRepository userRepository;
    public final JwtUtils jwtUtils;
    public final AuthenticationManager authenticationManager;
    private final PasswordEncoder passwordEncoder;

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
}
