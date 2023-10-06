package com.example.app.model;

import javax.persistence.Entity;
import javax.persistence.PrimaryKeyJoinColumn;

@Entity
@PrimaryKeyJoinColumn(
        name = "advisorID"
)
public class AdvisorUser extends Users {
    private String advisorID;

    public AdvisorUser() {
        this.setRole("Advisor");
    }

    public AdvisorUser(int id, String username, String firstName, String lastName, String email, String password, String studentID) {
        super(id, username, firstName, lastName, email, password);
        this.advisorID = studentID;
        this.setRole("Advisor");
    }

    public String getAdvisorID() {
        return this.advisorID;
    }

    public void setAdvisorID(String advisorID) {
        this.advisorID = advisorID;
    }
}
