package com.lawrenceekale.jwtspringsecurity.controller;

import com.lawrenceekale.jwtspringsecurity.configuration.JwtService;
import com.lawrenceekale.jwtspringsecurity.entity.RefreshToken;
import com.lawrenceekale.jwtspringsecurity.models.AuthenticationResponse;
import com.lawrenceekale.jwtspringsecurity.models.RefreshTokenRequest;
import com.lawrenceekale.jwtspringsecurity.repository.UserRepository;
import com.lawrenceekale.jwtspringsecurity.service.RefreshTokenService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/api/v2/protected")
public class ProtectedController {

    @Autowired
    private JwtService jwtService;
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private RefreshTokenService refreshTokenService;
    @GetMapping
    public ResponseEntity<String> greetings() {
        return ResponseEntity.ok("Hello There ");
    }

}
