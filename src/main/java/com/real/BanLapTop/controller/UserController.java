package com.real.BanLapTop.controller;

import java.util.List;

import org.springframework.http.ResponseEntity;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.real.BanLapTop.dto.request.user.RegisterRequest;
import com.real.BanLapTop.dto.request.user.UserUpdateRequest;
import com.real.BanLapTop.dto.response.UserResponse;
import com.real.BanLapTop.service.UserService;

@RestController
@RequestMapping("/api/users")

public class UserController {

    private final UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    // 🔹 Lấy tất cả user
    @GetMapping("all")
    public ResponseEntity<List<UserResponse>> getAllUsers() {
        return ResponseEntity.ok(userService.getAllUsers());
    }

    // 🔹 Lấy user theo id
    @GetMapping("{id}")
    public ResponseEntity<UserResponse> getUserById(@PathVariable("id") Long id) {
        UserResponse response = userService.getUserById(id);
        return ResponseEntity.ok(response);
    }

    // 🔹 Đăng ký user mớ
    @PostMapping("register")
    public ResponseEntity<UserResponse> registerUser(@RequestBody RegisterRequest request) {
        UserResponse response = userService.registerUser(request);
        return ResponseEntity.ok(response);
    }

    // 🔹 Xóa user
    @PostMapping("delete/{id}")
    public ResponseEntity<Void> deleteUser(@PathVariable("id") Long id) {
        userService.deleteUser(id);
        return ResponseEntity.status(200).build();
    }

    // 🔹 Cập nhật user
    @PostMapping("update/{id}")
    public ResponseEntity<UserResponse> updateUser(@PathVariable("id") Long id,
            @RequestBody UserUpdateRequest request) {
        UserResponse response = userService.updateUser(id, request);
        return ResponseEntity.ok(response);
    }
}
