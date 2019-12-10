package io.github.arnabmaji19.whatsnow.manager;

//Manages different schedule operations

import java.util.List;
import java.util.Map;

import io.github.arnabmaji19.whatsnow.model.Lecture;

public class ScheduleManager {
    private Map<String, List<Lecture>> schedule;
    private int currentLectureNo;

    public ScheduleManager(Map<String, List<Lecture>> schedule, int currentLectureNo) {
        this.schedule = schedule;
        this.currentLectureNo = currentLectureNo;
    }

    public List<Lecture> getAllLecturesOfToday() {
        //TODO: Get current day as string
        String weekdayString = "";
        return schedule.get(weekdayString);
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
