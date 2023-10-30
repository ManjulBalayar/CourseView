package com.example.app.model;

import javax.persistence.*;

@Entity
public class Schedule {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long schedule_id;


    @ManyToOne
    @JoinColumn(name = "courseid")
    private Course course;

    @OneToOne
    @JoinColumn(name = "userid", unique = true)
    private UserProfile userProfile;

    public Long getSchedule_id() {
        return schedule_id;
    }

    public void setSchedule_id(Long schedule_id) {
        this.schedule_id = schedule_id;
    }
}
