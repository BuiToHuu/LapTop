package com.real.BanLapTop.service;

import java.util.List;

import com.real.BanLapTop.dto.request.Order.OrderRequest;
import com.real.BanLapTop.dto.response.OrderResponse;

public interface OrderService {

    OrderResponse create(OrderRequest request);

    List<OrderResponse> getMyOrders();

}