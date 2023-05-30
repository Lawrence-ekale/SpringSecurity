package com.lawrenceekale.jwtspringsecurity.controller;

import com.lawrenceekale.jwtspringsecurity.entity.RefreshToken;
import com.lawrenceekale.jwtspringsecurity.models.AuthenticationResponse;
import com.lawrenceekale.jwtspringsecurity.models.RefreshTokenRequest;
import com.lawrenceekale.jwtspringsecurity.models.UserLogin;
import com.lawrenceekale.jwtspringsecurity.models.UserRegistration;
import com.lawrenceekale.jwtspringsecurity.service.RefreshTokenService;
import com.lawrenceekale.jwtspringsecurity.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/api/v2/auth")
@RequiredArgsConstructor
public class UserController {
    @Autowired
    private final UserService userService;
    @PostMapping("/register")
    public ResponseEntity<AuthenticationResponse> registerUser(@RequestBody UserRegistration user) {
        if(user.getFullname().isEmpty() || user.getEmail().isEmpty() || user.getPassword().isEmpty()) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(null);
        }

        if (userExists(user.getEmail())) {
            return ResponseEntity.status(HttpStatus.CONFLICT).body(null);
        }
        return ResponseEntity.ok(userService.registerUser(user));
    }

    @PostMapping("/login")
    public ResponseEntity<AuthenticationResponse> loginUser(@RequestBody UserLogin user) {
        return ResponseEntity.ok(userService.loginUser(user));
    }


    @PostMapping("/refreshToken")
    public ResponseEntity<AuthenticationResponse> refreshToken(@RequestBody RefreshTokenRequest request) {
        return ResponseEntity.ok(userService.refreshToken(request));
    }

    @PostMapping("/logout")
    public ResponseEntity<String> logout(@RequestBody RefreshTokenRequest request) {
        userService.logout(request);
        return ResponseEntity.ok().body(null);
    }

    public Boolean userExists(String email) {
        return userService.userExists(email);
    }
}
