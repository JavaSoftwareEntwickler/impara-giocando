package com.imparagiocando.imparagiocando.user;

import com.imparagiocando.imparagiocando.auth.AuthService;
import com.imparagiocando.imparagiocando.email.EmailService;
import jakarta.annotation.PostConstruct;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.mail.MessagingException;
import java.io.IOException;
@Slf4j
@Service
@Transactional
@RequiredArgsConstructor
public class MyUserService {
    private final MyUserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final AuthService authService;

    public MyUser getUserByUsername(String username){ return userRepository.findMyUserByUsername(username); }

    public void registerUser(MyUser user){
        var userToSave = MyUser.builder()
                .username(user.getUsername())
                .password(passwordEncoder.encode(user.getPassword()))
                .role(MyUserRole.USER)
                .enabled(false)
                .build();
                userRepository.save(userToSave);
        try {
            authService.sendValidationMail(userToSave);
        } catch (IOException e) {
            log.error("Invio mail errore {}",e);
        } catch (MessagingException e) {
            throw new RuntimeException(e);
        }

    }
    //Only for per development
    @PostConstruct
    public void postConstruct(){
        try{
            userRepository.save(MyUser.builder()
                    .username("user@user.com")
                    .password(passwordEncoder.encode("user"))
                    .role(MyUserRole.USER)
                    .build());
        userRepository.save(MyUser.builder()
                        .username("admin@admin.com")
                        .password(passwordEncoder.encode("admin"))
                        .role(MyUserRole.ADMIN)
                .build());
        userRepository.save(MyUser.builder()
                .username("super@super.com")
                .password(passwordEncoder.encode("super"))
                .role(MyUserRole.SUPER_USER)
                .build());
        }catch (DataIntegrityViolationException e){

        }
    }

}
