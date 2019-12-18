package io.github.arnabmaji19.whatsnow;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Toast;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.preference.Preference;
import androidx.preference.PreferenceFragmentCompat;

import io.github.arnabmaji19.whatsnow.util.ConnectionManager;

public class SettingsActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_settings);
        //display settings fragment
        getSupportFragmentManager()
                .beginTransaction()
                .replace(R.id.settings, new SettingsFragment())
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
        }

        private void sync() { //Sync the currently used schedule
            ConnectionManager connectionManager = new ConnectionManager(getActivity());
            if (!connectionManager.isInternetConnectionAvailable()) {
                Toast.makeText(getActivity(), "Internet connection required!", Toast.LENGTH_SHORT).show();
                return;
            }
            //TODO:Display progress dialog and update the current schedule

        }
    }

}
