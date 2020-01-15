package io.github.arnabmaji19.whatsnow;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.preference.Preference;
import androidx.preference.PreferenceFragmentCompat;

import java.util.List;
import java.util.Map;

import io.github.arnabmaji19.whatsnow.model.Lecture;
import io.github.arnabmaji19.whatsnow.model.LocalScheduleData;
import io.github.arnabmaji19.whatsnow.util.ConnectionManager;
import io.github.arnabmaji19.whatsnow.util.DatabaseManager;
import io.github.arnabmaji19.whatsnow.util.LocalDataManager;
import io.github.arnabmaji19.whatsnow.util.UpdateManager;

public class SettingsActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_settings);
        //display settings fragment
        getSupportFragmentManager()
                .beginTransaction()
                .replace(R.id.settings, new SettingsFragment(SettingsActivity.this))
                .commit();

        //configure toolbar
        Toolbar toolbar = findViewById(R.id.settings_toolbar);
        setSupportActionBar(toolbar);
        ActionBar actionBar = getSupportActionBar();
        if (actionBar != null) {
            actionBar.setDisplayHomeAsUpEnabled(true);
        }

    }

    //Settings Fragment
    public static class SettingsFragment extends PreferenceFragmentCompat {

        private Activity activity;
        private UpdateManager updateManager;
        private LocalDataManager localDataManager;
        private AlertDialog dialog;
        private LocalScheduleData localScheduleData;
        private String scheduleId;

        public SettingsFragment(Activity activity) {
            this.activity = activity;
            this.updateManager = new UpdateManager(DatabaseManager.getInstance());
            this.localDataManager = new LocalDataManager(activity);
            this.localScheduleData = localDataManager.retrieveLocalScheduleData(); //Retrieve current LocalScheduleData
        }

        @Override
        public void onCreatePreferences(Bundle savedInstanceState, String rootKey) {
            setPreferencesFromResource(R.xml.settings_preferences, rootKey);

            Preference syncPref = findPreference("sync_pref_key");
            if (syncPref != null) {
                syncPref.setOnPreferenceClickListener(new Preference.OnPreferenceClickListener() {
                    @Override
                    public boolean onPreferenceClick(Preference preference) {
                        sync();
                        return true;
                    }
                });
            }

            Preference changePref = findPreference("change_pref_key");
            if (changePref != null)
                changePref.setOnPreferenceClickListener(new Preference.OnPreferenceClickListener() {
                    @Override
                    public boolean onPreferenceClick(Preference preference) {
                        startActivity(new Intent(getContext(), SetupActivity.class)); //display setup activity
                        return true;
                    }
                });

            Preference viewCurrentPref = findPreference("view_current_pref_key");
            if (viewCurrentPref != null) {
                viewCurrentPref.setOnPreferenceClickListener(new Preference.OnPreferenceClickListener() {
                    @Override
                    public boolean onPreferenceClick(Preference preference) {
                        //display current schedule details
                        final String details = "Batch: " + localScheduleData.getBatch() +
                                "\nDepartment: " + localScheduleData.getDepartment() +
                                "\nSection: " + localScheduleData.getSection() +
                                "\nSemester: " + localScheduleData.getSemester() +
                                "\nLast Updated: " + localScheduleData.getLastUpdated();
                        //display dialog containing all details about current schedule
                        AlertDialog.Builder builder = new AlertDialog.Builder(activity);
                        builder.setMessage(details);
                        builder.create().show();
                        return true;
                    }
                });
            }
        }

        private void sync() { //Sync the currently used schedule
            ConnectionManager connectionManager = new ConnectionManager(activity);
            if (!connectionManager.isInternetConnectionAvailable()) { //If internet is not available discard
                Toast.makeText(activity, "Internet connection required!", Toast.LENGTH_SHORT).show();
                return;
            }
            //Configure to display progress dialog
            ViewGroup viewGroup = activity.findViewById(android.R.id.content);
            final View dialogView = LayoutInflater.from(activity).inflate(R.layout.sync_dialog, viewGroup, false);
            AlertDialog.Builder builder = new AlertDialog.Builder(activity);
            builder.setView(dialogView)
                    .setCancelable(false);
            this.dialog = builder.create(); //Create dialog
            dialog.show(); //Display dialog
            this.scheduleId = localScheduleData.getScheduleId();
            updateManager.getLastUpdated(scheduleId, new UpdateManager.OnDataFetchedListener() { //Get LastUpdated for current schedule
                @Override
                public void onDataFetched(final String lastUpdated) {
                    if (lastUpdated.equals(localScheduleData.getLastUpdated())) { //If it's same, don't update
                        Toast.makeText(getContext(), "Nothing to sync!", Toast.LENGTH_SHORT).show();
                        dialog.dismiss(); //Hide dialog
                        return; //Don't have to make changes
                    }
                    //Update the LocalScheduleData
                    updateManager.fetchSchedule(scheduleId, new UpdateManager.OnSuccessListener() {
                        @Override
                        public void onSuccess(Map<String, List<Lecture>> schedule) {
                            //Make changes to Local Schedule Data
                            localScheduleData.setLastUpdated(lastUpdated);
                            localScheduleData.setFullSchedule(schedule);
                            //Save it on storage
                            localDataManager.saveLocalScheduleData(localScheduleData);
                            dialog.dismiss(); //Dismiss the Sync Dialog
                            Toast.makeText(activity, "Schedule updated as of " + lastUpdated, Toast.LENGTH_SHORT).show();
                        }
                    });
                }
            });
        }
    }
}
