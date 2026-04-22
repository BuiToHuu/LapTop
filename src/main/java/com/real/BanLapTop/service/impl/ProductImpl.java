package com.real.BanLapTop.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.real.BanLapTop.dto.request.Product.ProductRequest;
import com.real.BanLapTop.dto.response.ProductResponse;
import com.real.BanLapTop.entity.Category;
import com.real.BanLapTop.entity.Product;
import com.real.BanLapTop.exception.BadRequestException;
import com.real.BanLapTop.repository.CategoryRepository;
import com.real.BanLapTop.repository.ProductRepository;
import com.real.BanLapTop.service.ProductService;

// @Service
// public class ProductImpl implements ProductService {
//     private final ProductRepository productRepository;
//     private final CategoryRepository categoryRepository;

//     public ProductImpl(ProductRepository productRepository,
//             CategoryRepository categoryRepository) {
//         this.productRepository = productRepository;
//         this.categoryRepository = categoryRepository;
//     }

//     // =============================
//     // Lấy tất cả sản phẩm
//     // =============================
//     @Override
//     public List<ProductResponse> getAllProduct() {
//         return productRepository.findAll()
//                 .stream()
//                 .map(this::mapToResponse)
//                 .toList();
//     }

//     // =============================
//     // Lấy sản phẩm theo ID
//     // =============================
//     @Override
//     public ProductResponse getProductById(Long id) {

//         Product product = productRepository.findById(id)
//                 .orElseThrow(() -> new RuntimeException("Product not found"));

//         return mapToResponse(product);
//     }

//     // =============================
//     // Lấy sản phẩm theo category
//     // =============================
//     @Override
//     public List<ProductResponse> getProductByCategory(Long categoryId) {

//         List<Product> products = productRepository.findByCategoryId(categoryId);

//         return products.stream()
//                 .map(this::mapToResponse)
//                 .toList();
//     }

//     @Override
//     public ProductResponse createProduct(ProductRequest request) {

//         Category category = categoryRepository
//                 .findByNameIgnoreCase(request.getCategoryName())
//                 .orElseThrow(() -> new BadRequestException("Category not found"));

//         if (productRepository.existsByName(request.getName())) {
//             throw new BadRequestException("Product name already exists");
//         }

//         Product product = new Product();
//         product.setName(request.getName());
//         product.setDescription(request.getDescription());
//         product.setPrice(request.getPrice());
//         product.setStock(request.getStock());
//         product.setBrand(request.getBrand());
//         product.setCategory(category); // dùng category tìm theo tên
//         product.setImageUrl(request.getImageUrl());

//         Product savedProduct = productRepository.save(product);
//         return mapToResponse(savedProduct);
//     }

//     // =============================
//     // Update sản phẩm
//     // =============================
//     @Override
//     public ProductResponse updateProduct(Long id, ProductRequest request) {

//         Product product = productRepository.findById(id)
//                 .orElseThrow(() -> new RuntimeException("Product not found"));

//         Category category = categoryRepository
//                 .findById(request.getCategoryId())
//                 .orElseThrow(() -> new RuntimeException("Category not found"));

//         product.setName(request.getName());
//         product.setDescription(request.getDescription());
//         product.setPrice(request.getPrice());
//         product.setStock(request.getStock());
//         product.setBrand(request.getBrand());
//         product.setCategory(category);
//         product.setImageUrl(request.getImageUrl());

//         Product updatedProduct = productRepository.save(product);

//         return mapToResponse(updatedProduct);
//     }

//     // =============================
//     // Xóa sản phẩm
//     // =============================
//     @Override
//     public void deleteProduct(Long id) {

//         if (!productRepository.existsById(id)) {
//             throw new RuntimeException("Product not found");
//         }

//         productRepository.deleteById(id);
//     }

//     // Search Sản Phẩm
//     @Override
//     public List<ProductResponse> searchProduct(String keyword) {

//         List<Product> products = productRepository.findByNameContainingIgnoreCaseOrBrandContainingIgnoreCase(
//                 keyword, keyword);

//         return products.stream()
//                 .map(this::mapToResponse)
//                 .toList();
//     }

//     // =============================
//     // Convert entity -> response
//     // =============================
//     private ProductResponse mapToResponse(Product product) {

//         ProductResponse response = new ProductResponse();

//         response.setId(product.getId());
//         response.setName(product.getName());
//         response.setDescription(product.getDescription());
//         response.setPrice(product.getPrice());
//         response.setStock(product.getStock());
//         response.setBrand(product.getBrand());
//         response.setCategoryId(product.getCategory().getId());
//         response.setCreatedAt(product.getCreatedAt());
//         response.setImageUrl(product.getImageUrl());

//         return response;
//     }
// }

// Test
@Service
public class ProductImpl implements ProductService {
    private final ProductRepository productRepository;
    private final CategoryRepository categoryRepository;

    public ProductImpl(ProductRepository productRepository,
            CategoryRepository categoryRepository) {
        this.productRepository = productRepository;
        this.categoryRepository = categoryRepository;
    }

    @Override
    public List<ProductResponse> getAllProduct() {
        return productRepository.findAll()
                .stream()
                .map(this::mapToResponse)
                .toList();
    }

    @Override
    public ProductResponse getProductById(Long id) {
        Product product = productRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Product not found"));
        return mapToResponse(product);
    }

    @Override
    public List<ProductResponse> getProductByCategory(Long categoryId) {
        List<Product> products = productRepository.findByCategoryId(categoryId);
        return products.stream().map(this::mapToResponse).toList();
    }

    @Override
    public ProductResponse createProduct(ProductRequest request) {
        Category category = categoryRepository
                .findByNameIgnoreCase(request.getCategoryName())
                .orElseThrow(() -> new BadRequestException("Category not found"));

        if (productRepository.existsByName(request.getName())) {
            throw new BadRequestException("Product name already exists");
        }

        Product product = new Product();
        product.setName(request.getName());
        product.setDescription(request.getDescription());
        product.setPrice(request.getPrice());
        product.setStock(request.getStock());
        product.setBrand(request.getBrand());
        product.setCategory(category);
        product.setImageUrl(request.getImageUrl());

        Product savedProduct = productRepository.save(product);
        return mapToResponse(savedProduct);
    }

    @Override
    public ProductResponse updateProduct(Long id, ProductRequest request) {
        Product product = productRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Product not found"));

        // Update: dùng categoryName thay vì categoryId
        Category category = categoryRepository
                .findByNameIgnoreCase(request.getCategoryName())
                .orElseThrow(() -> new RuntimeException("Category not found"));

        product.setName(request.getName());
        product.setDescription(request.getDescription());
        product.setPrice(request.getPrice());
        product.setStock(request.getStock());
        product.setBrand(request.getBrand());
        product.setCategory(category);
        product.setImageUrl(request.getImageUrl());

        Product updatedProduct = productRepository.save(product);
        return mapToResponse(updatedProduct);
    }

    @Override
    public void deleteProduct(Long id) {
        if (!productRepository.existsById(id)) {
            throw new RuntimeException("Product not found");
        }
        productRepository.deleteById(id);
    }

    @Override
    public List<ProductResponse> searchProduct(String keyword) {
        List<Product> products = productRepository.findByNameContainingIgnoreCaseOrBrandContainingIgnoreCase(
                keyword, keyword);
        return products.stream().map(this::mapToResponse).toList();
    }

    // map entity -> response, trả categoryName luôn
    private ProductResponse mapToResponse(Product product) {
        ProductResponse response = new ProductResponse();
        response.setId(product.getId());
        response.setName(product.getName());
        response.setDescription(product.getDescription());
        response.setPrice(product.getPrice());
        response.setStock(product.getStock());
        response.setBrand(product.getBrand());
        response.setCategoryId(product.getCategory().getId());
        response.setCategoryName(product.getCategory().getName()); // thêm tên
        response.setCreatedAt(product.getCreatedAt());
        response.setImageUrl(product.getImageUrl());
        return response;
    }
}