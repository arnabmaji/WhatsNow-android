package io.github.arnabmaji19.whatsnow.util;

import androidx.annotation.NonNull;

import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;
import java.util.List;

import io.github.arnabmaji19.whatsnow.model.ScheduleData;

public class UpdateManager {

    private static final String TAG = "UpdateManager";

    private DatabaseManager databaseManager;

    public UpdateManager(DatabaseManager databaseManager) {
        this.databaseManager = databaseManager;
    }

    public void fetchSchedulesList(final OnCompleteListener listener) {
        //get data from firebase
        databaseManager.getSchedulesListCollection()
                .addOnCompleteListener(new com.google.android.gms.tasks.OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                        List<ScheduleData> scheduleDataList = new ArrayList<>();
                        QuerySnapshot snapshots = task.getResult();
                        if (snapshots != null) {
                            for (QueryDocumentSnapshot snapshot : snapshots) {
                                scheduleDataList.add(snapshot.toObject(ScheduleData.class));
                            }
                            listener.onDataFetched(scheduleDataList);
                        }
                    }
                });
    }

    public interface OnCompleteListener {
        void onDataFetched(List<ScheduleData> scheduleDataList);
    }

    public interface onSuccessListener {
        void onSuccess();
    }
}
