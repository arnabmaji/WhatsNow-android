package io.github.arnabmaji19.whatsnow.manager;

//Manages different schedule operations

import java.util.List;
import java.util.Map;

import io.github.arnabmaji19.whatsnow.model.Lecture;

public class ScheduleManager {
    private Map<String, List<Lecture>> schedule;
    private int currentLectureNo;
    private String todayAsString;

    public ScheduleManager(Map<String, List<Lecture>> schedule, int currentLectureNo, String todayAsString) {
        this.schedule = schedule;
        this.currentLectureNo = currentLectureNo;
        this.todayAsString = todayAsString;
    }

    public List<Lecture> getAllLecturesOfToday() {
        return schedule.get(todayAsString);
    }

    public Lecture getOnGoingLecture() {
        return workingHours() ? getAllLecturesOfToday()
                .get(currentLectureNo - 1) : null;
    }

    public Lecture getUpcomingLecture() {
        return (workingHours() && currentLectureNo < 8) ? getAllLecturesOfToday()
                .get(currentLectureNo) : null;
    }

    public List<Lecture> getLecturesOfDay(String day) {
        return schedule.get(day);
    }

    private boolean workingHours() {
        return !(currentLectureNo == -1);
    }
}
