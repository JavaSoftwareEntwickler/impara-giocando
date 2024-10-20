package com.imparagiocando.imparagiocando.auth;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
public class CustomUserDetailsService implements UserDetailsService {

    @Autowired
    private MyUserServiceImpl customUserService;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        MyUser userDb = customUserService.getUserByUsername(username);
        return (userDb == null ) ? null : User.builder().username(userDb.getUsername()).password(userDb.getPassword()).roles(userDb.getRole().name()).build() ;
    }
}
