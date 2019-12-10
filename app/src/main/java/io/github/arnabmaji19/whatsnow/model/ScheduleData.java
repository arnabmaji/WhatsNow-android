package io.github.arnabmaji19.whatsnow.model;

//ScheduleData PJO class for retrieving data from database

public class ScheduleData {

    private String department;
    private String batch;
    private String section;
    private String semester;
    private String scheduleId;
    private String lastUpdated;

    public ScheduleData() {
    }

    public ScheduleData(String department, String batch, String section, String semester, String scheduleId, String lastUpdated) {
        this.department = department;
        this.batch = batch;
        this.section = section;
        this.semester = semester;
        this.scheduleId = scheduleId;
        this.lastUpdated = lastUpdated;
    }

    public String getDepartment() {
        return department;
    }

    public void setDepartment(String department) {
        this.department = department;
    }

    public String getBatch() {
        return batch;
    }

    public void setBatch(String batch) {
        this.batch = batch;
    }

    public String getSection() {
        return section;
    }

    public void setSection(String section) {
        this.section = section;
    }

    public String getSemester() {
        return semester;
    }

    public void setSemester(String semester) {
        this.semester = semester;
    }

    public String getScheduleId() {
        return scheduleId;
    }

    public void setScheduleId(String scheduleId) {
        this.scheduleId = scheduleId;
    }

    public String getLastUpdated() {
        return lastUpdated;
    }

    public void setLastUpdated(String lastUpdated) {
        this.lastUpdated = lastUpdated;
    }
}
