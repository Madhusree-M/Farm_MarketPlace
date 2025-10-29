package com.example.Farm_MarketPlace.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import com.example.Farm_MarketPlace.entity.Inventory;
import com.example.Farm_MarketPlace.entity.Product;
import java.util.Optional;

public interface InventoryRepository extends JpaRepository<Inventory, Long> {
    Optional<Inventory> findByProduct(Product product);
}
