package com.dev02.libraryproject.security.service;

import com.dev02.libraryproject.entity.concretes.user.User;
import com.dev02.libraryproject.repository.user.UserRepository;
import lombok.AllArgsConstructor;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.Collection;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor
public class UserDetailsServiceImpl implements UserDetailsService {

    private final UserRepository userRepository;

    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        User user = userRepository.findByEmailEquals(email);

        if (user != null) {
            Collection<? extends GrantedAuthority> authorities = user.getRoles().stream()
                    .map(role -> new SimpleGrantedAuthority(role.getRoleType().name()))
                    .collect(Collectors.toList());

            return new UserDetailsImpl(
                    user.getId(),
                    user.getEmail(),
                    user.getFirstName(),
                    user.getPassword(),
                    authorities
            );

        }
        throw new UsernameNotFoundException("User' " + email + " not found");
    }
}
