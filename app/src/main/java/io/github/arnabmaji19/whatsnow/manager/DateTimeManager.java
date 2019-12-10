package io.github.arnabmaji19.whatsnow.manager;

//Manages Data Time based operations

import android.util.SparseArray;

import java.util.Calendar;
import java.util.Locale;

public class DateTimeManager {

    private static final int PERIOD_MAX_TIME = 55;

    private static final SparseArray<String> weekdays = new SparseArray<>();

    static {
        weekdays.put(1, "Sunday");
        weekdays.put(2, "Monday");
        weekdays.put(3, "Tuesday");
        weekdays.put(4, "Wednesday");
        weekdays.put(5, "Thursday");
        weekdays.put(6, "Friday");
        weekdays.put(7, "Saturday");
    }

    public enum Weekdays {MONDAY, TUESDAY, WEDNESDAY, THURSDAY, FRIDAY}

    private String timeString;
    private int currentTimeValue;
    private String periodStartTimeString;
    private Calendar calendar;

    public DateTimeManager() {
        this.calendar = Calendar.getInstance();
        //Calculating current time values
        int currentMinute = calendar.get(Calendar.MINUTE);
        int currentHour = calendar.get(Calendar.HOUR_OF_DAY);
        this.timeString = String.format(Locale.getDefault(), "%d%02d", currentHour, currentMinute);
        this.currentTimeValue = Integer.parseInt(timeString);
    }

    public int getCurrentLectureNo() {
        int currentPeriod = -1;
        int startTime = 0;
        if (currentTimeValue >= 900 && currentTimeValue < 955) {
            currentPeriod = 1;
            startTime = 900;
        } else if (currentTimeValue >= 955 && currentTimeValue < 1050) {
            currentPeriod = 2;
            startTime = 955;
        } else if (currentTimeValue >= 1050 && currentTimeValue < 1145) {
            currentPeriod = 3;
            startTime = 1050;
        } else if (currentTimeValue >= 1145 && currentTimeValue < 1240) {
            currentPeriod = 4;
            startTime = 1145;
        } else if (currentTimeValue >= 1240 && currentTimeValue < 1335) {
            currentPeriod = 5;
            startTime = 1240;
        } else if (currentTimeValue >= 1335 && currentTimeValue < 1430) {
            currentPeriod = 6;
            startTime = 1335;
        } else if (currentTimeValue >= 1430 && currentTimeValue < 1525) {
            currentPeriod = 7;
            startTime = 1430;
        } else if (currentTimeValue >= 1525 && currentTimeValue < 1620) {
            currentPeriod = 8;
            startTime = 1525;
        }
        periodStartTimeString = String.format(Locale.getDefault(), "%04d", startTime);
        return currentPeriod;
    }

    public double getElapsedTimeFraction() {
        String currentTimeString = String.format(Locale.getDefault(), "%04d", this.currentTimeValue);
        String startTime = periodStartTimeString;
        int currentHour = Integer.parseInt(currentTimeString.substring(0, 2));
        int currentMinute = Integer.parseInt(currentTimeString.substring(2));

        int startHour = Integer.parseInt(startTime.substring(0, 2));
        int startMinute = Integer.parseInt(startTime.substring(2));
        int elapsedMinute = 0;
        if (currentMinute >= startMinute) {
            elapsedMinute += (currentMinute - startMinute);
        } else {
            currentHour--;
            currentMinute += 60;
            elapsedMinute += (currentMinute - startMinute);
        }
        int elapsedTime = elapsedMinute + (currentHour - startHour) * 60;

        return (double) (elapsedTime) / (double) (PERIOD_MAX_TIME);
    }

    public String getTodayAsString() {
        return weekdays.get(calendar.get(Calendar.DAY_OF_WEEK));
    }
}
