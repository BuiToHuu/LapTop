package com.real.BanLapTop.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.real.BanLapTop.dto.request.LoginRequest;
import com.real.BanLapTop.dto.response.AuthResponse;
import com.real.BanLapTop.service.AuthService;


@RestController
@RequestMapping("/api/auth")

public class AuthController {
    private final AuthService authService;
    public AuthController(AuthService authService) {
        this.authService = authService;
    }
    @PostMapping("/login")
    public ResponseEntity<AuthResponse> loginUser(@RequestBody LoginRequest request) {
        AuthResponse response = authService.loginUser(request);
        return ResponseEntity.ok(response);
    }
}
