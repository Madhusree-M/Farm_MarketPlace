package com.example.Farm_MarketPlace.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import com.example.Farm_MarketPlace.entity.Order;
import com.example.Farm_MarketPlace.entity.User;
import java.util.List;

public interface OrderRepository extends JpaRepository<Order, Long> {
    List<Order> findByBuyer(User buyer);
}
