package com.real.BanLapTop.controller;

import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.real.BanLapTop.dto.request.CartItem.CartItemRequest;
import com.real.BanLapTop.dto.response.CartItemResponse;
import com.real.BanLapTop.service.CartService;

@RestController
@RequestMapping("/api/cart")
public class CartController {

    private final CartService cartService;

    public CartController(CartService cartService) {
        this.cartService = cartService;
    }

    @GetMapping
    public ResponseEntity<List<CartItemResponse>> getCart() {
        return ResponseEntity.ok(cartService.getUserCart());
    }

    @PostMapping("/add")
    public ResponseEntity<CartItemResponse> addToCart(
            @RequestBody CartItemRequest request) {

        return ResponseEntity.ok(cartService.addToCart(request));
    }

    @PutMapping("/update/{id}")
    public ResponseEntity<CartItemResponse> updateQuantity(
            @PathVariable Long id,
            @RequestBody CartItemRequest request) {

        return ResponseEntity.ok(
                cartService.updateQuantity(id, request.getQuantity()));
    }

    @DeleteMapping("/remove/{id}")
    public ResponseEntity<?> removeItem(@PathVariable Long id) {

        cartService.removeFromCart(id);

        return ResponseEntity.ok("Removed");
    }

    @DeleteMapping("/clear")
    public ResponseEntity<?> clearCart() {

        cartService.clearCart();

        return ResponseEntity.ok("Cart cleared");
    }
}