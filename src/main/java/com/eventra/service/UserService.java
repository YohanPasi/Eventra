package com.eventra.service;

import com.eventra.model.User;
import com.eventra.repository.UserRepository;

import java.util.UUID;

public class UserService {

    private UserRepository userRepository = new UserRepository();

    public boolean usernameExists(String username) {
        return userRepository.getAllUsers()
                .stream()
                .anyMatch(u -> u.getName().equalsIgnoreCase(username));
    }

    public boolean emailExists(String email) {
        return userRepository.getAllUsers()
                .stream()
                .anyMatch(u -> u.getEmail().equalsIgnoreCase(email));
    }

    public boolean registerUser(User user) {

        if (emailExists(user.getEmail()) || usernameExists(user.getName())) {
            return false;
        }

        // Generate unique ID
        user.setId("U" + UUID.randomUUID().toString().substring(0,5));

        // Default role
        user.setRole("USER");

        userRepository.saveUser(user);
        return true;
    }

    public User login(String username, String password) {

        return userRepository.getAllUsers()
                .stream()
                .filter(u -> u.getName().equals(username) &&
                             u.getPassword().equals(password))
                .findFirst()
                .orElse(null);
    }
}
