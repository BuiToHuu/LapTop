package com.real.BanLapTop.service;

import java.util.List;

import com.real.BanLapTop.dto.request.CartItem.CartItemRequest;
import com.real.BanLapTop.dto.response.CartItemResponse;

public interface CartService {

    CartItemResponse addToCart(CartItemRequest request);

    List<CartItemResponse> getUserCart();

    CartItemResponse updateQuantity(Long cartItemId, Integer quantity);

    void removeFromCart(Long cartItemId);

    void clearCart();
}