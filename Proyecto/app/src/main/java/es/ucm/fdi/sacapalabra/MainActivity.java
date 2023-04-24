package es.ucm.fdi.sacapalabra;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.content.res.Configuration;
import android.content.res.Resources;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.View;
import android.widget.Button;

import java.util.Locale;

public class MainActivity extends BaseActivity {

    private Button button_info;
    private Button button_history;
    private Button button_config;
    private Button button_play;

    private SharedPreferences sharedPreferences;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);

        // Recuperamos las preferencias
        sharedPreferences = getSharedPreferences("MyPrefs", MODE_PRIVATE);

        String theme = sharedPreferences.getString("theme", "dark");
        String language = sharedPreferences.getString("language", "es");

        setTheme(theme);
        setLanguage(language);

        setContentView(R.layout.activity_main);
        assignButtons();
    }

    View.OnClickListener bhtpListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            Intent intent = new Intent(MainActivity.this, HowToPlayActivity.class);
            startActivity(intent);

        }
    };
    View.OnClickListener bHistoryListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            Intent intent = new Intent(MainActivity.this, HistoryActivity.class);
            startActivity(intent);
        }
    };
    View.OnClickListener bConfigListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            Intent intent = new Intent(MainActivity.this, ConfigActivity.class);
            startActivity(intent);
            finish();
        }
    };
    View.OnClickListener bPlayListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            Intent intent = new Intent(MainActivity.this, GameOptionsActivity.class);
            Log.i("MainActivity", "Pulsado play");
            startActivity(intent);
        }
    };

    private void assignButtons(){

        button_info = findViewById(R.id.button4);
        button_history = findViewById(R.id.button3);
        button_config = findViewById(R.id.button);
        button_play = findViewById(R.id.button2);

        button_info.setOnClickListener(bhtpListener);
        button_history.setOnClickListener(bHistoryListener);
        button_config.setOnClickListener(bConfigListener);
        button_play.setOnClickListener(bPlayListener);
    }
    private void setTheme(String theme){
        if (theme.equals("dark")) {
            setTheme(R.style.Theme_Default);
        } else {
            setTheme(R.style.Theme_White);
        }
    }
    private void setLanguage(String language){
        Locale locale = new Locale(language);
        Resources res = getResources();
        DisplayMetrics dm = res.getDisplayMetrics();
        Configuration conf = res.getConfiguration();
        conf.locale = locale;
        res.updateConfiguration(conf, dm);
    }

}