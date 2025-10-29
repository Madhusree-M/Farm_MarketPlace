package com.example.Farm_MarketPlace.service;

import com.example.Farm_MarketPlace.entity.Product;
import com.example.Farm_MarketPlace.entity.User;
import com.example.Farm_MarketPlace.repository.ProductRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ProductService {

    @Autowired
    private ProductRepository productRepository;

    public Product addProduct(Product product) {
        return productRepository.save(product);
    }

    public List<Product> getAllProducts() {
        return productRepository.findAll();
    }

    public List<Product> getProductsByFarmer(User farmer) {
        return productRepository.findByFarmer(farmer);
    }
}
