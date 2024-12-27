package com.example.SalonMate.repository;

import com.example.SalonMate.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.Optional;

public interface UserRepository extends JpaRepository<User, Long> {

    // Custom query to find a User by username
    Optional<User> findByUsername(String username);
    
    // Custom query to find a User by email
    Optional<User> findByEmail(String email);
}
