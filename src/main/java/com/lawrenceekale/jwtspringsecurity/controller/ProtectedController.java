package com.lawrenceekale.jwtspringsecurity.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/api/v2/protected")
public class ProtectedController {

    @GetMapping
    public ResponseEntity<String> greetings() {
        return ResponseEntity.ok("Hello There ");
    }
}
