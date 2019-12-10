package io.github.arnabmaji19.whatsnow.model;

//Lecture POJO class for retrieving data from database and app usage

public class Lecture {
    private int period;
    private String courseName;
    private String faculty;
    private String room;
    private String duration;

    public Lecture() {
    }

    public Lecture(int period, String courseName, String faculty, String room, String duration) {
        this.period = period;
        this.courseName = courseName;
        this.faculty = faculty;
        this.room = room;
        this.duration = duration;
    }

    public int getPeriod() {
        return period;
    }

    public void setPeriod(int period) {
        this.period = period;
    }

    public String getCourseName() {
        return courseName;
    }

    public void setCourseName(String courseName) {
        this.courseName = courseName;
    }

    public String getFaculty() {
        return faculty;
    }

    public void setFaculty(String faculty) {
        this.faculty = faculty;
    }

    public String getRoom() {
        return room;
    }

    public void setRoom(String room) {
        this.room = room;
    }

    public String getDuration() {
        return duration;
    }

    public void setDuration(String duration) {
        this.duration = duration;
    }
}
