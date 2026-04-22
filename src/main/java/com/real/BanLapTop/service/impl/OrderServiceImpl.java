package com.real.BanLapTop.service.impl;

import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.real.BanLapTop.dto.request.OrderRequest;
import com.real.BanLapTop.dto.request.GuestOrderRequest;
import com.real.BanLapTop.dto.request.GuestOrderItem;
import com.real.BanLapTop.dto.response.OrderResponse;
import com.real.BanLapTop.entity.Order;
import com.real.BanLapTop.entity.OrderStatus;
import com.real.BanLapTop.entity.User;
import com.real.BanLapTop.entity.Product;
import com.real.BanLapTop.repository.OrderRepository;
import com.real.BanLapTop.repository.UserRepository;
import com.real.BanLapTop.repository.ProductRepository;
import com.real.BanLapTop.repository.CartRepository;
import com.real.BanLapTop.service.CouponService;
import com.real.BanLapTop.service.OrderService;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Service
@Transactional
public class OrderServiceImpl implements OrderService {

    private final OrderRepository orderRepository;
    private final UserRepository userRepository;
    private final ProductRepository productRepository;
    private final CartRepository cartRepository;
    private final CouponService couponService;

    public OrderServiceImpl(OrderRepository orderRepository,
            UserRepository userRepository,
            ProductRepository productRepository,
            CartRepository cartRepository,
            CouponService couponService) {
        this.orderRepository = orderRepository;
        this.userRepository = userRepository;
        this.productRepository = productRepository;
        this.cartRepository = cartRepository;
        this.couponService = couponService;
    }

    /* ── CREATE ORDER FOR LOGGED-IN USER ── */
    @Override
    public OrderResponse createOrder(OrderRequest request) {

        // Lấy user từ SecurityContext (không hardcode)
        User user = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();

        // Tính tổng tiền từ cart của user
        List<com.real.BanLapTop.entity.CartItem> cartItems = cartRepository.findByUserId(user.getId());

        double totalPrice = cartItems.stream()
                .mapToDouble(i -> i.getPrice() * i.getQuantity())
                .sum();

        double shippingFee = totalPrice > 30000000 ? 0 : 30000;

        double discount = 0;
        if (request.getCouponCode() != null && !request.getCouponCode().isEmpty()) {
            discount = couponService.calculateDiscount(request.getCouponCode(), totalPrice);
        }

        double finalPrice = totalPrice + shippingFee - discount;

        // Tạo order
        Order order = new Order();
        order.setUser(user);
        order.setName(request.getName());
        order.setEmail(user.getEmail());
        order.setPhone(request.getPhone());
        order.setShippingAddress(request.getShippingAddress());
        order.setStatus(OrderStatus.PENDING);
        order.setCreatedAt(LocalDateTime.now());
        order.setTotalPrice(finalPrice);
        order.setCouponCode(request.getCouponCode());

        orderRepository.save(order);

        return mapToResponse(order);
    }

    /* ── CREATE ORDER FOR GUEST ── */
    @Override
    public OrderResponse createGuestOrder(GuestOrderRequest request) {

        double totalPrice = 0;
        for (GuestOrderItem item : request.getItems()) {
            Product p = productRepository.findById(item.getProductId())
                    .orElseThrow(() -> new RuntimeException("Sản phẩm không tồn tại: " + item.getProductId()));
            totalPrice += p.getPrice() * item.getQuantity();
        }

        double shippingFee = totalPrice > 30000000 ? 0 : 30000;

        double discount = 0;
        if (request.getCouponCode() != null && !request.getCouponCode().isEmpty()) {
            discount = couponService.calculateDiscount(request.getCouponCode(), totalPrice);
        }

        double finalPrice = totalPrice + shippingFee - discount;

        // Tạo order
        Order order = new Order();
        order.setName(request.getName());
        order.setEmail(request.getEmail());
        order.setPhone(request.getPhone());
        order.setShippingAddress(request.getShippingAddress());
        order.setStatus(OrderStatus.PENDING);
        order.setCreatedAt(LocalDateTime.now());
        order.setTotalPrice(finalPrice);
        order.setCouponCode(request.getCouponCode());

        orderRepository.save(order);

        return mapToResponse(order);
    }

    /* ── GET ORDERS ── */
    @Override
    public List<OrderResponse> getMyOrders() {
        User user = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        return orderRepository.findByUserId(user.getId())
                .stream()
                .map(this::mapToResponse)
                .collect(Collectors.toList());
    }

    @Override
    public List<OrderResponse> getAllOrders() {
        return orderRepository.findAll()
                .stream()
                .map(this::mapToResponse)
                .collect(Collectors.toList());
    }

    /* ── UPDATE / CANCEL ── */
    @Override
    public OrderResponse updateOrderStatus(Long id, String status) {
        Order order = orderRepository.findById(id).orElseThrow();
        order.setStatus(OrderStatus.valueOf(status.toUpperCase()));
        return mapToResponse(order);
    }

    @Override
    public OrderResponse cancelOrder(Long id) {
        Order order = orderRepository.findById(id).orElseThrow();
        order.setStatus(OrderStatus.CANCELLED);
        return mapToResponse(order);
    }

    /* ── MAPPER ── */
    private OrderResponse mapToResponse(Order order) {
        return new OrderResponse(
                order.getId(),
                order.getName(),
                order.getEmail(),
                order.getPhone(),
                order.getShippingAddress(),
                order.getTotalPrice(),
                order.getStatus(),
                order.getCreatedAt());
    }
}