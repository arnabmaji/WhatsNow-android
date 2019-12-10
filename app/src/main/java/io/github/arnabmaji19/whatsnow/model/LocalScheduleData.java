package io.github.arnabmaji19.whatsnow.model;

import java.io.Serializable;
import java.util.List;
import java.util.Map;

public class LocalScheduleData extends ScheduleData implements Serializable {
    private final static Long serialVersionUID = 1L;
    private Map<String, List<Lecture>> fullSchedule;

    public LocalScheduleData(String department, String batch, String section, String semester, String scheduleId, String lastUpdated, Map<String, List<Lecture>> fullSchedule) {
        super(department, batch, section, semester, scheduleId, lastUpdated);
        this.fullSchedule = fullSchedule;
    }

    public Map<String, List<Lecture>> getFullSchedule() {
        return fullSchedule;
    }

    public void setFullSchedule(Map<String, List<Lecture>> fullSchedule) {
        this.fullSchedule = fullSchedule;
    }
}
