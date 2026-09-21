package com.sagar.order_service.repository;

import com.sagar.order_service.entity.OrderLog;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface OrderLogRepository extends JpaRepository<OrderLog, Long> {

    List<OrderLog> findByUserIdOrderByTimestampDesc(Long userId);

    List<OrderLog> findByOrderId(Long orderId);
}
