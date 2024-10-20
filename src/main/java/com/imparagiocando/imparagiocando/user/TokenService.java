package com.imparagiocando.imparagiocando.user;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class TokenService {
    private final TokenRepository tokenRepository;

    public Token getToken(String token){
        return tokenRepository.findByToken(token).orElseThrow(() -> new RuntimeException("Invalid token"));
    }
    public void saveToken(Token token) {
        tokenRepository.save(token);
    }
}
