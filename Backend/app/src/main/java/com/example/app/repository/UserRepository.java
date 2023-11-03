package com.example.app.repository;

import com.example.app.model.UserProfile;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface UserRepository extends JpaRepository<UserProfile, Long> {
    UserProfile findByUsername(String username);
    List<UserProfile> findAllByUsername(String username);
}

