package com.sagar.order_service.controller;

import com.sagar.order_service.dto.CreateOrderRequest;
import com.sagar.order_service.dto.OrderLogResponse;
import com.sagar.order_service.dto.OrderResponse;
import com.sagar.order_service.entity.Order;
import com.sagar.order_service.service.OrderService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/orders")
@RequiredArgsConstructor
public class OrderController {

    private final OrderService orderService;

    @PostMapping
    public ResponseEntity<Order> createOrder(
            @RequestBody CreateOrderRequest request) {

        Order order = orderService.createOrder(request);

        return ResponseEntity.ok(order);
    }
}

