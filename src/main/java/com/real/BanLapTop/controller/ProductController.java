package com.real.BanLapTop.controller;

import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.real.BanLapTop.dto.request.Product.ProductRequest;
import com.real.BanLapTop.dto.response.ProductResponse;
import com.real.BanLapTop.service.ProductService;

@RestController
@RequestMapping("/api/products")
@CrossOrigin("*")
public class ProductController {

    private final ProductService productService;

    public ProductController(ProductService productService) {
        this.productService = productService;
    }

    // =============================
    // Lấy tất cả sản phẩm
    // =============================
    @GetMapping
    public ResponseEntity<List<ProductResponse>> getAllProducts() {
        return ResponseEntity.ok(productService.getAllProduct());
    }

    // =============================
    // Lấy sản phẩm theo ID
    // =============================
    @GetMapping("/{id}")
    public ResponseEntity<ProductResponse> getProductById(@PathVariable Long id) {
        return ResponseEntity.ok(productService.getProductById(id));
    }

    // =============================
    // Lấy sản phẩm theo category
    // =============================
    @GetMapping("/category/{categoryId}")
    public ResponseEntity<List<ProductResponse>> getProductByCategory(
            @PathVariable Long categoryId) {

        return ResponseEntity.ok(productService.getProductByCategory(categoryId));
    }

    // =============================
    // Tạo sản phẩm
    // =============================
    @PostMapping("/create")
    public ResponseEntity<ProductResponse> createProduct(
            @RequestBody ProductRequest request) {

        return ResponseEntity.ok(productService.createProduct(request));
    }

    // =============================
    // Update sản phẩm
    // =============================
    @PutMapping("/{id}")
    public ResponseEntity<ProductResponse> updateProduct(
            @PathVariable Long id,
            @RequestBody ProductRequest request) {

        return ResponseEntity.ok(productService.updateProduct(id, request));
    }

    // =============================
    // Xóa sản phẩm
    // =============================
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteProduct(@PathVariable Long id) {

        productService.deleteProduct(id);

        return ResponseEntity.noContent().build();
    }
}