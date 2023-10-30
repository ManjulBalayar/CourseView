package com.example.app.repository;

import com.example.app.model.Users;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface UserRepository extends JpaRepository<Users, Long> {
    Users findByUsername(String username);

    // This will return a list of Users with the specified username
    List<Users> findAllByUsername(String username);
}
