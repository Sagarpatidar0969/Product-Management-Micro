package com.sagar.order_service.service;

import com.sagar.order_service.dto.CreateOrderRequest;
import com.sagar.order_service.dto.OrderLogResponse;
import com.sagar.order_service.dto.OrderResponse;
import com.sagar.order_service.entity.Order;

import java.util.List;

public interface OrderService {

    Order createOrder(CreateOrderRequest request);
}
