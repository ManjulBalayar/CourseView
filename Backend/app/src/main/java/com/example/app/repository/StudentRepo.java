package com.example.app.repository;

import com.example.app.model.Student;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.Map;

public interface StudentRepo extends JpaRepository<Student, Long> {

    @Query("FROM Student WHERE name LIKE ?1% ORDER BY name")
    List<Student> findByNameStartsWithSorted(String name);

    @Query("SELECT new map(s.name as name, s.id as id) FROM Student s ORDER BY s.name")
    List<Map<String, Object>> findAllStudentNames();
}
