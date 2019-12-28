package io.github.arnabmaji19.whatsnow.util;

import androidx.annotation.NonNull;

import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;
import com.google.gson.Gson;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import io.github.arnabmaji19.whatsnow.model.Lecture;
import io.github.arnabmaji19.whatsnow.model.ScheduleData;

public class UpdateManager {
    private static final String SCHEDULE_FIELD_NAME = "json_data";
    private static final String TAG = "UpdateManager";

    private DatabaseManager databaseManager;

    public UpdateManager(DatabaseManager databaseManager) {
        this.databaseManager = databaseManager;
    }

    public void fetchSchedulesList(final OnCompleteListener listener) {
        //get data from firebase
        databaseManager.getSchedulesListCollection()
                .get()
                .addOnCompleteListener(new com.google.android.gms.tasks.OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                        List<ScheduleData> scheduleDataList = new ArrayList<>();
                        QuerySnapshot snapshots = task.getResult();
                        if (snapshots != null) {
                            for (QueryDocumentSnapshot snapshot : snapshots) {
                                scheduleDataList.add(snapshot.toObject(ScheduleData.class));
                            }
                            listener.onComplete(scheduleDataList);
                        }
                    }
                });
    }

    //Get selected schedule from database
    public void fetchSchedule(final String scheduleId, final OnSuccessListener onSuccessListener) {
        databaseManager.getScheduleCollection(scheduleId)
                .addOnCompleteListener(new com.google.android.gms.tasks.OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                        QuerySnapshot snapshots = task.getResult();
                        if (snapshots != null) {
                            Map<String, List<Lecture>> schedule = new HashMap<>();
                            for (QueryDocumentSnapshot snapshot : snapshots) {
                                String jsonString = snapshot.getString(SCHEDULE_FIELD_NAME);
                                Gson gson = new Gson();
                                schedule = gson.fromJson(jsonString, schedule.getClass());
                            }
                            onSuccessListener.onSuccess(schedule);
                        }
                    }
                });
    }

    public void getLastUpdated(final String scheduleId, final OnDataFetchedListener onDataFetchedListener) {
        /*
         * Gets Last Updated details for a schedule
         */
        databaseManager.getSchedulesListCollection()
                .whereEqualTo("scheduleId", scheduleId)
                .get()
                .addOnCompleteListener(new com.google.android.gms.tasks.OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                        QuerySnapshot snapshots = task.getResult();
                        if (snapshots != null) {
                            ScheduleData scheduleData = null;
                            for (QueryDocumentSnapshot snapshot : snapshots)
                                scheduleData = snapshot.toObject(ScheduleData.class);
                            if (scheduleData != null)
                                onDataFetchedListener.onDataFetched(scheduleData.getLastUpdated());
                        }
                    }
                });
    }

    public interface OnDataFetchedListener {
        void onDataFetched(String lastUpdated);
    }

    public interface OnCompleteListener {
        void onComplete(List<ScheduleData> scheduleDataList);
    }

    public interface OnSuccessListener {
        void onSuccess(Map<String, List<Lecture>> schedule);
    }
}
