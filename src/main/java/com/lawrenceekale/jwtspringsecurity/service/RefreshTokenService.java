package com.lawrenceekale.jwtspringsecurity.service;

import com.lawrenceekale.jwtspringsecurity.entity.RefreshToken;
import com.lawrenceekale.jwtspringsecurity.repository.RefreshTokenRepository;
import com.lawrenceekale.jwtspringsecurity.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.util.Optional;
import java.util.UUID;

@Service
public class RefreshTokenService {
    @Autowired
    private RefreshTokenRepository refreshTokenRepository;

    @Autowired
    private UserRepository userRepository;


    public RefreshToken generateRefereshToken(String username) {
        RefreshToken ref =  RefreshToken.builder()
                .user(userRepository.findByEmail(username).get())
                .token(UUID.randomUUID().toString())
                .expirydate(Instant.now().plusMillis(600000))// 10 minutes
                .build();

       return  refreshTokenRepository.save(ref);
    }

    public Optional<RefreshToken> findToken(String token) {
        return refreshTokenRepository.findByToken(token);
    }

    public RefreshToken verifyExpiration(RefreshToken token) {
        if(token.getExpirydate().compareTo(Instant.now()) < 0) {
            refreshTokenRepository.delete(token);
            throw new RuntimeException(token.getToken() + " Token expired. Make a sign in request");
        }
        return token;
    }
}
