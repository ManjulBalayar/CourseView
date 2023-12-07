package com.example.app.repository;

import com.example.app.miscellaneous.UsernameAndId;
import com.example.app.model.UserProfile;
import org.apache.catalina.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface UserRepository extends JpaRepository<UserProfile, Long> {
    UserProfile findByUsername(String username);
    List<UserProfile> findAllByUsername(String username);
    List<UserProfile> findByUsernameStartingWithOrderByUsername(String username);
    List<UsernameAndId> findAllByOrderByUsername();
    List<UserProfile> findByRole(String role);
    Optional<UserProfile> findById(Long userId);

}

