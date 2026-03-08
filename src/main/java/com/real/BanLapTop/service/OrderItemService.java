package com.real.BanLapTop.service;

import java.util.List;
import com.real.BanLapTop.dto.request.OrderItem.OrderItemRequest;
import com.real.BanLapTop.dto.response.OrderItemResponse;

public interface OrderItemService {

    OrderItemResponse create(OrderItemRequest request);

    List<OrderItemResponse> getByOrder(Long orderId);

}
