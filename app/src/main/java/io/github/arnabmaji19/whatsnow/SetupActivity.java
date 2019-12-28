package io.github.arnabmaji19.whatsnow;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;
import java.util.Map;

import io.github.arnabmaji19.whatsnow.model.Lecture;
import io.github.arnabmaji19.whatsnow.model.LocalScheduleData;
import io.github.arnabmaji19.whatsnow.model.ScheduleData;
import io.github.arnabmaji19.whatsnow.util.ConnectionManager;
import io.github.arnabmaji19.whatsnow.util.DatabaseManager;
import io.github.arnabmaji19.whatsnow.util.LocalDataManager;
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
        //add line divider in recycler view
        scheduleListRecyclerView.addItemDecoration(new DividerItemDecoration(scheduleListRecyclerView.getContext(), DividerItemDecoration.VERTICAL));

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
            public void onComplete(List<ScheduleData> scheduleDataList) {
                loadingLayout.setVisibility(View.GONE); //Hide loading animation
                scheduleListLayout.setVisibility(View.VISIBLE); //show main layout
                populateRecyclerView(scheduleDataList);
            }
        });
    }

    private void populateRecyclerView(List<ScheduleData> scheduleDataList) {
        ScheduleListAdapter scheduleListAdapter = new ScheduleListAdapter(scheduleDataList, new ScheduleListAdapter.OnItemClickListener() {
            @Override
            public void onItemClicked(final ScheduleData data) {
                String message = "Department: " + data.getDepartment() + "\n"
                        + "Batch: " + data.getBatch() + "\n"
                        + "Section: " + data.getSection();
                //Display confirmation dialog
                new AlertDialog.Builder(SetupActivity.this)
                        .setTitle("Please verify your Schedule")
                        .setMessage(message)
                        .setNegativeButton(android.R.string.no, null)
                        .setPositiveButton(android.R.string.yes, new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {
                                //Show Loading Layout and save the schedule data on mobile
                                scheduleListLayout.setVisibility(View.GONE);
                                TextView message = findViewById(R.id.loadingMessageTextView);
                                message.setText(R.string.setup_message);
                                loadingLayout.setVisibility(View.VISIBLE);
                                //Get Schedule and save on local storage
                                getAndSaveScheduleOnLocalStorage(data);
                            }
                        }).show();
            }
        });
        scheduleListRecyclerView.setAdapter(scheduleListAdapter);
    }

    private void getAndSaveScheduleOnLocalStorage(final ScheduleData data) {
        UpdateManager updateManager = new UpdateManager(DatabaseManager.getInstance());
        updateManager.fetchSchedule(data.getScheduleId(), new UpdateManager.OnSuccessListener() {
            @Override
            public void onSuccess(Map<String, List<Lecture>> schedule) {
                //Create data to save on local storage
                LocalScheduleData localScheduleData = new LocalScheduleData(
                        data.getDepartment(),
                        data.getBatch(),
                        data.getSection(),
                        data.getSemester(),
                        data.getScheduleId(),
                        data.getLastUpdated(),
                        schedule
                );
                LocalDataManager localDataManager = new LocalDataManager(SetupActivity.this);
                localDataManager.saveLocalScheduleData(localScheduleData);
                loadingLayout.setVisibility(View.GONE);
                //Show complete dialog
                new AlertDialog.Builder(SetupActivity.this)
                        .setMessage(R.string.success_message)
                        .setCancelable(false)
                        .setPositiveButton(R.string.exit, new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {
                                //Close the app
                                finish();
                            }
                        }).show();
            }
        });
    }

    public void restartActivity(View view) {
        /*
         * When the user clicks try again button upon restoration of internet connection,
         * Restart the same activity
         */

        Intent intent = getIntent(); //get current intent
        finish(); //finish the current instance of the activity
        startActivity(intent); //restart the current activity
    }
}
