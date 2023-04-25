package es.ucm.fdi.sacapalabra;

import android.app.Application;
import android.content.Context;
import android.util.Log;

public class DataBase extends Application {
    private static GameDBHelper dbHelper;

    public static GameDBHelper getDbHelper(Context context) {
        if(dbHelper == null){
            dbHelper = new GameDBHelper(context);
        }
        return dbHelper;
    }
}