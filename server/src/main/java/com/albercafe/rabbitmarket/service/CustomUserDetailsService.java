package com.albercafe.rabbitmarket.service;

import com.albercafe.rabbitmarket.entity.User;
import com.albercafe.rabbitmarket.exception.RabbitMarketException;
import com.albercafe.rabbitmarket.repository.UserRepository;
import com.albercafe.rabbitmarket.security.UserPrincipal;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class CustomUserDetailsService implements UserDetailsService {

    private final UserRepository userRepository;

    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        User user = userRepository.findByEmail(email).orElseThrow(() -> new RabbitMarketException("user not found with email : " + email));

        return UserPrincipal.create(user);
    }
}
