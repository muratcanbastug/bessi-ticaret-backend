package com.bessisebzemeyve.repository;

import com.bessisebzemeyve.entity.Order;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface OrderRepository extends JpaRepository<Order, Long> {
    List<Order> findAllByUserIdOrderByAmount(long userId);
}
