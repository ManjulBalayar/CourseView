package com.example.app.repository;

import com.example.app.model.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface UserRepository extends JpaRepository<User, Long> {
    User findByUsername(String username);

    // This will return a list of Users with the specified username
    List<User> findAllByUsername(String username);
}
