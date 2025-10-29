package com.example.Farm_MarketPlace.controller;

import com.example.Farm_MarketPlace.entity.Order;
import com.example.Farm_MarketPlace.entity.User;
import com.example.Farm_MarketPlace.service.OrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/orders")
public class OrderController {

    @Autowired
    private OrderService orderService;

    @PostMapping("/place")
    public Order placeOrder(@RequestBody Order order) {
        return orderService.placeOrder(order);
    }

    @GetMapping("/buyer/{buyerId}")
    public List<Order> getOrdersByBuyer(@PathVariable Long buyerId) {
        User buyer = new User();
        buyer.setId(buyerId);
        return orderService.getOrdersByBuyer(buyer);
    }
}
