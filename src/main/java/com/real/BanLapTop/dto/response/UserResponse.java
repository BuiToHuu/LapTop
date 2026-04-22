package com.real.BanLapTop.dto.response;

import java.time.LocalDateTime;

public class UserResponse {

    private Long id;
    private String fullName;
    private String address;
    private String phone;
    private String username;
    private String email;
    private String role;
    private String status;
    private LocalDateTime createdAt;

    // Constructor rỗng
    public UserResponse() {
    }

    // Constructor đầy đủ
    public UserResponse(Long id, String fullName, String address, String phone,
            String username, String email, String role,
            String status, LocalDateTime createdAt) {
        this.id = id;
        this.fullName = fullName;
        this.address = address;
        this.phone = phone;
        this.username = username;
        this.email = email;
        this.role = role;
        this.status = status;
        this.createdAt = createdAt;
    }

    // ===== Getter =====
    public Long getId() {
        return id;
    }

    public String getFullName() {
        return fullName;
    }

    public String getAddress() {
        return address;
    }

    public String getPhone() {
        return phone;
    }

    public String getUsername() {
        return username;
    }

    public String getEmail() {
        return email;
    }

    public String getRole() {
        return role;
    }

    public String getStatus() {
        return status;
    }

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

    // ===== Setter =====
    public void setId(Long id) {
        this.id = id;
    }

    public void setFullName(String fullName) {
        this.fullName = fullName;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public void setRole(String role) {
        this.role = role;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public void setCreatedAt(LocalDateTime createdAt) {
        this.createdAt = createdAt;
    }
}