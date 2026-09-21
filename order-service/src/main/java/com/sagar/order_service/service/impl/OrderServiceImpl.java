package com.sagar.order_service.service.impl;


import com.sagar.order_service.client.ProductClient;
import com.sagar.order_service.dto.CreateOrderRequest;
import com.sagar.order_service.dto.ProductResponse;
import com.sagar.order_service.entity.Order;
import com.sagar.order_service.event.OrderCreatedEvent;
import com.sagar.order_service.repository.OrderRepository;
import com.sagar.order_service.service.OrderService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.List;

@Service
@RequiredArgsConstructor
public class OrderServiceImpl implements OrderService {
    private final OrderRepository orderRepository;
    private final ProductClient productClient;
    private final KafkaProducerService kafkaProducerService;


    @Override
    public Order createOrder(CreateOrderRequest request) {

        // 1. Get product details
        ProductResponse product = productClient.getProduct(request.getProductId());

        // 2. Calculate total
        BigDecimal total = product.getPrice().multiply(
                                BigDecimal.valueOf(request.getQuantity()));

        // 3. Create order
        Order order = new Order();

        order.setUserId(request.getUserId());
        order.setProductId(request.getProductId());
        order.setQuantity(request.getQuantity());
        order.setTotalPrice(product.getPrice());
        order.setTotalAmount(total.doubleValue());
        order.setStatus("CREATED");

        // 4. Save order
        Order savedOrder = orderRepository.save(order);

        // 5. Create Kafka event
        OrderCreatedEvent event = OrderCreatedEvent.builder()
                .orderId(savedOrder.getId())
                .userId(savedOrder.getUserId())
                .productId(savedOrder.getProductId())
                .quantity(savedOrder.getQuantity())
                .build();

        // 6. Send event to Kafka
        kafkaProducerService.sendOrderCreatedEvent(event);

        return savedOrder;
    }
}