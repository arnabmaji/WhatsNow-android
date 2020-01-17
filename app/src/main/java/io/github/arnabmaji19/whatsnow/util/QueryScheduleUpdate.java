package io.github.arnabmaji19.whatsnow.util;

import android.content.Context;
import android.widget.Toast;

import io.github.arnabmaji19.whatsnow.model.LocalScheduleData;

public class QueryScheduleUpdate implements Runnable {

    //Looks for any update for currently used Schedule

    private Context context;
    private UpdateManager updateManager;
    private LocalScheduleData localScheduleData;

    public QueryScheduleUpdate(Context context, UpdateManager updateManager, LocalScheduleData localScheduleData) {
        this.context = context;
        this.updateManager = updateManager;
        this.localScheduleData = localScheduleData;
    }

    @Override
    public void run() {
        updateManager.getLastUpdated(localScheduleData.getScheduleId(), new UpdateManager.OnDataFetchedListener() {
            @Override
            public void onDataFetched(String lastUpdated) {
                if (!localScheduleData.getLastUpdated().equals(lastUpdated)) { //If new update is available
                    //Notify user to Sync the currently used schedule
                    Toast.makeText(context, "A schedule update is available", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }
}
