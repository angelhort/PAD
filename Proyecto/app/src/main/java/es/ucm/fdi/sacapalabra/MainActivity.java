package es.ucm.fdi.sacapalabra;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.res.Configuration;
import android.content.res.Resources;
import android.os.Bundle;
import android.text.Layout;
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

        // Tema
        if (theme.equals("dark")) {
            setTheme(R.style.Theme_Default);
            layout.setBackgroundResource(R.drawable.bg_dark);
        } else {
            setTheme(R.style.Theme_White);
            layout.setBackgroundResource(R.drawable.bg_white);
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