package com.example.SalonMate.service;

import com.example.SalonMate.model.User;
import com.example.SalonMate.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class UserService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private BCryptPasswordEncoder passwordEncoder;  // For encoding passwords

    // Register a new user
    public void registerUser(User user) {

        // Encrypt the user's password before saving
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        userRepository.save(user);
    }

    // Save or update user details
    public void saveUser(User user) {
        userRepository.save(user);
    }

    // Validate user credentials during login
    public boolean validateUser(String username, String rawPassword) {

        Optional<User> userOpt = userRepository.findByUsername(username);

        if (userOpt.isPresent()) {
            User user = userOpt.get();

            // Compare raw password with the encoded password in the database
            return passwordEncoder.matches(rawPassword, user.getPassword());
        }
        return false;
    }

    // Find user by username (to avoid registering duplicate usernames)
    public Optional<User> findUserByUsername(String username) {

        return userRepository.findByUsername(username);

    }

    // Check if the user already exists (for registration validation)
    public boolean isUserExists(String username) {

        return findUserByUsername(username).isPresent();
        
    }

    // Retrieve user by ID
    public Optional<User> findById(Long userId) {
        return userRepository.findById(userId);
    }
}
