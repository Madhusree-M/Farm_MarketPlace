package com.example.Farm_MarketPlace.service;

import com.example.Farm_MarketPlace.entity.User;
import com.example.Farm_MarketPlace.repository.UserRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class UserService {

    @Autowired
    private UserRepository userRepository;

    public User registerUser(User user) {
        return userRepository.save(user);
    }

    // Get all users
    public List<User> getAllUsers() {
        return userRepository.findAll();
    }

    // Find user by ID
    public Optional<User> getUserById(Long id) {
        return userRepository.findById(id);
    }

    // Find User by email
    public Optional<User> getUserByEmail(String email) {
        return userRepository.getUserByEmail(email);
    }

    // Delete user by Id
    public void deleteUser(Long id) {
        userRepository.deleteById(id);
    }
}
