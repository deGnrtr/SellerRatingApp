package com.leverx.javacourse.seller.rating.app.security;

import com.leverx.javacourse.seller.rating.app.entity.User;
import com.leverx.javacourse.seller.rating.app.service.UserService;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.Collections;

@Service
public class CustomUserDetailsService implements UserDetailsService {

    private final UserService userService;

    public CustomUserDetailsService(UserService userService) {
        this.userService = userService;
    }

    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        User user = userService.findByLogin(username);
        return new org.springframework.security.core.userdetails
                .User(user.getLogin(), user.getPassword(), Collections.singleton(user.getRole()));
    }
}
