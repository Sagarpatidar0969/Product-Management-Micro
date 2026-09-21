package com.sagar.product_service.service.impl;

import com.sagar.product_service.event.OrderCreatedEvent;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;

@Service
public class OrderEventConsumer {

    @KafkaListener(
            topics = "order-created",
            groupId = "product-service"
    )
    public void consumeOrderCreated(OrderCreatedEvent event) {

        try {

            System.out.println("===== ORDER EVENT RECEIVED =====");

            System.out.println("Order ID: " + event.getOrderId());
            System.out.println("User ID: " + event.getUserId());
            System.out.println("Product ID: " + event.getProductId());
            System.out.println("Quantity: " + event.getQuantity());

            System.out.println("================================");

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}