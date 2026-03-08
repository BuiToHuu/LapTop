package com.real.BanLapTop.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.real.BanLapTop.entity.Payment;

public interface PaymentRepository extends JpaRepository<Payment, Long> {

}