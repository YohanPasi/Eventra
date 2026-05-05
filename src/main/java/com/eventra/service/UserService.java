package com.eventra.service;

import com.eventra.model.User;
import com.eventra.repository.UserRepository;

import java.util.UUID;

public class UserService {

    private UserRepository userRepository = new UserRepository();

    public boolean emailExists(String email) {
        return userRepository.getAllUsers()
                .stream()
                .anyMatch(u -> u.getEmail().equalsIgnoreCase(email));
    }

    public boolean registerUser(User user) {

        if (emailExists(user.getEmail())) {
            return false;
        }

        // Generate unique ID
        user.setId("U" + UUID.randomUUID().toString().substring(0,5));

        // Default role
        user.setRole("USER");

        userRepository.saveUser(user);
        return true;
    }

    public User login(String email, String password) {

        return userRepository.getAllUsers()
                .stream()
                .filter(u -> u.getEmail().equals(email) &&
                             u.getPassword().equals(password))
                .findFirst()
                .orElse(null);
    }
}
