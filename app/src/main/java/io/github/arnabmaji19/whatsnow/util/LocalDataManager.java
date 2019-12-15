package io.github.arnabmaji19.whatsnow.util;

//Saves and retrieves data locally on mobile

import android.app.Activity;
import android.util.Log;

import com.google.gson.Gson;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;

import io.github.arnabmaji19.whatsnow.model.LocalScheduleData;

public class LocalDataManager {

    private static final String FILENAME_WITH_EXTENSION = "local_schedule_data.sm";
    private static final String TAG = "LocalDataManager";
    private Gson gson;

    private File file;

    public LocalDataManager(Activity activity) {
        file = new File(activity.getFilesDir(), FILENAME_WITH_EXTENSION);
        this.gson = new Gson();
    }

    //Convert LocalScheduleData object into Json String and save as text file
    public void saveLocalScheduleData(LocalScheduleData localScheduleData) {
        String objectJson = gson.toJson(localScheduleData);
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(file))) {
            writer.write(objectJson);
        } catch (IOException e) {
            Log.e(TAG, "saveLocalScheduleData: Failed to save local schedule data" + e.getMessage());
        }
    }

    //Convert json string to LocalScheduleData object from files directory
    public LocalScheduleData retrieveLocalScheduleData() {
        LocalScheduleData localScheduleData = null;
        try (BufferedReader reader = new BufferedReader(new FileReader(file))) {
            StringBuilder builder = new StringBuilder();
            String line;
            while ((line = reader.readLine()) != null) {
                builder.append(line);
            }
            localScheduleData = gson.fromJson(builder.toString(), LocalScheduleData.class);
        } catch (IOException e) {
            Log.e(TAG, "retrieveLocalScheduleData: Failed to retrieve local schedule data" + e.getMessage());
        }
        return localScheduleData;
    }

    public boolean isLocalDataAvailable() {
        return file.exists();
    }
}
