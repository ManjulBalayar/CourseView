package com.example.demo1;

import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.GetMapping;

import java.util.List;

@Service
public class StudentService {
    public List<Student> getStudents() {
        return List.of(new Student(
                        577753192,
                        "Manjul Balayar",
                        "mbalayar@iastate.edu",
                        19
                ),
                new Student(
                        537539893,
                        "Kalem Schrock",
                        "kschrock@iastate.edu",
                        21
                ),
                new Student(
                        342434322,
                        "Benjamin Muslic",
                        "bmuslic@iastate.edu",
                        20
                ),
                new Student(
                        1256434536,
                        "Devin Alamsaya",
                        "dalamsaya@iastate.edu",
                        20
                )
        );
    }

}
