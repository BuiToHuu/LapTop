package com.real.BanLapTop.service;

import java.util.List;

import com.real.BanLapTop.dto.request.Category.CategoryRequest;
import com.real.BanLapTop.dto.response.CategoryResponse;

public interface CategoryService {
    List<CategoryResponse> getAllCategories();

    // List<CategoryResponse> getSearch();

    CategoryResponse createCategory(CategoryRequest request);

    void deleteCategory(Long id);

    CategoryResponse getCategoryById(Long id);
}
