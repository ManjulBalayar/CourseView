package com.example.app.repository;

import com.example.app.model.UserProfile;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.Map;

public interface UserRepository extends JpaRepository<UserProfile, Long> {
    UserProfile findByUsername(String username);
    List<UserProfile> findAllByUsername(String username);

    @Query("FROM UserProfile WHERE username LIKE ?1% ORDER BY username")
    List<UserProfile> findByNameStartsWithSorted(String name);

    @Query("SELECT new map(u.username as name, u.userid as id) FROM UserProfile u ORDER BY u.username")
    List<Map<String, Object>> findAllUserProfileNames();
}

