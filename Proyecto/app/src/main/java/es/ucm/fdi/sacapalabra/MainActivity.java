package es.ucm.fdi.sacapalabra;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.res.Configuration;
import android.content.res.Resources;
import android.os.Bundle;
import android.text.Layout;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.Surface;
import android.view.View;
import android.widget.Button;

import java.util.Locale;

public class MainActivity extends AppCompatActivity {

    private Button button_info;
    private Button button_history;
    private Button button_config;
    private Button button_play;

    private SharedPreferences sharedPreferences;

    private View layout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        layout = findViewById(R.id.main_layout);

        // Recuperamos las preferencias
        sharedPreferences = getSharedPreferences("MyPrefs", MODE_PRIVATE);

        String theme = sharedPreferences.getString("theme", "dark");
        String language = sharedPreferences.getString("language", "es");
        Locale locale;

        // Tema
        int rotacion = getWindowManager().getDefaultDisplay().getRotation();
        if (rotacion == Surface.ROTATION_0 || rotacion == Surface.ROTATION_180) {
            //...hacer lo que quiera con la pantalla vertical
            if (theme.equals("dark")) {
                setTheme(R.style.Theme_Default);
                layout.setBackgroundResource(R.drawable.bg_dark);
            } else {
                setTheme(R.style.Theme_White);
                layout.setBackgroundResource(R.drawable.bg_white);
            }
        } else {
            //...hacer lo que quiera con la pantalla horizontal
            if (theme.equals("dark")) {
                setTheme(R.style.Theme_Default);
                layout.setBackgroundResource(R.drawable.bg_dark_h);
            } else {
                setTheme(R.style.Theme_White);
                layout.setBackgroundResource(R.drawable.bg_white_h);
            }
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

        button_info = findViewById(R.id.button4);
        button_history = findViewById(R.id.button3);
        button_config = findViewById(R.id.button);
        button_play = findViewById(R.id.button2);

        button_info.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, HowToPlayActivity.class);
                startActivity(intent);
            }
        });

        button_history.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, HistoryActivity.class);
                startActivity(intent);
            }
        });

        button_config.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, ConfigActivity.class);
                startActivity(intent);
            }
        });

        button_play.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, GameOptionsActivity.class);
                startActivity(intent);
            }
        });
    }
}