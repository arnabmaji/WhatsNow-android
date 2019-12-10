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
        return getAllLecturesOfToday()
                .get(--currentLectureNo);
    }

    public Lecture getUpcomingLecture() {
        return getAllLecturesOfToday()
                .get(currentLectureNo);
    }
}
