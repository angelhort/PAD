package es.ucm.fdi.sacapalabra;

import android.content.Context;
import android.content.SharedPreferences;
import android.content.res.Configuration;
import android.content.res.Resources;
import android.database.Cursor;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.BaseAdapter;
import android.widget.ListView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

public class HistoryActivity extends BaseActivity {

    private SharedPreferences sharedPreferences;

    private ListView historial;
    private TextView textRachaVictorias;
    private TextView textMejorRachaVictorias;
    private TextView textPartidas;
    private TextView textVictoriasPorcentaje;

    private GameDBHelper dbHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        // Recuperamos las preferencias
        sharedPreferences = getSharedPreferences("MyPrefs", MODE_PRIVATE);
        String theme = sharedPreferences.getString("theme", "dark");
        String language = sharedPreferences.getString("language", "es");

        setTheme(theme);
        setLanguage(language);

        setContentView(R.layout.activity_history);
        assignButtons();

        setStatistics();

        // Get a reference to the instance of DataBase
        dbHelper = DataBase.getDbHelper(this.getApplicationContext());
        displayGameHistory();

    }

    private void setStatistics(){
        int currentStreak = sharedPreferences.getInt("currentStreak", 0);
        int bestStreak = sharedPreferences.getInt("bestStreak", 0);
        float percentage = sharedPreferences.getFloat("percentage", 0);
        String formattedPercentage = String.format("%.1f", percentage);
        int plays = sharedPreferences.getInt("plays", 0);

        textPartidas.setText(String.valueOf(plays));
        textVictoriasPorcentaje.setText(formattedPercentage+"%");
        textMejorRachaVictorias.setText(String.valueOf(bestStreak));
        textRachaVictorias.setText(String.valueOf(currentStreak));
    }

    private void assignButtons(){
        historial = findViewById(R.id.lvHistorialPartidas);
        textRachaVictorias = findViewById(R.id.textViewRachaActual);
        textMejorRachaVictorias = findViewById(R.id.textViewMejorRacha);
        textPartidas = findViewById(R.id.textViewPartidasJugadas);
        textVictoriasPorcentaje = findViewById(R.id.textViewVictorias);
    }

    private void displayGameHistory() {
        Cursor cursor = dbHelper.getAllGames();
        ArrayList<MyData> lista = new ArrayList<MyData>();

        // Iterate through the cursor to get the game data
        while (cursor.moveToNext()) {
            String language = cursor.getString(cursor.getColumnIndexOrThrow(GameContract.GameEntry.COLUMN_NAME_LANGUAGE));
            String mode = cursor.getString(cursor.getColumnIndexOrThrow(GameContract.GameEntry.COLUMN_NAME_MODE));
            String word = cursor.getString(cursor.getColumnIndexOrThrow(GameContract.GameEntry.COLUMN_NAME_WORD));
            int tries = cursor.getInt(cursor.getColumnIndexOrThrow(GameContract.GameEntry.COLUMN_NAME_TRIES));
            int result = cursor.getInt(cursor.getColumnIndexOrThrow(GameContract.GameEntry.COLUMN_NAME_RESULT));

            language = (language.substring(0, 1).toUpperCase() + language.substring(1)).trim();
            mode = (mode.substring(0, 1).toUpperCase() + mode.substring(1)).trim();
            word = (word.substring(0, 1).toUpperCase() + word.substring(1)).trim();
           
            lista.add(new MyData(language, mode, word, tries));
            Log.d("PalabraHistorial", word);
        }
        cursor.close();
        HistoryAdapter adapter = new HistoryAdapter(this, lista);
        historial.setAdapter(adapter);
        adapter.notifyDataSetChanged();
    }
    private void setTheme(String theme){
        if (theme.equals("dark")) {
            setTheme(R.style.Theme_Default);
        } else {
            setTheme(R.style.Theme_White);
        }
    }

    private void setLanguage(String language) {
        Locale locale = new Locale(language);
        Resources res = getResources();
        DisplayMetrics dm = res.getDisplayMetrics();
        Configuration conf = res.getConfiguration();
        conf.locale = locale;
        res.updateConfiguration(conf, dm);
    }
    public class HistoryAdapter extends BaseAdapter {
        private List<MyData> mDataList;
        private LayoutInflater mInflater;

        public HistoryAdapter(Context context, List<MyData> dataList) {
            mDataList = dataList;
            mInflater = LayoutInflater.from(context);
        }

        @Override
        public int getCount() {
            return mDataList.size();
        }

        @Override
        public Object getItem(int position) {
            return mDataList.get(position);
        }

        @Override
        public long getItemId(int position) {
            return position;
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            ViewHolder holder;

            if (convertView == null) {
                convertView = mInflater.inflate(R.layout.activity_history_items, null);

                holder = new ViewHolder();
                holder.languageTextView = convertView.findViewById(R.id.textViewIdioma);
                holder.modeTextView = convertView.findViewById(R.id.textViewModo);
                holder.wordTextView = convertView.findViewById(R.id.textViewPalabra);
                holder.triesTextView = convertView.findViewById(R.id.textViewIntentos);

                convertView.setTag(holder);
            } else {
                holder = (ViewHolder) convertView.getTag();
            }

            MyData data = mDataList.get(position);

            holder.languageTextView.setText(data.language);
            holder.modeTextView.setText(data.mode);
            holder.wordTextView.setText(data.word);
            holder.triesTextView.setText(String.valueOf(data.tries));

            return convertView;
        }

        private class ViewHolder {
            TextView languageTextView;
            TextView modeTextView;
            TextView wordTextView;
            TextView triesTextView;
        }
    }
    public class MyData {
        public String language;
        public String mode;
        public String word;
        public int tries;

        public MyData(String language, String mode, String word, int tries) {
            this.language = language;
            this.mode = mode;
            this.word = word;
            this.tries = tries;
        }
    }
}