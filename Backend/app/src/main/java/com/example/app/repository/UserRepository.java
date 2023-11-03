package com.example.app.repository;

import com.example.app.model.UserProfile;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.Map;

public interface UserRepository extends JpaRepository<UserProfile, Long> {
    UserProfile findByUsername(String username);
    List<UserProfile> findAllByUsername(String username);
    List<UserProfile> findByUsernameStartingWithOrderByUsername(String username);
    List<UsernameAndId> findAllByOrderByUsername();
}

