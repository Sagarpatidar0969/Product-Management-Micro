package com.sagar.order_service.service.impl;

import com.sagar.order_service.event.OrderCreatedEvent;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

@Service
public class KafkaProducerService {

    private static final String TOPIC = "order-created";

    private final KafkaTemplate<String, OrderCreatedEvent> kafkaTemplate;

    public KafkaProducerService(
            KafkaTemplate<String, OrderCreatedEvent> kafkaTemplate) {
        this.kafkaTemplate = kafkaTemplate;
    }

    public void sendOrderCreatedEvent(OrderCreatedEvent event) {

        kafkaTemplate.send(
                TOPIC,
                event.getOrderId().toString(),
                event
        ).whenComplete((result, exception) -> {

            if (exception != null) {
                System.out.println("❌ Kafka send failed: "
                        + exception.getMessage());
            } else {
                System.out.println("✅ Kafka message sent successfully");
                System.out.println("Topic: "
                        + result.getRecordMetadata().topic());
                System.out.println("Partition: "
                        + result.getRecordMetadata().partition());
                System.out.println("Offset: "
                        + result.getRecordMetadata().offset());
            }
        });
    }
}