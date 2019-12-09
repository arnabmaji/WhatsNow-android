package io.github.arnabmaji19.whatsnow;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import ca.antonious.materialdaypicker.MaterialDayPicker;
import ca.antonious.materialdaypicker.SingleSelectionMode;

public class TodaysScheduleFragment extends Fragment {

    private View view;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        super.onCreateView(inflater, container, savedInstanceState);
        view = inflater.inflate(R.layout.fragement_todays_schedule, container, false);

        //Configuring Week Day picker
        MaterialDayPicker dayPicker = view.findViewById(R.id.day_picker);
        dayPicker.setSelectionMode(SingleSelectionMode.create());

        return view;
    }
}
