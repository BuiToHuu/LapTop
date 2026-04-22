
// // // Test
// package com.real.BanLapTop.service.impl;

// import java.math.BigDecimal;
// import java.time.LocalDateTime;
// import java.util.List;

// import org.springframework.security.core.Authentication;
// import org.springframework.security.core.context.SecurityContextHolder;
// import org.springframework.stereotype.Service;

// import com.real.BanLapTop.dto.request.Order.OrderRequest;
// import com.real.BanLapTop.dto.request.OrderItem.OrderItemRequest;
// import com.real.BanLapTop.dto.response.OrderResponse;
// import com.real.BanLapTop.entity.CartItem;
// import com.real.BanLapTop.entity.Order;
// import com.real.BanLapTop.entity.OrderItem;
// import com.real.BanLapTop.entity.OrderStatus;
// import com.real.BanLapTop.entity.Product;
// import com.real.BanLapTop.entity.User;
// import com.real.BanLapTop.repository.CartItemRepository;
// import com.real.BanLapTop.repository.OrderItemRepository;
// import com.real.BanLapTop.repository.OrderRepository;
// import com.real.BanLapTop.repository.ProductRepository;
// import com.real.BanLapTop.repository.UserRepository;
// import com.real.BanLapTop.service.OrderService;

// @Service
// public class OrderImpl implements OrderService {

//     private final OrderRepository orderRepository;
//     private final OrderItemRepository orderItemRepository;
//     private final ProductRepository productRepository;
//     private final UserRepository userRepository;
//     private final CartItemRepository cartItemRepository;

//     public OrderImpl(
//             OrderRepository orderRepository,
//             OrderItemRepository orderItemRepository,
//             ProductRepository productRepository,
//             UserRepository userRepository,
//             CartItemRepository cartItemRepository) {

//         this.orderRepository = orderRepository;
//         this.orderItemRepository = orderItemRepository;
//         this.productRepository = productRepository;
//         this.userRepository = userRepository;
//         this.cartItemRepository = cartItemRepository;
//     }

//     // ================= MAP RESPONSE =================

//     private OrderResponse mapToResponse(Order order) {

//         OrderResponse res = new OrderResponse();

//         res.setId(order.getId());

//         if (order.getUser() != null) {
//             res.setUserId(order.getUser().getId());
//         }

//         res.setName(order.getName());
//         res.setEmail(order.getEmail());
//         res.setPhone(order.getPhone());
//         res.setShippingAddress(order.getShippingAddress());

//         res.setTotalPrice(order.getTotalPrice());
//         res.setStatus(order.getStatus().name());
//         res.setCreatedAt(order.getCreatedAt());

//         return res;
//     }

//     // ================= USER ORDER =================

//     @Override
//     public OrderResponse create(OrderRequest request) {

//         Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

//         if (authentication == null
//                 || !authentication.isAuthenticated()
//                 || authentication.getName().equals("anonymousUser")) {

//             throw new RuntimeException("User not authenticated");
//         }

//         String username = authentication.getName();

//         User user = userRepository.findByUsername(username)
//                 .orElseThrow(() -> new RuntimeException("User not found"));

//         List<CartItem> cartItems = cartItemRepository.findByUserId(user.getId());

//         if (cartItems == null || cartItems.isEmpty()) {
//             throw new RuntimeException("Cart is empty!");
//         }

//         Order order = new Order();

//         order.setUser(user);
//         order.setName(request.getName());
//         order.setEmail(user.getEmail());
//         order.setPhone(request.getPhone());
//         order.setShippingAddress(request.getShippingAddress());
//         order.setStatus(OrderStatus.PENDING);
//         order.setCreatedAt(LocalDateTime.now());

//         order = orderRepository.save(order);

//         BigDecimal total = BigDecimal.ZERO;

//         for (CartItem item : cartItems) {

//             Product product = item.getProduct();

//             BigDecimal itemTotal = product.getPrice()
//                     .multiply(BigDecimal.valueOf(item.getQuantity()));

//             OrderItem orderItem = new OrderItem();

//             orderItem.setOrder(order);
//             orderItem.setProductName(product.getName());
//             orderItem.setBrand(product.getBrand());
//             orderItem.setPrice(product.getPrice());
//             orderItem.setQuantity(item.getQuantity());
//             orderItem.setTotalPrice(itemTotal);

//             orderItemRepository.save(orderItem);

//             total = total.add(itemTotal);
//         }

//         order.setTotalPrice(total);
//         orderRepository.save(order);

//         // clear cart
//         cartItemRepository.deleteAll(cartItems);

//         return mapToResponse(order);
//     }

//     // ================= GUEST ORDER =================

//     @Override
//     public OrderResponse createGuestOrder(OrderRequest request) {

//         Order order = new Order();

//         order.setName(request.getName());
//         order.setEmail(request.getEmail());
//         order.setPhone(request.getPhone());
//         order.setShippingAddress(request.getShippingAddress());
//         order.setStatus(OrderStatus.PENDING);
//         order.setCreatedAt(LocalDateTime.now());

//         order = orderRepository.save(order);

//         BigDecimal total = BigDecimal.ZERO;

//         for (OrderItemRequest item : request.getItems()) {

//             Product product = productRepository.findById(item.getProductId())
//                     .orElseThrow(() -> new RuntimeException("Product not found"));

//             BigDecimal itemTotal = product.getPrice()
//                     .multiply(BigDecimal.valueOf(item.getQuantity()));

//             OrderItem orderItem = new OrderItem();

//             orderItem.setOrder(order);
//             orderItem.setProductName(product.getName());
//             orderItem.setBrand(product.getBrand());
//             orderItem.setPrice(product.getPrice());
//             orderItem.setQuantity(item.getQuantity());
//             orderItem.setTotalPrice(itemTotal);

//             orderItemRepository.save(orderItem);

//             total = total.add(itemTotal);
//         }

//         order.setTotalPrice(total);
//         orderRepository.save(order);

//         return mapToResponse(order);
//     }

//     // ================= USER ORDER HISTORY =================

//     @Override
//     public List<OrderResponse> getMyOrders() {

//         Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

//         String username = authentication.getName();

//         User user = userRepository.findByUsername(username)
//                 .orElseThrow(() -> new RuntimeException("User not found"));

//         List<Order> orders = orderRepository.findByUserId(user.getId());

//         return orders.stream()
//                 .map(this::mapToResponse)
//                 .toList();
//     }

//     ////////////// TEST
//     @Override
//     public List<OrderResponse> getAllOrders() {

//         List<Order> orders = orderRepository.findAll();

//         return orders.stream()
//                 .map(this::mapToResponse)
//                 .toList();
//     }

//     @Override
//     public OrderResponse updateStatus(Long orderId, String status) {

//         Order order = orderRepository.findById(orderId)
//                 .orElseThrow(() -> new RuntimeException("Order not found"));

//         order.setStatus(OrderStatus.valueOf(status));

//         orderRepository.save(order);

//         return mapToResponse(order);
//     }

//     @Override
//     public void deleteOrder(Long orderId) {

//         Order order = orderRepository.findById(orderId)
//                 .orElseThrow(() -> new RuntimeException("Order not found"));

//         orderRepository.delete(order);
//     }
// }

// Test 11
package com.real.BanLapTop.service.impl;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.real.BanLapTop.dto.request.Order.OrderRequest;
import com.real.BanLapTop.dto.request.OrderItem.OrderItemRequest;
import com.real.BanLapTop.dto.response.OrderResponse;
import com.real.BanLapTop.entity.CartItem;
import com.real.BanLapTop.entity.Order;
import com.real.BanLapTop.entity.OrderItem;
import com.real.BanLapTop.entity.OrderStatus;
import com.real.BanLapTop.entity.Product;
import com.real.BanLapTop.entity.User;
import com.real.BanLapTop.exception.BadRequestException;
import com.real.BanLapTop.repository.CartItemRepository;
import com.real.BanLapTop.repository.OrderItemRepository;
import com.real.BanLapTop.repository.OrderRepository;
import com.real.BanLapTop.repository.ProductRepository;
import com.real.BanLapTop.repository.UserRepository;
import com.real.BanLapTop.service.OrderService;

@Service
@Transactional
public class OrderImpl implements OrderService {

    private final OrderRepository orderRepository;
    private final OrderItemRepository orderItemRepository;
    private final ProductRepository productRepository;
    private final UserRepository userRepository;
    private final CartItemRepository cartItemRepository;

    public OrderImpl(
            OrderRepository orderRepository,
            OrderItemRepository orderItemRepository,
            ProductRepository productRepository,
            UserRepository userRepository,
            CartItemRepository cartItemRepository) {

        this.orderRepository = orderRepository;
        this.orderItemRepository = orderItemRepository;
        this.productRepository = productRepository;
        this.userRepository = userRepository;
        this.cartItemRepository = cartItemRepository;
    }

    // ================= MAPPING ORDER RESPONSE =================
    private OrderResponse mapToResponse(Order order) {
        OrderResponse res = new OrderResponse();
        res.setId(order.getId());
        if (order.getUser() != null) {
            res.setUserId(order.getUser().getId());
        }
        res.setName(order.getName());
        res.setEmail(order.getEmail());
        res.setPhone(order.getPhone());
        res.setShippingAddress(order.getShippingAddress());
        res.setTotalPrice(order.getTotalPrice());
        res.setStatus(order.getStatus().name());
        res.setCreatedAt(order.getCreatedAt());
        return res;
    }

    // ================= USER ORDER =================
    @Override
    public OrderResponse create(OrderRequest request) {

        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

        if (authentication == null
                || !authentication.isAuthenticated()
                || authentication.getName().equals("anonymousUser")) {
            throw new RuntimeException("User not authenticated");
        }

        String username = authentication.getName();

        User user = userRepository.findByUsername(username)
                .orElseThrow(() -> new RuntimeException("User not found"));

        List<CartItem> cartItems = cartItemRepository.findByUserId(user.getId());

        if (cartItems == null || cartItems.isEmpty()) {
            throw new RuntimeException("Cart is empty!");
        }

        Order order = new Order();
        order.setUser(user);
        order.setName(request.getName());
        order.setEmail(user.getEmail());
        order.setPhone(request.getPhone());
        order.setShippingAddress(request.getShippingAddress());
        order.setStatus(OrderStatus.PENDING);
        order.setCreatedAt(LocalDateTime.now());

        order = orderRepository.save(order);

        BigDecimal total = BigDecimal.ZERO;

        for (CartItem item : cartItems) {
            Product product = item.getProduct();

            // ===== CHECK TỒN KHO =====
            if (product.getStock() < item.getQuantity()) {
                throw new RuntimeException("Sản phẩm " + product.getName() + " không đủ trong kho");
            }

            // ===== TRỪ TỒN KHO =====
            product.setStock(product.getStock() - item.getQuantity());
            productRepository.save(product);

            BigDecimal itemTotal = product.getPrice()
                    .multiply(BigDecimal.valueOf(item.getQuantity()));

            OrderItem orderItem = new OrderItem();
            orderItem.setOrder(order);
            orderItem.setProductName(product.getName());
            orderItem.setBrand(product.getBrand());
            orderItem.setPrice(product.getPrice());
            orderItem.setQuantity(item.getQuantity());
            orderItem.setTotalPrice(itemTotal);

            orderItemRepository.save(orderItem);

            total = total.add(itemTotal);
        }

        order.setTotalPrice(total);
        orderRepository.save(order);

        // clear cart
        cartItemRepository.deleteAll(cartItems);

        return mapToResponse(order);
    }

    // ================= GUEST ORDER =================
    @Override
    public OrderResponse createGuestOrder(OrderRequest request) {

        Order order = new Order();
        order.setName(request.getName());
        order.setEmail(request.getEmail());
        order.setPhone(request.getPhone());
        order.setShippingAddress(request.getShippingAddress());
        order.setStatus(OrderStatus.PENDING);
        order.setCreatedAt(LocalDateTime.now());

        order = orderRepository.save(order);

        BigDecimal total = BigDecimal.ZERO;

        for (OrderItemRequest item : request.getItems()) {

            Product product = productRepository.findById(item.getProductId())
                    .orElseThrow(() -> new BadRequestException("Product not found"));

            // ===== CHECK TỒN KHO =====
            if (product.getStock() < item.getQuantity()) {
                throw new BadRequestException("Sản phẩm " + product.getName() + " không đủ trong kho");
            }

            // ===== TRỪ TỒN KHO =====
            product.setStock(product.getStock() - item.getQuantity());
            productRepository.save(product);

            BigDecimal itemTotal = product.getPrice()
                    .multiply(BigDecimal.valueOf(item.getQuantity()));

            OrderItem orderItem = new OrderItem();
            orderItem.setOrder(order);
            orderItem.setProductName(product.getName());
            orderItem.setBrand(product.getBrand());
            orderItem.setPrice(product.getPrice());
            orderItem.setQuantity(item.getQuantity());
            orderItem.setTotalPrice(itemTotal);

            orderItemRepository.save(orderItem);

            total = total.add(itemTotal);
        }

        order.setTotalPrice(total);
        orderRepository.save(order);

        return mapToResponse(order);
    }

    // ================= USER ORDER HISTORY =================
    @Override
    public List<OrderResponse> getMyOrders() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String username = authentication.getName();
        User user = userRepository.findByUsername(username)
                .orElseThrow(() -> new RuntimeException("User not found"));

        List<Order> orders = orderRepository.findByUserId(user.getId());
        return orders.stream().map(this::mapToResponse).toList();
    }

    // ================= ADMIN / TEST =================
    @Override
    public List<OrderResponse> getAllOrders() {
        List<Order> orders = orderRepository.findAll();
        return orders.stream().map(this::mapToResponse).toList();
    }

    @Override
    public OrderResponse updateStatus(Long orderId, String status) {
        Order order = orderRepository.findById(orderId)
                .orElseThrow(() -> new RuntimeException("Order not found"));

        order.setStatus(OrderStatus.valueOf(status));
        orderRepository.save(order);

        return mapToResponse(order);
    }

    @Override
    public OrderResponse cancelOrder(Long orderId) {
        Order order = orderRepository.findById(orderId)
                .orElseThrow(() -> new RuntimeException("Order not found"));

        // Không cho hủy nếu đã giao xong
        if (order.getStatus() == OrderStatus.COMPLETED) {
            throw new RuntimeException("Cannot cancel completed order");
        }

        order.setStatus(OrderStatus.CANCELLED);
        orderRepository.save(order);

        return mapToResponse(order);
    }
}