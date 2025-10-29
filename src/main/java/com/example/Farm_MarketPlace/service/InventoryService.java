package com.example.Farm_MarketPlace.service;

import com.example.Farm_MarketPlace.entity.Inventory;
import com.example.Farm_MarketPlace.entity.Product;
import com.example.Farm_MarketPlace.repository.InventoryRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class InventoryService {

    @Autowired
    private InventoryRepository inventoryRepository;

    public Inventory createInventory(Product product, int quantity) {
        Inventory inventory = Inventory.builder()
                .product(product)
                .availableQuantity(quantity)
                .build();
        return inventoryRepository.save(inventory);
    }

    public Optional<Inventory> getInventoryByProduct(Product product) {
        return inventoryRepository.findByProduct(product);
    }
}
