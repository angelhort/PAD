package es.ucm.fdi.sacapalabra;

import androidx.appcompat.app.AppCompatActivity;

import android.content.SharedPreferences;
import android.database.Cursor;
import android.os.Bundle;
import android.util.Log;
import android.widget.ListView;
import android.widget.TextView;

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
        setTheme(theme);

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

        // Iterate through the cursor to get the game data
        while (cursor.moveToNext()) {
            String language = cursor.getString(cursor.getColumnIndexOrThrow(GameContract.GameEntry.COLUMN_NAME_LANGUAGE));
            String mode = cursor.getString(cursor.getColumnIndexOrThrow(GameContract.GameEntry.COLUMN_NAME_MODE));
            String word = cursor.getString(cursor.getColumnIndexOrThrow(GameContract.GameEntry.COLUMN_NAME_WORD));
            int tries = cursor.getInt(cursor.getColumnIndexOrThrow(GameContract.GameEntry.COLUMN_NAME_TRIES));
            int result = cursor.getInt(cursor.getColumnIndexOrThrow(GameContract.GameEntry.COLUMN_NAME_RESULT));
            Log.d("palabra", word);

            // Do something with the game data
        }

        cursor.close();
    }
    private void setTheme(String theme){
        if (theme.equals("dark")) {
            setTheme(R.style.Theme_Default);
        } else {
            setTheme(R.style.Theme_White);
        }
    }
}