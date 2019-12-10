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

import io.github.arnabmaji19.whatsnow.manager.DateTimeManager;
import io.github.arnabmaji19.whatsnow.manager.LocalDataManager;
import io.github.arnabmaji19.whatsnow.manager.ScheduleManager;
import io.github.arnabmaji19.whatsnow.model.Lecture;
import io.github.arnabmaji19.whatsnow.model.LocalScheduleData;

public class MainActivity extends AppCompatActivity {

    private static final String TAG = "MainActivity";

    private NavigationView navigationView;
    private DrawerLayout drawerLayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //Creates Navigation Drawer
        setUpNavigationDrawer();

        //TODO: get json from database, use it or save on local storage
        //Code for testing purposes
        LocalDataManager localDataManager = new LocalDataManager(this);
        LocalScheduleData localScheduleData = localDataManager.retrieveLocalScheduleData();
        final DateTimeManager dateTimeManager = new DateTimeManager();
        int currentLectureNo = dateTimeManager.getCurrentLectureNo();
        ScheduleManager scheduleManager = new ScheduleManager(localScheduleData.getFullSchedule(),
                currentLectureNo,
                dateTimeManager.getTodayAsString());
        final Lecture currentLecture = scheduleManager.getOnGoingLecture();
        final Lecture nextLecture = scheduleManager.getUpcomingLecture();

        //---END of code for testing purposes--------------------------

        //Setting up navigation view and item selected listener
        navigationView = findViewById(R.id.navigation_view);
        navigationView.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {
                Fragment selectedFragment = null;

                switch (menuItem.getItemId()) {
                    case R.id.dashboard:
                        selectedFragment = new DashboardFragment(currentLecture, nextLecture, dateTimeManager.getElapsedTimeFraction());
                        break;

                    case R.id.today_schedule:
                        selectedFragment = new TodaysScheduleFragment();
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

        //Showing Dashboard fragment as default fragment
        setFragment(new DashboardFragment(currentLecture, nextLecture, dateTimeManager.getElapsedTimeFraction()));
        navigationView.setCheckedItem(R.id.dashboard);
    }

    private void setUpNavigationDrawer() {
        drawerLayout = findViewById(R.id.drawer_layout);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(this, drawerLayout, toolbar, R.string.open, R.string.close);
        drawerLayout.addDrawerListener(toggle);
        toggle.syncState();
    }

    private void startNewActivity(Activity activity) {
        startActivity(new Intent(MainActivity.this, activity.getClass()));
    }

    //sets the selected fragment in main activity's frame layout
    private void setFragment(Fragment fragment) {
        getSupportFragmentManager()
                .beginTransaction()
                .replace(R.id.frame_layout, fragment)
                .commit();
    }
}
