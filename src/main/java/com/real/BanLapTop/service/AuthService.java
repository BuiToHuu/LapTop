package com.real.BanLapTop.service;

import org.springframework.stereotype.Service;

import com.real.BanLapTop.dto.request.user.LoginRequest;
import com.real.BanLapTop.dto.request.user.RegisterRequest;
import com.real.BanLapTop.dto.response.AuthResponse;
import com.real.BanLapTop.dto.response.UserResponse;

@Service
public interface AuthService {
    AuthResponse loginUser(LoginRequest request);

}
