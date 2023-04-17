package es.ucm.fdi.sacapalabra;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.content.res.Configuration;
import android.content.res.Resources;
import android.os.Bundle;
import android.util.DisplayMetrics;
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

    private SharedPreferences sharedPreferences;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);

        // Recuperamos las preferencias
        sharedPreferences = getSharedPreferences("MyPrefs", MODE_PRIVATE);
        String language = sharedPreferences.getString("language", "es");
        String theme = sharedPreferences.getString("theme", "dark");
        setTheme(theme);

        setContentView(R.layout.activity_config);
        assignButtons();
        setButtons(language,theme);
        recoverSavedInstance(savedInstanceState);
    }

    // Listener button
    View.OnClickListener confirmListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            Intent intent = new Intent(ConfigActivity.this, MainActivity.class);
            startActivity(intent);
            finish();
        }
    };
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
            }
        }
    };

    private void assignButtons(){

        bSpanish = findViewById(R.id.config_language_op1);
        bEnglish = findViewById(R.id.config_language_op2);
        bDarkTheme = findViewById(R.id.config_theme_op1);
        bWhiteTheme = findViewById(R.id.config_theme_op2);
        confirm_button = findViewById(R.id.button_confirm);

        bSpanish.setOnCheckedChangeListener(bSpanishListener);
        bEnglish.setOnCheckedChangeListener(bEnglishListener);
        bDarkTheme.setOnCheckedChangeListener(bDarkThemeListener);
        bWhiteTheme.setOnCheckedChangeListener(bWhiteThemeListener);
        confirm_button.setOnClickListener(confirmListener);
    }
    private void setButtons(String language, String theme){

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

    private void recoverSavedInstance(Bundle savedInstanceState){
        // Recuperar la instancia si se ha cambiado la configuración
        if (savedInstanceState != null) {
            int languageSelected = savedInstanceState.getInt("language");
            int themeSelected = savedInstanceState.getInt("theme");
            int switchSelected = savedInstanceState.getInt("colourblind");

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

}
