package com.lawrenceekale.jwtspringsecurity.service;

import com.lawrenceekale.jwtspringsecurity.configuration.JwtService;
import com.lawrenceekale.jwtspringsecurity.entity.Role;
import com.lawrenceekale.jwtspringsecurity.entity.User;
import com.lawrenceekale.jwtspringsecurity.models.AuthenticationResponse;
import com.lawrenceekale.jwtspringsecurity.models.UserLogin;
import com.lawrenceekale.jwtspringsecurity.models.UserRegistration;
import com.lawrenceekale.jwtspringsecurity.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UserService {
    @Autowired
    private final UserRepository userRepository;

    private final PasswordEncoder passwordEncoder;

    private final JwtService jwtService;
    private final AuthenticationManager authenticationManager;
    public AuthenticationResponse registerUser(UserRegistration user) {
        //check if all fields have been provided
        var userToRegister = User.builder()
                .fullname(user.getFullname())
                .email(user.getEmail())
                .password(passwordEncoder.encode(user.getPassword()))
                .role(Role.USER)
                .build();
        userRepository.save(userToRegister);
        var jwtToken = jwtService.generateToken(userToRegister);
        return AuthenticationResponse.builder()
                .token(jwtToken)
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
        var jwtToken = jwtService.generateToken(userToLog);
        return AuthenticationResponse.builder()
                .token(jwtToken)
                .build();
    }
}
