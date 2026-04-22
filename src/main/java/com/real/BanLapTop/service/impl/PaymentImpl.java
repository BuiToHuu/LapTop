// package com.real.BanLapTop.service.impl;

// import java.time.LocalDateTime;
// import java.util.UUID;

// import org.springframework.stereotype.Service;

// import com.real.BanLapTop.dto.request.Payment.PaymentRequest;
// import com.real.BanLapTop.dto.response.PaymentResponse;
// import com.real.BanLapTop.entity.Order;
// import com.real.BanLapTop.entity.Payment;
// import com.real.BanLapTop.repository.OrderRepository;
// import com.real.BanLapTop.repository.PaymentRepository;
// import com.real.BanLapTop.service.PaymentService;

// @Service
// public class PaymentImpl implements PaymentService {

//     private final PaymentRepository paymentRepository;
//     private final OrderRepository orderRepository;

//     public PaymentImpl(PaymentRepository paymentRepository,
//             OrderRepository orderRepository) {

//         this.paymentRepository = paymentRepository;
//         this.orderRepository = orderRepository;
//     }

//     @Override
//     public PaymentResponse create(PaymentRequest request) {

//         Order order = orderRepository.findById(request.getOrderId())
//                 .orElseThrow(() -> new RuntimeException("Order not found"));

//         Payment payment = new Payment();

//         payment.setOrder(order);
//         payment.setPaymentMethod(request.getPaymentMethod());
//         payment.setAmount(request.getAmount());

//         payment.setStatus("PENDING");
//         payment.setTransactionCode(UUID.randomUUID().toString());

//         payment.setPaidAt(LocalDateTime.now());

//         Payment saved = paymentRepository.save(payment);

//         // Fake MoMo redirect
//         String payUrl = "http://localhost:3000/momo-payment?paymentId=" + saved.getId();

//         PaymentResponse res = new PaymentResponse();

//         res.setId(saved.getId());
//         res.setOrderId(order.getId());
//         res.setAmount(saved.getAmount());
//         res.setStatus(saved.getStatus());
//         res.setPaymentMethod(saved.getPaymentMethod());
//         res.setPayUrl(payUrl);
//         return res;
//     }
// }

// //TEST
// package com.real.BanLapTop.service.impl;

// import java.time.LocalDateTime;
// import java.util.UUID;

// import org.springframework.stereotype.Service;

// import com.real.BanLapTop.dto.request.Payment.PaymentRequest;
// import com.real.BanLapTop.dto.response.PaymentResponse;
// import com.real.BanLapTop.entity.Order;
// import com.real.BanLapTop.entity.OrderStatus;
// import com.real.BanLapTop.entity.Payment;
// import com.real.BanLapTop.repository.OrderRepository;
// import com.real.BanLapTop.repository.PaymentRepository;
// import com.real.BanLapTop.service.EmailService;
// import com.real.BanLapTop.service.PaymentService;

// @Service
// public class PaymentImpl implements PaymentService {

//     private final PaymentRepository paymentRepository;
//     private final OrderRepository orderRepository;
//     private final EmailService emailService;

//     public PaymentImpl(
//             PaymentRepository paymentRepository,
//             OrderRepository orderRepository,
//             EmailService emailService) {

//         this.paymentRepository = paymentRepository;
//         this.orderRepository = orderRepository;
//         this.emailService = emailService;
//     }

//     @Override
//     public PaymentResponse create(PaymentRequest request) {

//         Order order = orderRepository.findById(request.getOrderId())
//                 .orElseThrow(() -> new RuntimeException("Order not found"));

//         Payment payment = new Payment();

//         payment.setOrder(order);
//         payment.setPaymentMethod(request.getPaymentMethod());
//         payment.setAmount(request.getAmount());
//         payment.setTransactionCode(UUID.randomUUID().toString());
//         payment.setPaidAt(LocalDateTime.now());

//         // ================= COD =================

//         if ("COD".equals(request.getPaymentMethod())) {

//             payment.setStatus("SUCCESS");

//             order.setStatus(OrderStatus.CONFIRMED);

//             orderRepository.save(order);

//             sendOrderEmail(order);
//         }

//         // ================= ONLINE =================

//         else {

//             payment.setStatus("PENDING");

//         }

//         Payment saved = paymentRepository.save(payment);

//         // Fake redirect (MoMo)
//         String payUrl = "http://localhost:3000/momo-payment?paymentId=" + saved.getId();

//         PaymentResponse res = new PaymentResponse();

//         res.setId(saved.getId());
//         res.setOrderId(order.getId());
//         res.setAmount(saved.getAmount());
//         res.setStatus(saved.getStatus());
//         res.setPaymentMethod(saved.getPaymentMethod());
//         res.setPayUrl(payUrl);

//         return res;
//     }

//     // ================= PAYMENT SUCCESS =================

//     public void paymentSuccess(Long paymentId) {

//         Payment payment = paymentRepository.findById(paymentId)
//                 .orElseThrow(() -> new RuntimeException("Payment not found"));

//         payment.setStatus("SUCCESS");

//         Order order = payment.getOrder();

//         order.setStatus(OrderStatus.PAID);

//         orderRepository.save(order);

//         paymentRepository.save(payment);

//         sendOrderEmail(order);
//     }

//     // ================= SEND EMAIL =================

//     private void sendOrderEmail(Order order) {

//         if (order.getEmail() == null || order.getEmail().isEmpty()) {
//             return;
//         }

//         emailService.sendEmail(
//                 order.getEmail(),
//                 "Đơn hàng #" + order.getId(),
//                 "Cảm ơn bạn đã đặt hàng tại BanLaptop. Đơn hàng của bạn đã được xác nhận.");

//         // SENT ADMIN
//         emailService.sendEmail(
//                 "buitohuu123@gmail.com",
//                 "Đơn hàng mới #" + order.getId(),
//                 "Có đơn hàng mới từ: " + order.getName()
//                         + "\nSĐT: " + order.getPhone()
//                         + "\nĐịa chỉ: " + order.getShippingAddress()
//                         + "\nTổng tiền: " + order.getTotalPrice());
//     }

// }

// Test 2
package com.real.BanLapTop.service.impl;

import java.time.LocalDateTime;
import java.util.UUID;

import org.springframework.stereotype.Service;

import com.real.BanLapTop.dto.request.Payment.PaymentRequest;
import com.real.BanLapTop.dto.response.PaymentResponse;
import com.real.BanLapTop.entity.Order;
import com.real.BanLapTop.entity.OrderStatus;
import com.real.BanLapTop.entity.Payment;
import com.real.BanLapTop.entity.PaymentStatus;
import com.real.BanLapTop.repository.OrderRepository;
import com.real.BanLapTop.repository.PaymentRepository;
import com.real.BanLapTop.service.EmailService;
import com.real.BanLapTop.service.PaymentService;

@Service
public class PaymentImpl implements PaymentService {

    private final PaymentRepository paymentRepository;
    private final OrderRepository orderRepository;
    private final EmailService emailService;

    public PaymentImpl(
            PaymentRepository paymentRepository,
            OrderRepository orderRepository,
            EmailService emailService) {

        this.paymentRepository = paymentRepository;
        this.orderRepository = orderRepository;
        this.emailService = emailService;
    }

    // ================= CREATE PAYMENT =================

    @Override
    public PaymentResponse create(PaymentRequest request) {

        Order order = orderRepository.findById(request.getOrderId())
                .orElseThrow(() -> new RuntimeException("Order not found"));

        Payment payment = new Payment();

        payment.setOrder(order);
        payment.setPaymentMethod(request.getPaymentMethod());
        payment.setAmount(request.getAmount());
        payment.setTransactionCode(UUID.randomUUID().toString());
        payment.setPaidAt(LocalDateTime.now());

        // ================= COD =================

        if ("COD".equals(request.getPaymentMethod())) {

            payment.setStatus(PaymentStatus.SUCCESS);

            order.setStatus(OrderStatus.CONFIRMED);
            orderRepository.save(order);

            sendOrderEmail(order);

        } else {

            // ================= ONLINE =================

            payment.setStatus(PaymentStatus.PENDING);

        }

        Payment saved = paymentRepository.save(payment);

        // Fake redirect payment gateway
        String payUrl = "http://localhost:3000/momo-payment?paymentId=" + saved.getId();

        PaymentResponse res = new PaymentResponse();

        res.setId(saved.getId());
        res.setOrderId(order.getId());
        res.setAmount(saved.getAmount());
        res.setStatus(saved.getStatus());
        res.setPaymentMethod(saved.getPaymentMethod());
        res.setPayUrl(payUrl);

        return res;
    }

    // ================= PAYMENT SUCCESS (ONLINE CALLBACK) =================

    @Override
    public void paymentSuccess(Long paymentId) {

        Payment payment = paymentRepository.findById(paymentId)
                .orElseThrow(() -> new RuntimeException("Payment not found"));

        payment.setStatus(PaymentStatus.SUCCESS);

        Order order = payment.getOrder();

        order.setStatus(OrderStatus.PAID);

        orderRepository.save(order);
        paymentRepository.save(payment);

        sendOrderEmail(order);
    }

    // ================= PAYMENT FAIL =================

    @Override
    public void paymentFail(Long paymentId) {

        Payment payment = paymentRepository.findById(paymentId)
                .orElseThrow(() -> new RuntimeException("Payment not found"));

        payment.setStatus(PaymentStatus.FAILED);

        Order order = payment.getOrder();
        order.setStatus(OrderStatus.CANCELLED);

        orderRepository.save(order);
        paymentRepository.save(payment);
    }

    // ================= SEND EMAIL =================

    private void sendOrderEmail(Order order) {

        try {

            if (order.getEmail() != null && !order.getEmail().isEmpty()) {

                emailService.sendEmail(
                        order.getEmail(),
                        "Đơn hàng #" + order.getId(),
                        "Cảm ơn bạn đã đặt hàng tại BanLaptop.\n"
                                + "Mã đơn: #" + order.getId()
                                + "\nTổng tiền: " + order.getTotalPrice());
            }
        } catch (Exception e) {

            System.out.println("Email error: " + e.getMessage());

        }
        emailService.sendEmail(
                "buitohuu123@gmail.com",
                "Đơn hàng mới #" + order.getId(),
                "Có đơn hàng mới\n"
                        + "Tên: " + order.getName()
                        + "\nSĐT: " + order.getPhone()
                        + "\nĐịa chỉ: " + order.getShippingAddress()
                        + "\nTổng tiền: " + order.getTotalPrice());

    }

}