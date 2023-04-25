package es.ucm.fdi.sacapalabra;

import android.app.Application;
import android.util.Log;

public class DataBase extends Application {
    private static GameDBHelper dbHelper;

    @Override
    public void onCreate() {
        super.onCreate();
        dbHelper = new GameDBHelper(getApplicationContext());
        Log.d("DataBase", "onCreate() called");
    }

    public static GameDBHelper getDbHelper() {
        return dbHelper;
    }
}