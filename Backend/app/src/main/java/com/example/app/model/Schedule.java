package com.example.app.model;

import com.fasterxml.jackson.annotation.JsonIgnore;

import javax.persistence.*;
import java.util.List;

@Entity
public class Schedule {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long scheduleid;


    @ManyToMany
    @JoinTable(
            name = "schedule_course",
            joinColumns = @JoinColumn(name = "scheduleid"),
            inverseJoinColumns = @JoinColumn(name = "courseid"))
    private List<Course> courses;

    @OneToOne
    @JoinColumn(name = "userid", unique = true)
    @JsonIgnore
    private UserProfile userProfile;

    public Long getScheduleid() {
        return scheduleid;
    }

    public void setScheduleid(Long scheduleid) {
        this.scheduleid = scheduleid;
    }

    public List<Course> getCourses() {
        return courses;
    }

    public void setCourses(List<Course> courses) {
        this.courses = courses;
    }

    public UserProfile getUserProfile() {
        return userProfile;
    }

    public void setUserProfile(UserProfile userProfile) {
        this.userProfile = userProfile;
    }
}
