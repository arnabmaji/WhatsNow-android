package io.github.arnabmaji19.whatsnow;

import android.os.Bundle;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

import io.github.arnabmaji19.whatsnow.model.ScheduleData;
import io.github.arnabmaji19.whatsnow.util.ConnectionManager;
import io.github.arnabmaji19.whatsnow.util.DatabaseManager;
import io.github.arnabmaji19.whatsnow.util.ScheduleListAdapter;
import io.github.arnabmaji19.whatsnow.util.UpdateManager;

public class SetupActivity extends AppCompatActivity {

    private static final String TAG = "SetupActivity";

    private ConstraintLayout scheduleListLayout;
    private LinearLayout connectionErrorLayout;
    private LinearLayout loadingLayout;

    private RecyclerView scheduleListRecyclerView;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_setup);

        //Linking Views
        scheduleListLayout = findViewById(R.id.scheduleListLayout);
        connectionErrorLayout = findViewById(R.id.connectionErrorLayout);
        loadingLayout = findViewById(R.id.loadingLayout);
        scheduleListRecyclerView = findViewById(R.id.scheduleListRecyclerView);
        scheduleListRecyclerView.setLayoutManager(new LinearLayoutManager(this));

        ConnectionManager connectionManager = new ConnectionManager(SetupActivity.this);
        if (!connectionManager.isInternetConnectionAvailable()) {
            //show connection error layout and hide others
            connectionErrorLayout.setVisibility(View.VISIBLE);
            return;
        }
        showAvailableSchedules();
    }


    private void showAvailableSchedules() {
        loadingLayout.setVisibility(View.VISIBLE); //display loading animation to user

        UpdateManager manager = new UpdateManager(DatabaseManager.getInstance());
        manager.fetchSchedulesList(new UpdateManager.OnCompleteListener() {
            @Override
            public void onDataFetched(List<ScheduleData> scheduleDataList) {
                loadingLayout.setVisibility(View.GONE); //Hide loading animation
                scheduleListLayout.setVisibility(View.VISIBLE); //show main layout
                populateRecyclerView(scheduleDataList);
            }
        });
    }

    private void populateRecyclerView(List<ScheduleData> scheduleDataList) {
        ScheduleListAdapter scheduleListAdapter = new ScheduleListAdapter(scheduleDataList, new ScheduleListAdapter.OnItemClickListener() {
            @Override
            public void onItemClicked(ScheduleData data) {
                Toast.makeText(SetupActivity.this, data.getSection(), Toast.LENGTH_SHORT).show();
            }
        });
        scheduleListRecyclerView.setAdapter(scheduleListAdapter);
    }
}
