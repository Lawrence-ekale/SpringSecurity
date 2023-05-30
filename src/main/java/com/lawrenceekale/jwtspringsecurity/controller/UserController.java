package com.lawrenceekale.jwtspringsecurity.controller;

import com.lawrenceekale.jwtspringsecurity.models.AuthenticationResponse;
import com.lawrenceekale.jwtspringsecurity.models.UserLogin;
import com.lawrenceekale.jwtspringsecurity.models.UserRegistration;
import com.lawrenceekale.jwtspringsecurity.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
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
        return ResponseEntity.ok(userService.registerUser(user));
    }

    @PostMapping("/login")
    public ResponseEntity<AuthenticationResponse> loginUser(@RequestBody UserLogin user) {
        return ResponseEntity.ok(userService.loginUser(user));
    }
}
