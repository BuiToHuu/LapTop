package com.real.BanLapTop.service;

import java.util.List;



import com.real.BanLapTop.dto.request.user.RegisterRequest;
import com.real.BanLapTop.dto.request.user.UserUpdateRequest;
import com.real.BanLapTop.dto.response.UserResponse;


public interface UserService {
    List<UserResponse> getAllUsers();
    UserResponse getUserById(Long id);
    UserResponse registerUser(RegisterRequest request);
    UserResponse updateUser(Long id, UserUpdateRequest request);
    void deleteUser(Long id);

}
