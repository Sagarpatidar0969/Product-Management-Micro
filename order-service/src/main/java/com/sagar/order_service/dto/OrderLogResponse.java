package com.sagar.order_service.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class OrderLogResponse {

    private Long id;
    private Long userId;
    private Long productId;
    private Long orderId;
    private String action;
    private LocalDateTime timestamp;
}
