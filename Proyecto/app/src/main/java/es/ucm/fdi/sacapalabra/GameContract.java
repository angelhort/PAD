package es.ucm.fdi.sacapalabra;

import android.provider.BaseColumns;

public final class GameContract {

    private GameContract() {}

    public static class GameEntry implements BaseColumns {
        public static final String TABLE_NAME = "game";
        public static final String COLUMN_NAME_WORD = "word";
        public static final String COLUMN_NAME_TRIES = "tries";
        public static final String COLUMN_NAME_MODE = "mode";
        public static final String COLUMN_NAME_LANGUAGE = "language";
        public static final String COLUMN_NAME_RESULT = "result";
    }
}