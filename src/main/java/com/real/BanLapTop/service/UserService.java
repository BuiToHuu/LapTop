package com.real.BanLapTop.service;

import java.util.List;

import org.springframework.stereotype.Service;

import com.real.BanLapTop.dto.request.RegisterRequest;
import com.real.BanLapTop.dto.request.UserUpdateRequest;
import com.real.BanLapTop.dto.response.UserResponse;

@Service
public interface UserService {
    List<UserResponse> getAllUsers();
    UserResponse getUserById(Long id);
    UserResponse registerUser(RegisterRequest request);
    UserResponse updateUser(Long id, UserUpdateRequest request);
    void deleteUser(Long id);

}
