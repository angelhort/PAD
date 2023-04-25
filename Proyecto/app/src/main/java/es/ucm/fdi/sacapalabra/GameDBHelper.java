package es.ucm.fdi.sacapalabra;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

public class GameDBHelper extends SQLiteOpenHelper {

    private static final String DATABASE_NAME = "game.db";
    private static final int DATABASE_VERSION = 1;

    public GameDBHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
        Log.d("miau", "dceie");
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String SQL_CREATE_GAME_TABLE = "CREATE TABLE " + GameContract.GameEntry.TABLE_NAME + " ("
                + GameContract.GameEntry._ID + " INTEGER PRIMARY KEY AUTOINCREMENT, "
                + GameContract.GameEntry.COLUMN_NAME_WORD + " TEXT NOT NULL, "
                + GameContract.GameEntry.COLUMN_NAME_LANGUAGE + " TEXT NOT NULL, "
                + GameContract.GameEntry.COLUMN_NAME_MODE + " TEXT NOT NULL, "
                + GameContract.GameEntry.COLUMN_NAME_TRIES + " TEXT NOT NULL, "
                + GameContract.GameEntry.COLUMN_NAME_RESULT + " INTEGER NOT NULL);";

        db.execSQL(SQL_CREATE_GAME_TABLE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        // This database is only a cache for online data, so its upgrade policy is
        // to simply to discard the data and start over
        db.execSQL("DROP TABLE IF EXISTS " + GameContract.GameEntry.TABLE_NAME);
        onCreate(db);
    }

    public void insertGame(String language, String mode, String word, int result, int tries) {
        SQLiteDatabase db = getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put(GameContract.GameEntry.COLUMN_NAME_LANGUAGE, language);
        values.put(GameContract.GameEntry.COLUMN_NAME_MODE, mode);
        values.put(GameContract.GameEntry.COLUMN_NAME_WORD, word);
        values.put(GameContract.GameEntry.COLUMN_NAME_RESULT, result);
        values.put(GameContract.GameEntry.COLUMN_NAME_TRIES, tries);

        db.insert(GameContract.GameEntry.TABLE_NAME, null, values);
    }

    public Cursor getAllGames() {
        SQLiteDatabase db = getReadableDatabase();

        String[] projection = {
                GameContract.GameEntry._ID,
                GameContract.GameEntry.COLUMN_NAME_WORD,
                GameContract.GameEntry.COLUMN_NAME_LANGUAGE,
                GameContract.GameEntry.COLUMN_NAME_MODE,
                GameContract.GameEntry.COLUMN_NAME_TRIES,
                GameContract.GameEntry.COLUMN_NAME_RESULT
        };

        return db.query(
                GameContract.GameEntry.TABLE_NAME,
                projection,
                null,
                null,
                null,
                null,
                null
        );
    }
}
