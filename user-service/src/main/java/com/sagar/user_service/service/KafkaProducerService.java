package com.sagar.user_service.service;

import com.sagar.user_service.event.UserCreatedEvent;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

@Service
public class KafkaProducerService {

    private static final String TOPIC = "user-created";

    private final KafkaTemplate<String, UserCreatedEvent> kafkaTemplate;

    public KafkaProducerService(
            KafkaTemplate<String, UserCreatedEvent> kafkaTemplate) {

        this.kafkaTemplate = kafkaTemplate;
    }

    public void sendUserCreatedEvent(UserCreatedEvent event) {

        kafkaTemplate.send(
                TOPIC,
                event.getUserId().toString(),
                event
        );

        System.out.println(
                "Kafka event sent for user: " + event.getEmail()
        );
    }
}