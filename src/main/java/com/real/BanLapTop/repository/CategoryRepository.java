package com.real.BanLapTop.repository;

import java.util.*;

import org.springframework.data.jpa.repository.JpaRepository;

import com.real.BanLapTop.dto.response.CategoryResponse;
import com.real.BanLapTop.entity.Category;

public interface CategoryRepository extends JpaRepository<Category, Long> {

}