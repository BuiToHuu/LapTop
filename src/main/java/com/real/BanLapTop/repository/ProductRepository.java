package com.real.BanLapTop.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.real.BanLapTop.entity.Product;

public interface ProductRepository extends JpaRepository<Product, Long> {
    List<Product> findByNameContainingIgnoreCase(String name);
    List<Product> findByCategoryId(Long categoryId);
    List<Product> findByBrandId(Long brandId);
    List<Product> findPriceBetween(Double minPrice, Double maxPrice);

}
