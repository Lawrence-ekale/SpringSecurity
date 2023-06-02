package com.lawrenceekale.jwtspringsecurity.controller;

import com.lawrenceekale.jwtspringsecurity.models.AuthenticationResponse;
import com.lawrenceekale.jwtspringsecurity.models.RefreshTokenRequest;
import com.lawrenceekale.jwtspringsecurity.models.UserLogin;
import com.lawrenceekale.jwtspringsecurity.models.UserRegistration;
import com.lawrenceekale.jwtspringsecurity.service.UserService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/api/v2/auth")
@RequiredArgsConstructor
@Validated
public class UserController {
    @Autowired
    private final UserService userService;
    @PostMapping("/register")
    public ResponseEntity<AuthenticationResponse> registerUser(@Valid @RequestBody UserRegistration user) {

        if (userExists(user.getEmail())) {
            throw new IllegalArgumentException("Please login");
        }
        return new ResponseEntity<>(userService.registerUser(user),HttpStatus.CREATED);
    }

    @PostMapping("/login")
    public ResponseEntity<AuthenticationResponse> loginUser(@RequestBody @Valid UserLogin user) {
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
