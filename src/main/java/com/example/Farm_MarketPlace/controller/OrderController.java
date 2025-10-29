package com.example.Farm_MarketPlace.controller;

import com.example.Farm_MarketPlace.entity.Order;
import com.example.Farm_MarketPlace.entity.User;
import com.example.Farm_MarketPlace.service.OrderService;
import com.example.Farm_MarketPlace.service.ProductService;
import com.example.Farm_MarketPlace.service.UserService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
@RequestMapping("/orders")
public class OrderController {

    @Autowired
    private OrderService orderService;

    @Autowired
    private ProductService productService;

    @Autowired
    private UserService userService;

    // List all orders
    @GetMapping
    public String listOrders(Model model) {
        List<Order> orders = orderService.getAllOrders();
        model.addAttribute("orders", orders);
        return "orders/list";
    }

    // Show order form
    @GetMapping("/add")
    public String showAddForm(Model model) {
        model.addAttribute("order", new Order());
        model.addAttribute("products", productService.getAllProducts());
        model.addAttribute("buyers", userService.getAllUsers()
                .stream().filter(u -> u.getRole() == User.Role.BUYER).toList());
        return "orders/form";
    }

    @PostMapping("/save")
    public String placeOrder(@ModelAttribute Order order, Model model) {
        try {
            orderService.placeOrder(order);
            return "redirect:/orders";
        } catch (RuntimeException e) {
            // Show error message on same page
            model.addAttribute("error", e.getMessage());
            model.addAttribute("order", order);
            model.addAttribute("products", productService.getAllProducts());
            model.addAttribute("buyers", userService.getAllUsers()
                    .stream().filter(u -> u.getRole() == User.Role.BUYER).toList());
            return "orders/form";
        }
    }

    // Cancel an order
    @GetMapping("/cancel/{id}")
    public String cancelOrder(@PathVariable Long id) {
        orderService.cancelOrder(id);
        return "redirect:/orders";
    }

    // Update order status
    @GetMapping("/status/{id}/{status}")
    public String updateStatus(@PathVariable Long id, @PathVariable Order.Status status) {
        orderService.updateOrderStatus(id, status);
        return "redirect:/orders";
    }
}
