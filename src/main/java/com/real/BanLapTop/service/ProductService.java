package com.real.BanLapTop.service;

import java.util.List;

import org.springframework.stereotype.Service;

import com.real.BanLapTop.dto.request.Product.ProductRequest;
import com.real.BanLapTop.dto.response.ProductResponse;
import com.real.BanLapTop.entity.Product;

@Service
public interface ProductService {
    List<ProductResponse> getAllProduct();

    ProductResponse createProduct(ProductRequest request);

    ProductResponse updateProduct(Long id, ProductRequest request);

    void deleteProduct(Long id);

    List<ProductResponse> getProductByCategory(Long categoryId);

    ProductResponse getProductById(Long id);

}
