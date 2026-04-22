package com.real.BanLapTop.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.real.BanLapTop.dto.request.user.ChangePasswordRequest;
import com.real.BanLapTop.dto.request.user.RegisterRequest;
import com.real.BanLapTop.dto.request.user.UserUpdateRequest;
import com.real.BanLapTop.dto.response.UserResponse;
import com.real.BanLapTop.entity.Role;
import com.real.BanLapTop.entity.User;
import com.real.BanLapTop.entity.UserStatus;
import com.real.BanLapTop.exception.ResourceNotFoundException;
import com.real.BanLapTop.repository.UserRepository;
import com.real.BanLapTop.service.EmailService;
import com.real.BanLapTop.service.UserService;

@Service
public class UserImpl implements UserService {

    @Autowired
    private EmailService emailService;

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    public UserImpl(UserRepository userRepository, PasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
    }

    // =========================
    // MAP ENTITY -> DTO
    // =========================
    private UserResponse mapToResponse(User user) {
        UserResponse response = new UserResponse();
        response.setId(user.getId());
        response.setUsername(user.getUsername());
        response.setEmail(user.getEmail());
        response.setRole(user.getRole().name());
        response.setStatus(user.getStatus().name()); // 🔥
        response.setAddress(user.getAddress());
        response.setFullName(user.getFullName());
        response.setPhone(user.getPhone());
        response.setCreatedAt(user.getCreatedAt());
        return response;
    }

    // =========================
    // GET ALL
    // =========================
    @Override
    public List<UserResponse> getAllUsers() {
        return userRepository.findAll()
                .stream()
                .map(this::mapToResponse)
                .toList();
    }

    // =========================
    // GET BY ID
    // =========================
    @Override
    public UserResponse getUserById(Long id) {
        User user = userRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Không tìm thấy user với id: " + id));
        return mapToResponse(user);
    }

    // =========================
    // REGISTER
    // =========================
    @Override
    public UserResponse registerUser(RegisterRequest request) {

        User user = new User();

        user.setUsername(request.getUsername());

        String email = request.getEmail().trim().toLowerCase();
        user.setEmail(email);

        user.setPassword(passwordEncoder.encode(request.getPassword()));

        // role
        if (request.getRole() != null) {
            user.setRole(Role.valueOf(request.getRole().toUpperCase()));
        } else {
            user.setRole(Role.USER);
        }

        // 🔥 STATUS MẶC ĐỊNH
        user.setStatus(UserStatus.ACTIVE);

        User savedUser = userRepository.save(user);

        // gửi email (fail không crash)
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

    // =========================
    // ❌ KHÔNG DELETE THẬT
    // 👉 CHỈ BAN USER
    // =========================
    @Override
    public void deleteUser(Long id) {
        User user = userRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Không tìm thấy user"));

        user.setStatus(UserStatus.BANNED); // 🔥
        userRepository.save(user);
    }

    // =========================
    // UPDATE USER
    // =========================
    @Override
    public UserResponse updateUser(Long id, UserUpdateRequest request) {

        User user = userRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Không tìm thấy user"));

        user.setUsername(request.getUsername());
        user.setEmail(request.getEmail());
        user.setFullName(request.getFullName());
        user.setAddress(request.getAddress());
        user.setPhone(request.getPhone());

        // role
        if (request.getRole() != null) {
            user.setRole(Role.valueOf(request.getRole().toUpperCase()));
        }

        // 🔥 status
        if (request.getStatus() != null) {
            user.setStatus(UserStatus.valueOf(request.getStatus().toUpperCase()));
        }

        // 🔥 password optional
        if (request.getPassword() != null && !request.getPassword().isEmpty()) {
            user.setPassword(passwordEncoder.encode(request.getPassword()));
        }

        User updatedUser = userRepository.save(user);
        return mapToResponse(updatedUser);
    }

    // =========================
    // UPDATE STATUS RIÊNG (ADMIN)
    // =========================
    @Override
    public UserResponse updateStatus(Long id, String status) {

        User user = userRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Không tìm thấy user"));

        user.setStatus(UserStatus.valueOf(status.toUpperCase()));

        userRepository.save(user);

        return mapToResponse(user);
    }

    // =========================
    // CHANGE PASSWORD
    // =========================
    @Override
    public void changePassword(String email, ChangePasswordRequest request) {

        User user = userRepository.findByEmail(email)
                .orElseThrow(() -> new ResourceNotFoundException("Không tìm thấy user"));

        // check mật khẩu cũ
        if (!passwordEncoder.matches(request.getOldPassword(), user.getPassword())) {
            throw new RuntimeException("Mật khẩu cũ không đúng");
        }

        // không cho trùng
        if (request.getOldPassword().equals(request.getNewPassword())) {
            throw new RuntimeException("Mật khẩu mới phải khác mật khẩu cũ");
        }

        user.setPassword(passwordEncoder.encode(request.getNewPassword()));
        userRepository.save(user);
    }
}