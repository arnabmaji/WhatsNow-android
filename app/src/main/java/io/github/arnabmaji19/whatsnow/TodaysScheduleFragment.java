package io.github.arnabmaji19.whatsnow;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;
import java.util.Locale;

import ca.antonious.materialdaypicker.MaterialDayPicker;
import io.github.arnabmaji19.whatsnow.model.Lecture;
import io.github.arnabmaji19.whatsnow.util.DateTimeManager;
import io.github.arnabmaji19.whatsnow.util.LecturesListAdapter;
import io.github.arnabmaji19.whatsnow.util.ScheduleManager;

public class TodaysScheduleFragment extends Fragment {

    private View view;
    private Context context;
    private ScheduleManager scheduleManager;
    private DateTimeManager dateTimeManager;
    private TextView offDayTextView;
    private RecyclerView scheduleRecyclerView;

    public TodaysScheduleFragment(Context context, ScheduleManager scheduleManager, DateTimeManager dateTimeManager) {
        this.context = context;
        this.scheduleManager = scheduleManager;
        this.dateTimeManager = dateTimeManager;
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        super.onCreateView(inflater, container, savedInstanceState);
        view = inflater.inflate(R.layout.fragement_todays_schedule, container, false);

        offDayTextView = view.findViewById(R.id.offdayTextView);
        //Configuring Recycler View
        scheduleRecyclerView = view.findViewById(R.id.scheduleRecyclerView);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(context);
        scheduleRecyclerView.setLayoutManager(linearLayoutManager);

        //Configuring Week Day picker
        MaterialDayPicker dayPicker = view.findViewById(R.id.day_picker);
        dayPicker.setLocale(Locale.ENGLISH);

        //Selecting toady
        MaterialDayPicker.Weekday today = dateTimeManager.getTodayAsMaterialDayPickerWeekday();
        dayPicker.setSelectedDays(today);
        showSchedule(today); //Showing todays schedule by default

        dayPicker.setDayPressedListener(new MaterialDayPicker.DayPressedListener() {
            @Override
            public void onDayPressed(@NonNull MaterialDayPicker.Weekday selectedDay, boolean b) {
                showSchedule(selectedDay); //show schedule for current day
            }
        });
        return view;
    }

    private void showSchedule(MaterialDayPicker.Weekday selectedDay) {
        if (selectedDay.equals(MaterialDayPicker.Weekday.SATURDAY) ||
                selectedDay.equals(MaterialDayPicker.Weekday.SUNDAY)) {
            //If It's off day, hide recycler view and show off day message
            scheduleRecyclerView.setVisibility(View.GONE);
            offDayTextView.setVisibility(View.VISIBLE);
        } else {
            //Otherwise show recycler view and hide off day message
            scheduleRecyclerView.setVisibility(View.VISIBLE);
            offDayTextView.setVisibility(View.GONE);
            //Show schedule for specific day
            String day = dateTimeManager.getMaterialDayPickerWeekdayAsString(selectedDay);
            populateRecyclerView(day);
        }
    }

    private void populateRecyclerView(String day) { //Populate Recycler View with current day's lecture
        //Get List of Lectures for requested day
        List<Lecture> lecturesList = scheduleManager.getLecturesOfDay(day);
        LecturesListAdapter adapter = new LecturesListAdapter(lecturesList, dateTimeManager, day);
        scheduleRecyclerView.setAdapter(adapter);
    }
}
