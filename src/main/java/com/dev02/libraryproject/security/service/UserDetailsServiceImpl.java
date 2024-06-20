package com.dev02.libraryproject.security.service;

import com.dev02.libraryproject.entity.concretes.user.User;
import com.dev02.libraryproject.repository.user.UserRepository;
import lombok.AllArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class UserDetailsServiceImpl implements UserDetailsService {

    private final UserRepository userRepository;

    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        User user = userRepository.findByEmailEquals(email);

        if(user != null){
            return new UserDetailsImpl(
                    user.getId(),
                    user.getEmail(),
                    false,
                    user.getPassword());
                    //user.getUserRole().getRoleType().name() bizde bir kullanıcının birden fazla rolü olabiliyor.
                    // bu durumda bu kısmı nasıl düşünmek lazım?

        }
        throw new UsernameNotFoundException("User' " + email + " not found");
    }
}
