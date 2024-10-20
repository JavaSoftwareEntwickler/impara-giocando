package com.imparagiocando.imparagiocando.auth;

import com.imparagiocando.imparagiocando.email.EmailService;
import com.imparagiocando.imparagiocando.user.*;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.mail.MessagingException;
import java.io.IOException;
import java.security.SecureRandom;
import java.time.LocalDateTime;

@Service
@RequiredArgsConstructor
public class AuthService {
    public static final int TOKEN_LENGTH = 6;
    public static final String CHAR_TOKEN = "0123456789";
    public static final int MINUTES_OF_TOKEN_VALIDITY = 15;
    public static final String TOKEN_EXPIRED_MESSAGGE = "Activation token has expired. A new token has been send to the same email address";
    public static final String USER_NOT_FOUND_MESSAGE = "User not found";

    private final EmailService emailService;
    private final TokenService tokenService;
    private final MyUserRepository userRepository;

    private String generateAndSaveActivationToken(MyUser user) {
        String generatedToken = generateActivationCode(TOKEN_LENGTH);
        var token = Token.builder()
                .token(generatedToken)
                .createdAt(LocalDateTime.now())
                .expiresAt(LocalDateTime.now().plusMinutes(MINUTES_OF_TOKEN_VALIDITY))
                .user(user)
                .build();
        tokenService.saveToken(token);
        return generatedToken;
    }
    private String generateActivationCode(int length) {
        StringBuilder codeBuilder = new StringBuilder();
        SecureRandom secureRandom = new SecureRandom();
        for (int i = 0; i < length; i++) {
            int randomIndex = secureRandom.nextInt(CHAR_TOKEN.length());
            codeBuilder.append(CHAR_TOKEN.charAt(randomIndex));
        }
        return codeBuilder.toString();
    }
    @Transactional
    public void activateAccount(String token) throws Exception {
        Token tokenDb = tokenService.getToken(token);
        if (isTokenExpired(tokenDb)) {
            sendValidationMail(tokenDb.getUser());
            throw new RuntimeException(TOKEN_EXPIRED_MESSAGGE);
        }
        var user = userRepository.findById(tokenDb.getUser().getId())
                .orElseThrow(() -> new UsernameNotFoundException(USER_NOT_FOUND_MESSAGE));
        user.setEnabled(true);
        userRepository.save(user);
        tokenDb.setValidatedAt(LocalDateTime.now());
        tokenService.saveToken(tokenDb);
    }

    public void sendValidationMail(MyUser user) throws IOException, MessagingException {
        var newToken = generateAndSaveActivationToken(user);
        emailService.sendEMail(user, newToken);
    }

    private static boolean isTokenExpired(Token tokenDb) {
        return LocalDateTime.now().isAfter(tokenDb.getExpiresAt());
    }
}
