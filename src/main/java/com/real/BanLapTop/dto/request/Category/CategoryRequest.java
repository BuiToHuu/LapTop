package com.real.BanLapTop.dto.request.Category;

import jakarta.persistence.Column;

public class CategoryRequest {
    @Column(length = 100, nullable = false)
    private String name;
    @Column(length = 500, nullable = false)
    private String description;

    // getters and setters
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }
}
