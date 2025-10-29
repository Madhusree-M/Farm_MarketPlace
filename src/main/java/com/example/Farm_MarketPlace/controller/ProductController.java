package com.example.Farm_MarketPlace.controller;

import com.example.Farm_MarketPlace.entity.Product;
import com.example.Farm_MarketPlace.entity.User;
import com.example.Farm_MarketPlace.repository.UserRepository;
import com.example.Farm_MarketPlace.service.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/products")
public class ProductController {

    @Autowired
    private ProductService productService;

    @Autowired
    private UserRepository userRepository;

    @PostMapping("/add")
    public Product addProduct(@RequestBody Product product) {
        Long farmerId = product.getFarmer().getId(); // get farmer ID from request
        User farmer = userRepository.findById(farmerId)
                .orElseThrow(() -> new RuntimeException("Farmer not found"));

        product.setFarmer(farmer); // attach actual farmer entity
        return productService.addProduct(product);
    }

    @GetMapping("/all")
    public List<Product> getAllProducts() {
        return productService.getAllProducts();
    }
}
