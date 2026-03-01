package com.real.BanLapTop.service.impl;

import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.real.BanLapTop.security.*;
import com.real.BanLapTop.dto.request.LoginRequest;
import com.real.BanLapTop.dto.response.AuthResponse;
import com.real.BanLapTop.entity.UserEntity;
import com.real.BanLapTop.exception.ResourceNotFoundException;
import com.real.BanLapTop.repository.UserRepository;
import com.real.BanLapTop.service.AuthService;



@Service
public class AuthImpl implements AuthService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtUtil jwtUtil;

    public AuthImpl(UserRepository userRepository, PasswordEncoder passwordEncoder, JwtUtil jwtUtil) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
        this.jwtUtil = jwtUtil;
    }
    
    @Override
    public AuthResponse loginUser(LoginRequest request) {
    // 1. Tìm user theo username
    UserEntity user = userRepository.findByUsername(request.getUsername())
            .orElseThrow(() -> new ResourceNotFoundException("Username không tồn tại!"));

    // 2. Kiểm tra mật khẩu (So sánh mật khẩu thô và mật khẩu đã mã hóa trong DB)
    if (!passwordEncoder.matches(request.getPassword(), user.getPassword())) {
        throw new RuntimeException("Mật khẩu không chính xác!");
    }

    // 3. Tạo Token (Giả sử Huu đã có lớp JwtUtil)
    String token = jwtUtil.generateToken(user.getUsername(), user.getRole().name());

    // 4. Trả về AuthResponse chứa Token
    AuthResponse response = new AuthResponse();
    response.setToken(token);
    return response;
}
}
