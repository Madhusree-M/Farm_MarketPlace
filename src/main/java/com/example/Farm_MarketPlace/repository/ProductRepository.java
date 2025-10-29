package com.example.Farm_MarketPlace.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import com.example.Farm_MarketPlace.entity.Product;
import com.example.Farm_MarketPlace.entity.User;
import java.util.List;

public interface ProductRepository extends JpaRepository<Product, Long> {
    List<Product> findByFarmer(User farmer);
}