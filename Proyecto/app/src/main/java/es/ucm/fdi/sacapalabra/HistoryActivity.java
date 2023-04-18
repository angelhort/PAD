package es.ucm.fdi.sacapalabra;

import androidx.appcompat.app.AppCompatActivity;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.widget.ListView;
import android.widget.TextView;

import java.util.Locale;

public class HistoryActivity extends AppCompatActivity {

    private SharedPreferences sharedPreferences;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_history);
        assignButtons();

        // Recuperamos las preferencias
        sharedPreferences = getSharedPreferences("MyPrefs", MODE_PRIVATE);
        String theme = sharedPreferences.getString("theme", "dark");
        setTheme(theme);
    }


    private void assignButtons(){
        ListView historial = findViewById(R.id.lvHistorialPartidas);
        TextView textRachaVictorias = findViewById(R.id.textViewRachaActual);
        TextView textMejorRachaVictorias = findViewById(R.id.textViewMejorRacha);
        TextView textPartidas = findViewById(R.id.textViewPartidasJugadas);
        TextView textVictoriasPorcentaje = findViewById(R.id.textViewVictorias);
    }
    private void setTheme(String theme){
        if (theme.equals("dark")) {
            setTheme(R.style.Theme_Default);
        } else {
            setTheme(R.style.Theme_White);
        }
    }
}