package com.example.Farm_MarketPlace.service;

import com.example.Farm_MarketPlace.entity.Order;
// import com.example.Farm_MarketPlace.entity.Product;
import com.example.Farm_MarketPlace.entity.User;
import com.example.Farm_MarketPlace.repository.OrderRepository;
import com.example.Farm_MarketPlace.repository.InventoryRepository;
import com.example.Farm_MarketPlace.entity.Inventory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class OrderService {

    @Autowired
    private OrderRepository orderRepository;

    @Autowired
    private InventoryRepository inventoryRepository;

    public Order placeOrder(Order order) {
        // Check stock before placing order
        Inventory inventory = inventoryRepository.findByProduct(order.getProduct())
                .orElseThrow(() -> new RuntimeException("Product not found in inventory"));

        if (inventory.getAvailableQuantity() < order.getQuantity()) {
            throw new RuntimeException("Not enough stock available");
        }

        // Deduct quantity
        inventory.setAvailableQuantity(inventory.getAvailableQuantity() - order.getQuantity());
        inventoryRepository.save(inventory);

        order.setTotalPrice(order.getProduct().getPrice() * order.getQuantity());
        return orderRepository.save(order);
    }

    public List<Order> getOrdersByBuyer(User buyer) {
        return orderRepository.findByBuyer(buyer);
    }
}
