package com.example.app.model;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

@Entity
public class Schedule {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long schedule_id;

    public Long getSchedule_id() {
        return schedule_id;
    }

    public void setSchedule_id(Long schedule_id) {
        this.schedule_id = schedule_id;
    }
}
