package com.lawrenceekale.jwtspringsecurity.service;

import com.lawrenceekale.jwtspringsecurity.configuration.JwtService;
import com.lawrenceekale.jwtspringsecurity.entity.RefreshToken;
import com.lawrenceekale.jwtspringsecurity.entity.Role;
import com.lawrenceekale.jwtspringsecurity.entity.User;
import com.lawrenceekale.jwtspringsecurity.models.AuthenticationResponse;
import com.lawrenceekale.jwtspringsecurity.models.RefreshTokenRequest;
import com.lawrenceekale.jwtspringsecurity.models.UserLogin;
import com.lawrenceekale.jwtspringsecurity.models.UserRegistration;
import com.lawrenceekale.jwtspringsecurity.repository.RefreshTokenRepository;
import com.lawrenceekale.jwtspringsecurity.repository.UserRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class UserService {
    @Autowired
    private final UserRepository userRepository;

    private final PasswordEncoder passwordEncoder;

    private final JwtService jwtService;
    @Autowired
    private final RefreshTokenService refreshTokenService;
    @Autowired
    private final RefreshTokenRepository refreshTokenRepository;
    private final AuthenticationManager authenticationManager;
    public AuthenticationResponse registerUser(UserRegistration user) {
        var userToRegister = User.builder()
                .fullname(user.getFullname())
                .email(user.getEmail())
                .password(passwordEncoder.encode(user.getPassword()))
                .role(Role.USER)
                .build();
        userRepository.save(userToRegister);
        return AuthenticationResponse.builder()
                .token("User registered Successfully")
                .build();
    }

    public AuthenticationResponse loginUser(UserLogin user) {
        authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        user.getEmail(),
                        user.getPassword()
                )
        );

        var userToLog = userRepository.findByEmail(user.getEmail()).orElseThrow();
        RefreshToken refreshToken =  refreshTokenService.generateRefereshToken(user.getEmail());
        return AuthenticationResponse.builder()
                .token(jwtService.generateToken(userToLog))
                .refreshToken(refreshToken.getToken())
                .build();
    }

    public Boolean userExists(String email) {
        Optional<User> user = userRepository.findByEmail(email);
        return user.isPresent();
    }

    public AuthenticationResponse refreshToken(RefreshTokenRequest request) {
        return refreshTokenService.findToken(request.getToken())
                .map(refreshTokenService::verifyExpiration)
                .map(RefreshToken::getUser)
                .map(
                        user -> {
                            var userToLog = userRepository.findByEmail(user.getEmail()).orElseThrow();
                            String accessToken = jwtService.generateToken(userToLog);
                            return AuthenticationResponse.builder()
                                    .token(accessToken)
                                    .refreshToken(request.getToken())
                                    .build();
                        }
                ).orElseThrow(()-> new RuntimeException("Refresh token not in database!"));
    }

    @Transactional
    public void logout(RefreshTokenRequest request) {
        RefreshToken token = refreshTokenRepository.findByToken(request.getToken()).orElseThrow();
        refreshTokenRepository.deleteByUserId(token.getUser().getUserId());
    }
}
