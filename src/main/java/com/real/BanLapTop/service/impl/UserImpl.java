package com.real.BanLapTop.service.impl;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.context.ApplicationContextException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.real.BanLapTop.dto.request.RegisterRequest;
import com.real.BanLapTop.dto.request.UserUpdateRequest;
import com.real.BanLapTop.dto.response.UserResponse;
import com.real.BanLapTop.entity.Role;
import com.real.BanLapTop.entity.UserEntity;
import com.real.BanLapTop.repository.UserRepository;
import com.real.BanLapTop.service.UserService;

//import lombok.RequiredArgsConstructor;

@Service
//@RequiredArgsConstructor
public class UserImpl implements UserService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    public UserImpl(UserRepository userRepository, PasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
    }

    // Lấy dữ liệu
    @Override
    public List<UserResponse> getAllUsers() {
        return userRepository.findAll()
        .stream()
        .map(this::mapToResponse)
        .collect(Collectors.toList());
        }

        private UserResponse mapToResponse(UserEntity user) {
        return UserResponse.builder()
        .id(user.getId())
        .username(user.getUsername())
        .email(user.getEmail())
        .role(user.getRole().name())
        .createdAt(user.getCreatedAt())
        .updatedAt(user.getUpdatedAt())
        .build();
        }

    // Lấy dữ liệu theo id
    @Override
    public UserResponse getUserById(Long id) {
        UserEntity user = userRepository.findById(id)
                .orElseThrow(() -> new ApplicationContextException("Không tìm thấy user với id: " + id));
        return mapToResponse(user);
    }

    // Đăng ký
    @Override
    public UserResponse registerUser(RegisterRequest request) {
        UserEntity user = new UserEntity();
        user.setUsername(request.getUsername());
        user.setEmail(request.getEmail());
        String hashedPassword = passwordEncoder.encode(request.getPassword());
        user.setPassword(hashedPassword);
        if (request.getRole() != null) {
        user.setRole(Role.valueOf(request.getRole().toUpperCase()));
        } else {
        user.setRole(Role.USER);
        }


        UserEntity savedUser = userRepository.save(user);
        return mapToResponse(savedUser);
    }

    // Xóa user
    @Override
    public void deleteUser(Long id) {
        userRepository.deleteById(id);
    }

    // Cập nhật user
    @Override
    public UserResponse updateUser(Long id, UserUpdateRequest request) {
        UserEntity user = userRepository.findById(id)
                .orElseThrow(() -> new ApplicationContextException("Không tìm thấy user với id: " + id));
        user.setUsername(request.getUsername());
        user.setEmail(request.getEmail());
        if (request.getRole() != null) {
        user.setRole(Role.valueOf(request.getRole().toUpperCase()));
        }
        UserEntity updatedUser = userRepository.save(user);
        return mapToResponse(updatedUser);
    }

}