package com.example.Farm_MarketPlace.controller;

import com.example.Farm_MarketPlace.entity.Product;
import com.example.Farm_MarketPlace.entity.User;
import com.example.Farm_MarketPlace.repository.UserRepository;
import com.example.Farm_MarketPlace.service.ProductService;
import com.example.Farm_MarketPlace.service.UserService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@Controller
@RequestMapping("/products")
public class ProductController {

    @Autowired
    private ProductService productService;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private UserService userService;

    @PostMapping("/save")
    public String addProduct(@ModelAttribute Product product) {
        Long farmerId = product.getFarmer().getId(); // get farmer ID from request
        User farmer = userRepository.findById(farmerId)
                .orElseThrow(() -> new RuntimeException("Farmer not found"));

        product.setFarmer(farmer); // attach actual farmer entity
        productService.addProduct(product);
        return "redirect:/products";
    }

    @GetMapping
    public String listProducts(Model model) {
        model.addAttribute("products", productService.getAllProducts());
        return "products/list";
    }

    @GetMapping("/add")
    public String showAddProductForm(Model model) {
        model.addAttribute("product", new Product());
        model.addAttribute("farmers", userService.getAllUsers()
                .stream().filter(u -> u.getRole() == User.Role.FARMER).toList());
        return "products/form";
    }

    @GetMapping("/edit/{id}")
    public String editProduct(@PathVariable Long id, Model model) {
        Optional<Product> product = productService.getProductById(id);
        if (product.isPresent()) {
            model.addAttribute("product", product.get());
            model.addAttribute("farmers", userService.getAllUsers()
                    .stream().filter(u -> u.getRole() == User.Role.FARMER).toList());
            return "products/form";
        }
        return "redirect:/products";
    }

    @GetMapping("/delete/{id}")
    public String deleteProduct(@PathVariable Long id) {
        productService.deleteProduct(id);
        return "redirect:/products";
    }
}
