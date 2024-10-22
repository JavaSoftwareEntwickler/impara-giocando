package com.imparagiocando.imparagiocando.user;

import lombok.NoArgsConstructor;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
public class MyUserDetailsService implements UserDetailsService {
    @Autowired
    private MyUserService userService;
    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        MyUser userDb = userService.getUserByUsername(username);
        return (userDb == null ) ? null :
                User.builder()
                .username(userDb.getUsername())
                .password(userDb.getPassword())
                .roles(userDb.getRole().name())
                .disabled(!userDb.isEnabled())
                .build() ;
    }
}
