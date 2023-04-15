package es.ucm.fdi.sacapalabra;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;

import android.content.Context;
import android.content.SharedPreferences;
import android.content.res.Configuration;
import android.content.res.Resources;
import android.os.Bundle;
import android.util.Log;
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

        super.onCreate(savedInstanceState);

        // Recuperamos las preferencias
        sharedPreferences = getSharedPreferences("MyPrefs", MODE_PRIVATE);

        String language = sharedPreferences.getString("language", "es");
        String theme = sharedPreferences.getString("theme", "dark");
        Locale locale;

        // Idioma
        if (language.equals("es")) {
            locale = new Locale("es");

        } else {
            locale = new Locale("en");
        }

        Configuration configuration = getResources().getConfiguration();
        configuration.setLocale(locale);
        Context context = createConfigurationContext(configuration);
        Resources resources = context.getResources();

       // Tema
        if (theme.equals("dark")) {
            setTheme(R.style.Theme_Default);
        } else {
            setTheme(R.style.Theme_White);
        }

        setContentView(R.layout.activity_config);

        bSpanish = findViewById(R.id.config_language_op1);
        bEnglish = findViewById(R.id.config_language_op2);
        bDarkTheme = findViewById(R.id.config_theme_op1);
        bWhiteTheme = findViewById(R.id.config_theme_op2);

        bSpanish.setOnCheckedChangeListener(bSpanishListener);
        bEnglish.setOnCheckedChangeListener(bEnglishListener);
        bDarkTheme.setOnCheckedChangeListener(bDarkThemeListener);
        bWhiteTheme.setOnCheckedChangeListener(bWhiteThemeListener);

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



    // Listener boton de español
    CompoundButton.OnCheckedChangeListener bSpanishListener = new CompoundButton.OnCheckedChangeListener() {
        public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
            if (isChecked) {
                bEnglish.setChecked(false);
                bEnglish.setClickable(true);
                bSpanish.setClickable(false);

                sharedPreferences.edit().putString("language", "es").apply();

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

                sharedPreferences.edit().putString("language", "en").apply();

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


                sharedPreferences.edit().putString("theme", "dark").apply();

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

                sharedPreferences.edit().putString("theme", "white").apply();
                // recreate();
                Log.d("Config","recreate");
            }
        }
    };

}
