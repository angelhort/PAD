package es.ucm.fdi.sacapalabra;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.ListView;
import android.widget.TextView;

public class HistoryActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_history);

        ListView historial = findViewById(R.id.lvHistorialPartidas);
        TextView textRachaVictorias = findViewById(R.id.textViewRachaActual);
        TextView textMejorRachaVictorias = findViewById(R.id.textViewMejorRacha);
        TextView textPartidas = findViewById(R.id.textViewPartidasJugadas);
        TextView textVictoriasPorcentaje = findViewById(R.id.textViewVictorias);
    }
}