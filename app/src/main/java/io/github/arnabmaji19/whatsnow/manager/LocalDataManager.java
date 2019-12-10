package io.github.arnabmaji19.whatsnow.manager;

//Saves and retrieves data locally on mobile

import android.app.Activity;
import android.util.Log;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;

import io.github.arnabmaji19.whatsnow.model.LocalScheduleData;

public class LocalDataManager {

    private static final String FILENAME_WITH_EXTENSION = "local_schedule_data.sm";
    private static final String TAG = "LocalDataManager";

    private File file;

    public LocalDataManager(Activity activity) {
        String path = activity.getFilesDir().getAbsolutePath();
        String fileWithPath = path + "/" + FILENAME_WITH_EXTENSION;
        file = new File(fileWithPath);
    }

    //Serialize LocalScheduleData object in files directory
    public void saveLocalScheduleData(LocalScheduleData localScheduleData) {
        try (ObjectOutputStream stream = new ObjectOutputStream(new BufferedOutputStream(new FileOutputStream(file)))) {
            stream.writeObject(localScheduleData);
        } catch (IOException e) {
            Log.e(TAG, "saveLocalScheduleData: Failed to save local schedule data" + e.getMessage());
        }
    }

    //Deserialize LocalScheduleData object from files directory and return
    public LocalScheduleData retrieveLocalScheduleData() {
        LocalScheduleData localScheduleData = null;
        try (ObjectInputStream stream = new ObjectInputStream(new BufferedInputStream(new FileInputStream(file)))) {
            localScheduleData = (LocalScheduleData) stream.readObject();
        } catch (IOException | ClassNotFoundException e) {
            Log.e(TAG, "retrieveLocalScheduleData: Failed to retrieve local schedule data" + e.getMessage());
        }
        return localScheduleData;
    }
}
