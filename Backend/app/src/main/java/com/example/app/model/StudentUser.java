package com.example.app.model;

import javax.persistence.Entity;
import javax.persistence.PrimaryKeyJoinColumn;

@Entity
@PrimaryKeyJoinColumn(
        name = "studentID"
)
public class StudentUser extends Users {
    private String studentID;

    public StudentUser() {
        this.setRole("Student");
    }

    public StudentUser(int id, String username, String firstName, String lastName, String email, String password, String studentID) {
        super(id, username, firstName, lastName, email, password);
        this.studentID = studentID;
        this.setRole("Student");
    }

    public String getStudentID() {
        return this.studentID;
    }

    public void setStudentID(String studentID) {
        this.studentID = studentID;
    }
}
