package io.github.arnabmaji19.whatsnow.util;

//Manages different schedule operations

import java.util.List;
import java.util.Map;

import io.github.arnabmaji19.whatsnow.model.Lecture;

public class ScheduleManager {
    private DateTimeManager dateTimeManager;
    private Map<String, List<Lecture>> schedule;

    public ScheduleManager(DateTimeManager dateTimeManager, Map<String, List<Lecture>> schedule) {
        this.dateTimeManager = dateTimeManager;
        this.schedule = schedule;
    }

    public List<Lecture> getAllLecturesOfToday() {
        return schedule.get(dateTimeManager.getTodayAsString());
    }

    public Lecture getOnGoingLecture() {
        return dateTimeManager.workingHours() ? getAllLecturesOfToday()
                .get(dateTimeManager.getCurrentLectureNo() - 1) : null;
    }

    public Lecture getUpcomingLecture() {
        try {
            return (dateTimeManager.workingHours()) ? getAllLecturesOfToday()
                    .get(dateTimeManager.getCurrentLectureNo()) : null;
        } catch (IndexOutOfBoundsException e) { //If it requests last lecture
            return null;
        }
    }

    public List<Lecture> getLecturesOfDay(String day) {
        return schedule.get(day);
    }
}
