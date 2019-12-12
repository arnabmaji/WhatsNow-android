package io.github.arnabmaji19.whatsnow.util;

//Manages Data Time based operations

import android.util.SparseArray;

import com.google.common.collect.BiMap;
import com.google.common.collect.HashBiMap;

import java.util.Calendar;
import java.util.Locale;

import ca.antonious.materialdaypicker.MaterialDayPicker;

public class DateTimeManager {

    public static final int PERIOD_MAX_TIME = 55;

    private static final SparseArray<String> weekdays = new SparseArray<>();
    private static final BiMap<String, MaterialDayPicker.Weekday> materialDayPickerWeekdayMap = HashBiMap.create();

    static {
        weekdays.put(1, "Sunday");
        weekdays.put(2, "Monday");
        weekdays.put(3, "Tuesday");
        weekdays.put(4, "Wednesday");
        weekdays.put(5, "Thursday");
        weekdays.put(6, "Friday");
        weekdays.put(7, "Saturday");

        //Adding Strings to materialDayPickerWeekdayMap
        materialDayPickerWeekdayMap.put("Sunday", MaterialDayPicker.Weekday.SUNDAY);
        materialDayPickerWeekdayMap.put("Monday", MaterialDayPicker.Weekday.MONDAY);
        materialDayPickerWeekdayMap.put("Tuesday", MaterialDayPicker.Weekday.TUESDAY);
        materialDayPickerWeekdayMap.put("Wednesday", MaterialDayPicker.Weekday.WEDNESDAY);
        materialDayPickerWeekdayMap.put("Thursday", MaterialDayPicker.Weekday.THURSDAY);
        materialDayPickerWeekdayMap.put("Friday", MaterialDayPicker.Weekday.FRIDAY);
        materialDayPickerWeekdayMap.put("Saturday", MaterialDayPicker.Weekday.SATURDAY);
    }

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
        /*
         * Function returns current lecture progress in Fraction
         */

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
        return weekdays.get(getCurrentDayNo());
    }

    private int getCurrentDayNo() {
        return calendar.get(Calendar.DAY_OF_WEEK);
    }


    public String getMaterialDayPickerWeekdayAsString(MaterialDayPicker.Weekday weekday) {
        /*
         * Function to Convert MaterialDayPicker.Weekday to String
         */
        return materialDayPickerWeekdayMap.inverse().get(weekday);
    }

    public MaterialDayPicker.Weekday getTodayAsMaterialDayPickerWeekday() {
        /*
         * Function to convert String to MaterialDayPicker.Weekday
         */

        return materialDayPickerWeekdayMap.get(getTodayAsString());
    }


    public int getLectureProgressStatus(int weekDayNo, int lectureNo) {
        /*
         * Returns Lecture Progress status: -1: Upcoming, 0: Ongoing, 1: Completed
         */
        int currentDayNo = getCurrentDayNo();
        int currentLectureNo = getCurrentLectureNo();

        if (weekDayNo < currentDayNo) {
            return 1;
        } else if (weekDayNo == currentDayNo) {
            if (lectureNo < currentLectureNo) {
                return 1;
            } else if (lectureNo == currentLectureNo) {
                return 0;
            }
        }
        return -1;
    }
}
