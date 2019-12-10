package io.github.arnabmaji19.whatsnow;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

import ca.antonious.materialdaypicker.MaterialDayPicker;
import ca.antonious.materialdaypicker.SingleSelectionMode;
import io.github.arnabmaji19.whatsnow.manager.DateTimeManager;
import io.github.arnabmaji19.whatsnow.manager.ScheduleManager;
import io.github.arnabmaji19.whatsnow.model.Lecture;

public class TodaysScheduleFragment extends Fragment {

    private View view;
    private Context context;
    private ScheduleManager scheduleManager;
    private DateTimeManager dateTimeManager;

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

        //Configuring Week Day picker
        MaterialDayPicker dayPicker = view.findViewById(R.id.day_picker);
        dayPicker.setSelectionMode(SingleSelectionMode.create());
        populateScheduleRecyclerView();
        return view;
    }

    private void populateScheduleRecyclerView() {
        //setting up Recycler View
        RecyclerView scheduleRecyclerView = view.findViewById(R.id.scheduleRecyclerView);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(context);
        scheduleRecyclerView.setLayoutManager(linearLayoutManager);
        List<Lecture> lectureList = scheduleManager.getAllLecturesOfToday();
        LecturesListAdapter adapter = new LecturesListAdapter(lectureList, dateTimeManager);
        scheduleRecyclerView.setAdapter(adapter);
    }
}
