package com.dev02.libraryproject.security.service;

import com.dev02.libraryproject.entity.concretes.user.User;
import com.dev02.libraryproject.repository.user.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class UserDetailsServiceImpl implements UserDetailsService {

//    private final UserRepository userRepository;
//
//    @Override
//    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
//        User user = userRepository.findByEmail(email);
//
//        if (user != null) {
////            Collection<? extends GrantedAuthority> authorities = user.getRoles().stream()
////                    .map(role -> new SimpleGrantedAuthority(role.getRoleType().name()))
////                    .collect(Collectors.toList());
//            return new UserDetailsImpl(
//                    user.getId(),
//                    user.getEmail(),
//                    user.getFirstName(),
//                    user.getPassword(),
//                    user.getRoles()
//            );
//
//        }
//        throw new UsernameNotFoundException("User' " + email + " not found");
//    }

    private final UserRepository userRepository;

    @Override
    @Transactional
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        User user = userRepository.findByEmail(email);

        if (user != null) {
            return new UserDetailsImpl(
                    user.getId(),
                    user.getEmail(),
                    user.getFirstName(),
                    user.getPassword(),
                    user.getRoles()
            );
        }
        throw new UsernameNotFoundException("User '" + email + "' not found");
    }
}
