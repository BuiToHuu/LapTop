package com.real.BanLapTop.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.real.BanLapTop.dto.request.user.RegisterRequest;
import com.real.BanLapTop.dto.request.user.UserUpdateRequest;
import com.real.BanLapTop.dto.response.UserResponse;
import com.real.BanLapTop.entity.Role;
import com.real.BanLapTop.entity.User;
import com.real.BanLapTop.exception.ResourceNotFoundException;
import com.real.BanLapTop.repository.UserRepository;
import com.real.BanLapTop.service.EmailService;
import com.real.BanLapTop.service.UserService;

//import lombok.RequiredArgsConstructor;

@Service
// @RequiredArgsConstructor
public class UserImpl implements UserService {
    @Autowired
    private EmailService emailService;
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
                .toList();
    }

    private UserResponse mapToResponse(User user) {
        UserResponse response = new UserResponse();
        response.setId(user.getId());
        response.setUsername(user.getUsername());
        response.setEmail(user.getEmail());
        response.setRole(user.getRole().name());
        response.setCreatedAt(user.getCreatedAt());
        return response;
    }

    // Lấy dữ liệu theo id
    @Override
    public UserResponse getUserById(Long id) {
        User user = userRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Không tìm thấy user với id: " + id));
        return mapToResponse(user);
    }

    // Đăng ký
    // @Override
    // public UserResponse registerUser(RegisterRequest request) {

    // User user = new User();

    // user.setUsername(request.getUsername());

    // String email = request.getEmail().trim().toLowerCase();
    // user.setEmail(email);

    // String hashedPassword = passwordEncoder.encode(request.getPassword());
    // user.setPassword(hashedPassword);

    // // mặc định USER
    // user.setRole(Role.USER);

    // User savedUser = userRepository.save(user);

    // // Sent Emial
    // emailService.sendEmail(
    // user.getEmail(),
    // "Chào mừng bạn",
    // "Tài khoản của bạn đã được tạo thành công!");
    // return mapToResponse(savedUser);
    // }
    @Override
    public UserResponse registerUser(RegisterRequest request) {

        User user = new User();

        user.setUsername(request.getUsername());

        String email = request.getEmail().trim().toLowerCase();
        user.setEmail(email);

        String hashedPassword = passwordEncoder.encode(request.getPassword());
        user.setPassword(hashedPassword);

        user.setRole(Role.USER);

        User savedUser = userRepository.save(user);

        try {
            emailService.sendEmail(
                    user.getEmail(),
                    "Chào mừng bạn",
                    "Tài khoản của bạn đã được tạo thành công!");
        } catch (Exception e) {
            System.out.println("Email error: " + e.getMessage());
        }

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
        User user = userRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Không tìm thấy user với id: " + id));
        user.setUsername(request.getUsername());
        user.setEmail(request.getEmail());
        if (request.getRole() != null) {
            user.setRole(Role.valueOf(request.getRole().toUpperCase()));
        }
        User updatedUser = userRepository.save(user);
        return mapToResponse(updatedUser);
    }

}