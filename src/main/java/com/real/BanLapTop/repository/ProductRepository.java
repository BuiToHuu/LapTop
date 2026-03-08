package com.real.BanLapTop.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.real.BanLapTop.entity.Product;

public interface ProductRepository extends JpaRepository<Product, Long> {
    List<Product> findByName(String name);

    List<Product> findByCategoryId(Long categoryId);

    List<Product> findByBrand(String brand);

    boolean existsByName(String name);
}
