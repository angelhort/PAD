package es.ucm.fdi.sacapalabra;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;

import android.content.Context;
import android.content.SharedPreferences;
import android.content.res.Configuration;
import android.content.res.Resources;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.SeekBar;
import android.widget.Switch;
import android.widget.ToggleButton;

import java.util.Locale;

public class ConfigActivity extends AppCompatActivity {

    private ToggleButton bSpanish;
    private ToggleButton bEnglish;

    private ToggleButton bDarkTheme;
    private ToggleButton bWhiteTheme;

    private Button confirm_button;
    private Switch dswitch;
    private SeekBar fontBar;

    private SharedPreferences sharedPreferences;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        sharedPreferences = getSharedPreferences("MyPrefs", MODE_PRIVATE);

        String language = sharedPreferences.getString("language", "es");
        String theme = sharedPreferences.getString("theme", "dark");

        // Utilizar la preferencia en tu código
        if (language.equals("es")) {

        } else {

        }

        // Utilizar la preferencia en tu código
        if (theme.equals("dark")) {

        } else {
            setTheme(R.style.Theme_White);
        }

        super.onCreate(savedInstanceState);
        setTheme(R.style.Theme_White);
        setContentView(R.layout.activity_config);

        bSpanish = findViewById(R.id.config_language_op1);
        bEnglish = findViewById(R.id.config_language_op2);
        bDarkTheme = findViewById(R.id.config_theme_op1);
        bWhiteTheme = findViewById(R.id.config_theme_op2);

        bSpanish.setOnCheckedChangeListener(bSpanishListener);
        bEnglish.setOnCheckedChangeListener(bEnglishListener);
        bDarkTheme.setOnCheckedChangeListener(bDarkThemeListener);
        bWhiteTheme.setOnCheckedChangeListener(bWhiteThemeListener);

        // Utilizar la preferencia en tu código
        if (language.equals("es")) {
            bEnglish.setChecked(false);
            bSpanish.setChecked(true);
            bEnglish.setClickable(true);
            bSpanish.setClickable(false);
        } else {
            bEnglish.setChecked(true);
            bSpanish.setChecked(false);
            bEnglish.setClickable(false);
            bSpanish.setClickable(true);
        }

        // Utilizar la preferencia en tu código
        if (theme.equals("dark")) {
            bWhiteTheme.setChecked(false);
            bDarkTheme.setChecked(true);
            bWhiteTheme.setClickable(true);
            bDarkTheme.setClickable(false);
        } else {
            bWhiteTheme.setChecked(true);
            bDarkTheme.setChecked(false);
            bWhiteTheme.setClickable(false);
            bDarkTheme.setClickable(true);

            setTheme(R.style.Theme_White);
        }

        if (savedInstanceState != null) {
            int languageSelected = savedInstanceState.getInt("language");

            switch (languageSelected) {
                case 0:                     // Idioma en español
                    bEnglish.setChecked(false);
                    bSpanish.setChecked(true);
                    bEnglish.setClickable(true);
                    bSpanish.setClickable(false);
                    break;
                case 1:                     // Idioma en ingles
                    bEnglish.setChecked(true);
                    bSpanish.setChecked(false);
                    bEnglish.setClickable(false);
                    bSpanish.setClickable(true);
                    break;
                default:
                    break;
            }
        }
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);

        if(bSpanish.isChecked()) {
            outState.putInt("language", 0);
        } else {
            outState.putInt("language", 1);
        }
        if(bDarkTheme.isChecked()) {
            outState.putInt("theme", 0);
        } else {
            outState.putInt("theme", 1);
        }

    }

    // Listener boton de español
    CompoundButton.OnCheckedChangeListener bSpanishListener = new CompoundButton.OnCheckedChangeListener() {
        public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
            if (isChecked) {
                bEnglish.setChecked(false);
                bEnglish.setClickable(true);
                bSpanish.setClickable(false);

                // Obtener una instancia del editor de SharedPreferences
                SharedPreferences.Editor editor = sharedPreferences.edit();
                editor.putString("language", "es");
                editor.apply();
                recreate();

            }
        }
    };

    // Listener boton de ingles
    CompoundButton.OnCheckedChangeListener bEnglishListener = new CompoundButton.OnCheckedChangeListener() {
        public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
            if (isChecked) {

                bSpanish.setChecked(false);
                bSpanish.setClickable(true);
                bEnglish.setClickable(false);

                // Obtener una instancia del editor de SharedPreferences
                SharedPreferences.Editor editor = sharedPreferences.edit();
                editor.putString("language", "en");
                editor.apply();
                recreate();

            }
        }
    };

    // Listener boton de tema oscuro
    CompoundButton.OnCheckedChangeListener bDarkThemeListener = new CompoundButton.OnCheckedChangeListener() {
        public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
            if (isChecked) {
                bWhiteTheme.setChecked(false);
                bWhiteTheme.setClickable(true);
                bDarkTheme.setClickable(false);

                // Obtener una instancia del editor de SharedPreferences
                SharedPreferences.Editor editor = sharedPreferences.edit();
                editor.putString("theme", "dark");
                editor.apply();
                recreate();

            }
        }
    };

    // Listener boton de tema claro
    CompoundButton.OnCheckedChangeListener bWhiteThemeListener = new CompoundButton.OnCheckedChangeListener() {
        public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
            if (isChecked) {
                bDarkTheme.setChecked(false);
                bDarkTheme.setClickable(true);
                bWhiteTheme.setClickable(false);

                sharedPreferences.edit().putString("theme", "white");
                sharedPreferences.edit().commit();
                recreate();

            }
        }
    };

}
