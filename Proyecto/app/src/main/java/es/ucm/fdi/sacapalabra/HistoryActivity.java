package es.ucm.fdi.sacapalabra;

import androidx.appcompat.app.AppCompatActivity;

import android.content.SharedPreferences;
import android.content.res.Configuration;
import android.content.res.Resources;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.View;
import android.widget.ListView;
import android.widget.TextView;

import java.util.Locale;

public class HistoryActivity extends AppCompatActivity {

    private View layout;

    private SharedPreferences sharedPreferences;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_history);
        layout = findViewById(R.id.history_layout);

        // Recuperamos las preferencias
        sharedPreferences = getSharedPreferences("MyPrefs", MODE_PRIVATE);

        String theme = sharedPreferences.getString("theme", "dark");
        String language = sharedPreferences.getString("language", "es");
        Locale locale;

        // Tema
        if (theme.equals("dark")) {
            setTheme(R.style.Theme_Default);
            layout.setBackgroundResource(R.drawable.bg_dark);
        } else {
            setTheme(R.style.Theme_White);
            layout.setBackgroundResource(R.drawable.bg_white);
        }

        // Idioma
        if (language.equals("es")) {
            locale = new Locale("es");
            Resources res = getResources();
            DisplayMetrics dm = res.getDisplayMetrics();
            Configuration conf = res.getConfiguration();
            conf.locale = locale;
            res.updateConfiguration(conf, dm);
            Log.d("miaaaau","recreate");

        } else {
            locale = new Locale("en");
            Resources res = getResources();
            DisplayMetrics dm = res.getDisplayMetrics();
            Configuration conf = res.getConfiguration();
            conf.locale = locale;
            res.updateConfiguration(conf, dm);
            Log.d("miau","recreate");
        }

        ListView historial = findViewById(R.id.lvHistorialPartidas);
        TextView textRachaVictorias = findViewById(R.id.textViewRachaActual);
        TextView textMejorRachaVictorias = findViewById(R.id.textViewMejorRacha);
        TextView textPartidas = findViewById(R.id.textViewPartidasJugadas);
        TextView textVictoriasPorcentaje = findViewById(R.id.textViewVictorias);
    }
}