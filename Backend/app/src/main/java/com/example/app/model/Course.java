package com.example.app.model;

import javax.persistence.*;
import java.util.List;

@Entity
public class Course {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long courseid;

    private String name;

    private String description;

    private String department;

    // TODO
    // 1) add relationship oneToMany with review
    // 2) add relationship oneToMany with schedule
    @OneToMany(mappedBy = "course")
    private List<Review> reviews;

    @OneToMany(mappedBy = "course")
    private List<Schedule> schedules;

    public Long getCourse_id() {
        return courseid;
    }

    public void setCourse_id(Long course_id) {
        this.courseid = course_id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getDepartment() {
        return department;
    }

    public void setDepartment(String department) {
        this.department = department;
    }
}
