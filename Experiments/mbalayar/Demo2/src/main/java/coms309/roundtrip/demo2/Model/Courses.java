package coms309.roundtrip.demo2.Model;

public class Courses {
    int ID;
    String name;
    int level;
    String professor;

    public Courses(int ID, String name, int level, String professor) {
        this.ID = ID;
        this.name = name;
        this.level = level;
        this.professor = professor;
    }

    public int getID() {
        return ID;
    }

    public void setID(int ID) {
        this.ID = ID;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getLevel() {
        return level;
    }

    public void setLevel(int level) {
        this.level = level;
    }

    public String getProfessor() {
        return professor;
    }

    public void setProfessor(String professor) {
        this.professor = professor;
    }
}
