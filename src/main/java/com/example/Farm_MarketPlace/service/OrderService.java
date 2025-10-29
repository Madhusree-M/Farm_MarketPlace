package com.example.Farm_MarketPlace.service;

import com.example.Farm_MarketPlace.entity.Order;
import com.example.Farm_MarketPlace.entity.Order.Status;
import com.example.Farm_MarketPlace.entity.Product;
// import com.example.Farm_MarketPlace.entity.Product;
import com.example.Farm_MarketPlace.entity.User;
import com.example.Farm_MarketPlace.repository.OrderRepository;
import com.example.Farm_MarketPlace.repository.ProductRepository;
import com.example.Farm_MarketPlace.repository.UserRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class OrderService {

    @Autowired
    private OrderRepository orderRepository;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private ProductRepository productRepository;

    public Order placeOrder(Order order) {
        // Fetch full Product
        Product product = productRepository.findById(order.getProduct().getId())
                .orElseThrow(() -> new RuntimeException("Product not found"));

        // Fetch full Buyer
        User buyer = userRepository.findById(order.getBuyer().getId())
                .orElseThrow(() -> new RuntimeException("Buyer not found"));

        // Set actual entities
        order.setProduct(product);
        order.setBuyer(buyer);

        // Check stock
        if (product.getQuantity() < order.getQuantity()) {
            throw new RuntimeException("Not enough stock for: " + product.getName());
        }

        // Deduct quantity
        product.setQuantity(product.getQuantity() - order.getQuantity());
        productRepository.save(product);

        // Calculate total
        order.setTotalPrice(product.getPrice() * order.getQuantity());
        order.setStatus(Order.Status.PENDING);

        return orderRepository.save(order);
    }

    // Get all orders
    public List<Order> getAllOrders() {
        return orderRepository.findAll();
    }

    public List<Order> getOrdersByBuyer(User buyer) {
        return orderRepository.findByBuyer(buyer);
    }

    // Cancel order (restore product quantity)
    public void cancelOrder(Long orderId) {
        Order order = orderRepository.findById(orderId)
                .orElseThrow(() -> new RuntimeException("Order not found"));

        Product product = order.getProduct();
        product.setQuantity(product.getQuantity() + order.getQuantity());
        productRepository.save(product);

        orderRepository.deleteById(orderId);
    }

    public Order updateOrderStatus(Long orderId, Status status) {
        Order order = orderRepository.findById(orderId)
                .orElseThrow(() -> new RuntimeException("Order not found"));

        order.setStatus(status);
        return orderRepository.save(order);
    }
}
