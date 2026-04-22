// // package com.real.BanLapTop.service.impl;

// // import java.math.BigDecimal;
// // import java.time.LocalDateTime;
// // import java.util.List;

// // import org.springframework.stereotype.Service;

// // import com.real.BanLapTop.dto.request.OrderItem.OrderItemRequest;
// // import com.real.BanLapTop.dto.response.OrderItemResponse;
// // import com.real.BanLapTop.entity.Order;
// // import com.real.BanLapTop.entity.OrderItem;
// // import com.real.BanLapTop.entity.Product;
// // import com.real.BanLapTop.repository.OrderItemRepository;
// // import com.real.BanLapTop.repository.OrderRepository;
// // import com.real.BanLapTop.repository.ProductRepository;
// // import com.real.BanLapTop.service.OrderItemService;

// // @Service
// // public class OrderItemImpl implements OrderItemService {

// // private final OrderItemRepository orderItemRepository;
// // private final OrderRepository orderRepository;
// // private final ProductRepository productRepository;

// // public OrderItemImpl(
// // OrderItemRepository orderItemRepository,
// // OrderRepository orderRepository,
// // ProductRepository productRepository) {

// // this.orderItemRepository = orderItemRepository;
// // this.orderRepository = orderRepository;
// // this.productRepository = productRepository;
// // }

// // @Override
// // public OrderItemResponse create(OrderItemRequest request) {

// // // tìm order
// // Order order = orderRepository.findById(request.getOrderId())
// // .orElseThrow(() -> new RuntimeException("Order not found"));

// // // tìm product
// // Product product = productRepository.findById(request.getProductId())
// // .orElseThrow(() -> new RuntimeException("Product not found"));

// // OrderItem item = new OrderItem();

// // item.setOrder(order);
// // item.setProductName(product.getName());
// // item.setBrand(product.getBrand());

// // // lấy giá từ database (an toàn hơn)
// // item.setPrice(product.getPrice());

// // item.setQuantity(request.getQuantity());

// // BigDecimal total = product.getPrice()
// // .multiply(BigDecimal.valueOf(request.getQuantity()));

// // item.setTotalPrice(total);
// // item.setCreatedAt(LocalDateTime.now());

// // OrderItem saved = orderItemRepository.save(item);

// // return mapToResponse(saved);
// // }

// // @Override
// // public List<OrderItemResponse> getByOrder(Long orderId) {

// // return orderItemRepository.findByOrderId(orderId)
// // .stream()
// // .map(this::mapToResponse)
// // .toList();
// // }

// // private OrderItemResponse mapToResponse(OrderItem item) {

// // OrderItemResponse res = new OrderItemResponse();

// // res.setId(item.getId());
// // res.setProductName(item.getProductName());
// // res.setBrand(item.getBrand());
// // res.setPrice(item.getPrice());
// // res.setQuantity(item.getQuantity());
// // res.setTotalPrice(item.getTotalPrice());

// // return res;
// // }
// // }

// // Test
// package com.real.BanLapTop.service.impl;

// import java.math.BigDecimal;
// import java.time.LocalDateTime;
// import java.util.List;

// import org.springframework.stereotype.Service;

// import com.real.BanLapTop.dto.request.OrderItem.OrderItemRequest;
// import com.real.BanLapTop.dto.response.OrderItemResponse;
// import com.real.BanLapTop.entity.Order;
// import com.real.BanLapTop.entity.OrderItem;
// import com.real.BanLapTop.entity.Product;
// import com.real.BanLapTop.repository.OrderItemRepository;
// import com.real.BanLapTop.repository.OrderRepository;
// import com.real.BanLapTop.repository.ProductRepository;
// import com.real.BanLapTop.service.OrderItemService;

// @Service
// public class OrderItemImpl implements OrderItemService {

//     private final OrderItemRepository orderItemRepository;
//     private final OrderRepository orderRepository;
//     private final ProductRepository productRepository;

//     public OrderItemImpl(
//             OrderItemRepository orderItemRepository,
//             OrderRepository orderRepository,
//             ProductRepository productRepository) {

//         this.orderItemRepository = orderItemRepository;
//         this.orderRepository = orderRepository;
//         this.productRepository = productRepository;
//     }

//     // ============================
//     // CREATE ORDER ITEM
//     // ============================

//     @Override
//     public OrderItemResponse create(OrderItemRequest request) {

//         Order order = orderRepository.findById(request.getOrderId())
//                 .orElseThrow(() -> new RuntimeException("Order not found"));

//         Product product = productRepository.findById(request.getProductId())
//                 .orElseThrow(() -> new RuntimeException("Product not found"));

//         OrderItem item = new OrderItem();

//         item.setOrder(order);
//         item.setProductName(product.getName());
//         item.setBrand(product.getBrand());
//         item.setPrice(product.getPrice());
//         item.setQuantity(request.getQuantity());

//         BigDecimal itemTotal = product.getPrice()
//                 .multiply(BigDecimal.valueOf(request.getQuantity()));

//         item.setTotalPrice(itemTotal);
//         item.setCreatedAt(LocalDateTime.now());

//         OrderItem savedItem = orderItemRepository.save(item);

//         // cập nhật total price của order
//         BigDecimal orderTotal = order.getTotalPrice();

//         if (orderTotal == null) {
//             orderTotal = BigDecimal.ZERO;
//         }

//         orderTotal = orderTotal.add(itemTotal);

//         order.setTotalPrice(orderTotal);

//         orderRepository.save(order);

//         return mapToResponse(savedItem);
//     }

//     // ============================
//     // GET ITEMS BY ORDER
//     // ============================

//     @Override
//     public List<OrderItemResponse> getByOrder(Long orderId) {

//         List<OrderItem> items = orderItemRepository.findByOrderId(orderId);

//         return items.stream()
//                 .map(this::mapToResponse)
//                 .toList();
//     }

//     // ============================
//     // MAP ENTITY -> RESPONSE
//     // ============================

//     private OrderItemResponse mapToResponse(OrderItem item) {

//         OrderItemResponse response = new OrderItemResponse();

//         response.setId(item.getId());
//         response.setProductName(item.getProductName());
//         response.setBrand(item.getBrand());
//         response.setPrice(item.getPrice());
//         response.setQuantity(item.getQuantity());
//         response.setTotalPrice(item.getTotalPrice());

//         return response;
//     }
// }

// Test
package com.real.BanLapTop.service.impl;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

import org.springframework.stereotype.Service;

import com.real.BanLapTop.dto.request.OrderItem.OrderItemRequest;
import com.real.BanLapTop.dto.response.OrderItemResponse;
import com.real.BanLapTop.entity.Order;
import com.real.BanLapTop.entity.OrderItem;
import com.real.BanLapTop.entity.Product;
import com.real.BanLapTop.repository.OrderItemRepository;
import com.real.BanLapTop.repository.OrderRepository;
import com.real.BanLapTop.repository.ProductRepository;
import com.real.BanLapTop.service.OrderItemService;

@Service
public class OrderItemImpl implements OrderItemService {

    private final OrderItemRepository orderItemRepository;
    private final OrderRepository orderRepository;
    private final ProductRepository productRepository;

    public OrderItemImpl(
            OrderItemRepository orderItemRepository,
            OrderRepository orderRepository,
            ProductRepository productRepository) {

        this.orderItemRepository = orderItemRepository;
        this.orderRepository = orderRepository;
        this.productRepository = productRepository;
    }

    // ========================
    // CREATE ORDER ITEM
    // ========================

    @Override
    public OrderItemResponse create(Long orderId, OrderItemRequest request) {

        Order order = orderRepository.findById(orderId)
                .orElseThrow(() -> new RuntimeException("Order not found"));

        Product product = productRepository.findById(request.getProductId())
                .orElseThrow(() -> new RuntimeException("Product not found"));

        OrderItem item = new OrderItem();

        item.setOrder(order);
        item.setProductName(product.getName());
        item.setBrand(product.getBrand());
        item.setPrice(product.getPrice());
        item.setQuantity(request.getQuantity());

        BigDecimal total = product.getPrice()
                .multiply(BigDecimal.valueOf(request.getQuantity()));

        item.setTotalPrice(total);
        item.setCreatedAt(LocalDateTime.now());

        OrderItem saved = orderItemRepository.save(item);

        // cập nhật tổng tiền order
        BigDecimal orderTotal = order.getTotalPrice();

        if (orderTotal == null) {
            orderTotal = BigDecimal.ZERO;
        }

        orderTotal = orderTotal.add(total);

        order.setTotalPrice(orderTotal);

        orderRepository.save(order);

        return mapToResponse(saved);
    }

    // ========================
    // GET ITEMS BY ORDER
    // ========================

    @Override
    public List<OrderItemResponse> getByOrder(Long orderId) {

        return orderItemRepository.findByOrderId(orderId)
                .stream()
                .map(this::mapToResponse)
                .toList();
    }

    // ========================
    // MAP ENTITY -> DTO
    // ========================

    private OrderItemResponse mapToResponse(OrderItem item) {

        OrderItemResponse res = new OrderItemResponse();

        res.setId(item.getId());
        res.setProductName(item.getProductName());
        res.setBrand(item.getBrand());
        res.setPrice(item.getPrice());
        res.setQuantity(item.getQuantity());
        res.setTotalPrice(item.getTotalPrice());

        return res;
    }
}