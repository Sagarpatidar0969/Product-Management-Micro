package com.sagar.order_service.event;

import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class OrderCreatedEvent {

    private Long orderId;
    private Long userId;
    private Long productId;
    private Integer quantity;
}