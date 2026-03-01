package com.real.BanLapTop.service;

import org.springframework.stereotype.Service;

import com.real.BanLapTop.dto.request.LoginRequest;
import com.real.BanLapTop.dto.response.AuthResponse;


@Service
public interface AuthService {
    AuthResponse loginUser(LoginRequest request);
}
