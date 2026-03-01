package com.real.BanLapTop.security;

import com.real.BanLapTop.entity.UserEntity;
import com.real.BanLapTop.repository.UserRepository;

// import lombok.RequiredArgsConstructor;

import org.springframework.security.core.userdetails.*;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.stereotype.Service;

import java.util.Collections;


@Service
public class CustomUserDetailsService implements UserDetailsService {

    private final UserRepository userRepository;

    public CustomUserDetailsService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    public UserDetails loadUserByUsername(String username)
            throws UsernameNotFoundException {

        UserEntity user = userRepository.findByUsername(username)
                .orElseThrow(() ->
                        new UsernameNotFoundException("User not found"));

        SimpleGrantedAuthority authority =
                new SimpleGrantedAuthority("ROLE_" + user.getRole().name());

        return new User(
                user.getUsername(),
                user.getPassword(),
                Collections.singleton(authority)
        );
    }
}
