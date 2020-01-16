package io.github.arnabmaji19.whatsnow;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.fragment.app.Fragment;

import com.google.android.material.navigation.NavigationView;

import io.github.arnabmaji19.whatsnow.model.Lecture;
import io.github.arnabmaji19.whatsnow.model.LocalScheduleData;
import io.github.arnabmaji19.whatsnow.util.DateTimeManager;
import io.github.arnabmaji19.whatsnow.util.LocalDataManager;
import io.github.arnabmaji19.whatsnow.util.ScheduleManager;

public class MainActivity extends AppCompatActivity {

    private static final String TAG = "MainActivity";

    private NavigationView navigationView;
    private DrawerLayout drawerLayout;
    private LocalScheduleData localScheduleData;
    private DateTimeManager dateTimeManager;
    private ScheduleManager scheduleManager;
    private Lecture currentLecture;
    private Lecture nextLecture;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //Create Navigation Drawer
        setUpNavigationDrawer();

        LocalDataManager localDataManager = new LocalDataManager(this);
        if (!localDataManager.isLocalDataAvailable()) { //Check if local data is available
            startNewActivity(new SetupActivity()); //If not available get from database from SetUpActivity
            finish(); //finish the activity, cause you need to restart activity after saving local data
            return;
        }
        //Get local data from local storage
        localScheduleData = localDataManager.retrieveLocalScheduleData();
        dateTimeManager = new DateTimeManager();
        scheduleManager = new ScheduleManager(dateTimeManager, localScheduleData.getFullSchedule());
        currentLecture = scheduleManager.getOnGoingLecture();
        nextLecture = scheduleManager.getUpcomingLecture();


        //Set up navigation view and item selected listener
        navigationView = findViewById(R.id.navigation_view);
        navigationView.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {
                Fragment selectedFragment = null;

                switch (menuItem.getItemId()) {
                    case R.id.dashboard:
                        selectedFragment = new DashboardFragment(currentLecture,
                                nextLecture,
                                dateTimeManager.getElapsedTimeFraction());
                        break;

                    case R.id.today_schedule:
                        selectedFragment = new TodaysScheduleFragment(MainActivity.this,
                                scheduleManager,
                                dateTimeManager);
                        break;

                    case R.id.settings:
                        startNewActivity(new SettingsActivity());
                        break;

                    case R.id.support:
                        startNewActivity(new SupportActivity());
                        break;

                    case R.id.about:
                        startNewActivity(new AboutActivity());
                        break;
                }

                if (selectedFragment != null) {
                    setFragment(selectedFragment);
                    drawerLayout.closeDrawer(GravityCompat.START);
                    return true;
                }
                return false;
            }
        });

        //Show Dashboard fragment as default fragment
        setFragment(new DashboardFragment(currentLecture,
                nextLecture,
                dateTimeManager.getElapsedTimeFraction()));
        navigationView.setCheckedItem(R.id.dashboard);
    }

    private void setUpNavigationDrawer() {
        drawerLayout = findViewById(R.id.drawer_layout);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(this,
                drawerLayout,
                toolbar, R.string.open, R.string.close);
        drawerLayout.addDrawerListener(toggle);
        toggle.syncState();
    }

    private void startNewActivity(Activity activity) {
        startActivity(new Intent(MainActivity.this, activity.getClass()));
    }

    //set the selected fragment in main activity's frame layout
    private void setFragment(Fragment fragment) {
        getSupportFragmentManager()
                .beginTransaction()
                .replace(R.id.frame_layout, fragment)
                .commit();
    }
}
