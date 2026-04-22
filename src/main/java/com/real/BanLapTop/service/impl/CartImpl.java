package com.real.BanLapTop.service.impl;

import java.util.List;

import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import com.real.BanLapTop.dto.request.CartItem.CartItemRequest;
import com.real.BanLapTop.dto.response.CartItemResponse;
import com.real.BanLapTop.entity.CartItem;
import com.real.BanLapTop.entity.Product;
import com.real.BanLapTop.entity.User;
import com.real.BanLapTop.repository.CartItemRepository;
import com.real.BanLapTop.repository.ProductRepository;
import com.real.BanLapTop.repository.UserRepository;
import com.real.BanLapTop.service.CartService;

@Service
public class CartImpl implements CartService {

    private final CartItemRepository cartItemRepository;
    private final ProductRepository productRepository;
    private final UserRepository userRepository;

    public CartImpl(
            CartItemRepository cartItemRepository,
            ProductRepository productRepository,
            UserRepository userRepository) {

        this.cartItemRepository = cartItemRepository;
        this.productRepository = productRepository;
        this.userRepository = userRepository;
    }

    private User getCurrentUser() {

        var auth = SecurityContextHolder
                .getContext()
                .getAuthentication();

        if (auth == null || auth.getName().equals("anonymousUser")) {
            throw new RuntimeException("User not authenticated");
        }

        return userRepository
                .findByUsername(auth.getName())
                .orElseThrow(() -> new RuntimeException("User not found"));
    }

    @Override
    public CartItemResponse addToCart(CartItemRequest request) {

        User user = getCurrentUser();

        Product product = productRepository
                .findById(request.getProductId())
                .orElseThrow(() -> new RuntimeException("Product not found"));

        CartItem cartItem = cartItemRepository
                .findByUserIdAndProductId(user.getId(), product.getId())
                .orElse(null);

        if (cartItem != null) {

            cartItem.setQuantity(
                    cartItem.getQuantity() + request.getQuantity());

        } else {

            cartItem = new CartItem();
            cartItem.setUser(user);
            cartItem.setProduct(product);
            cartItem.setQuantity(request.getQuantity());
        }

        CartItem saved = cartItemRepository.save(cartItem);

        return mapToResponse(saved);
    }

    @Override
    public List<CartItemResponse> getUserCart() {

        User user = getCurrentUser();

        return cartItemRepository
                .findByUserId(user.getId())
                .stream()
                .map(this::mapToResponse)
                .toList();
    }

    @Override
    public CartItemResponse updateQuantity(Long cartItemId, Integer quantity) {

        CartItem item = cartItemRepository
                .findById(cartItemId)
                .orElseThrow(() -> new RuntimeException("Cart item not found"));

        item.setQuantity(quantity);

        CartItem saved = cartItemRepository.save(item);

        return mapToResponse(saved);
    }

    @Override
    public void removeFromCart(Long cartItemId) {

        cartItemRepository.deleteById(cartItemId);
    }

    @Override
    public void clearCart() {

        User user = getCurrentUser();

        cartItemRepository.deleteByUserId(user.getId());
    }

    private CartItemResponse mapToResponse(CartItem item) {

        CartItemResponse res = new CartItemResponse();

        res.setId(item.getId());

        if (item.getProduct() != null) {

            res.setProductId(item.getProduct().getId());
            res.setProductName(item.getProduct().getName());
            res.setBrand(item.getProduct().getBrand());

            double price = item.getProduct().getPrice() != null
                    ? item.getProduct().getPrice().doubleValue()
                    : 0;

            res.setPrice(price);
            res.setQuantity(item.getQuantity());
            res.setTotalPrice(price * item.getQuantity());
        }

        return res;
    }
}