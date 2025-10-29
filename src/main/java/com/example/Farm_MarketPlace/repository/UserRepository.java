package com.example.Farm_MarketPlace.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import com.example.Farm_MarketPlace.entity.User;
import java.util.Optional;

public interface UserRepository extends JpaRepository<User, Long> {
    Optional<User> findByEmail(String email);
}