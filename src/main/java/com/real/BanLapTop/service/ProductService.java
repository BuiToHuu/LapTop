package com.real.BanLapTop.service;

import java.util.List;

import org.springframework.stereotype.Service;

import com.real.BanLapTop.entity.Product;

@Service
public interface ProductService {
    List<Product> getAllProducts();
    Product getProductById(Long id);
    List<Product> searchProducts(String name);
    List<Product> getProductsByCategory(Long categoryId);
    List<Product> getProductsByBrand(Long brandId);
    List<Product> getProductsByPriceRange(Double minPrice, Double maxPrice);
}
