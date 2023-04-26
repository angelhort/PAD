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
import android.widget.ToggleButton;

import java.util.Arrays;
import java.util.List;
import java.util.Locale;

public class GameOptionsActivity extends BaseActivity {

    private ToggleButton bSpanish;
    private ToggleButton bEnglish;
    private ToggleButton bGallego;

    private ToggleButton bNormal;
    private ToggleButton bContrarreloj;

    private ToggleButton bNTries3;
    private ToggleButton bNTries4;
    private ToggleButton bNTries5;
    private ToggleButton bNTries6;
    private ToggleButton bNTries7;

    private ToggleButton bLWord3;
    private ToggleButton bLWord4;
    private ToggleButton bLWord5;
    private ToggleButton bLWord6;
    private ToggleButton bLWord7;

    private Button bPlay;

    private SharedPreferences sharedPreferences;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        // Recuperamos las preferencias
        sharedPreferences = getSharedPreferences("MyPrefs", MODE_PRIVATE);
        String theme = sharedPreferences.getString("theme", "dark");
        String language = sharedPreferences.getString("language", "es");

        setLanguage(language);
        setTheme(theme);

        setContentView(R.layout.activity_game_options);
        assignButtons();
    }

    private void assignButtons(){

        // Assign buttons IDs
        bSpanish = findViewById(R.id.toggleButtonEspannol);
        bEnglish = findViewById(R.id.toggleButtonIngles);
        bGallego = findViewById(R.id.toggleButtonGallego);

        bNormal = findViewById(R.id.toggleButtonNormal);
        bContrarreloj = findViewById(R.id.toggleButtonContrarreloj);

        bNTries3 = findViewById(R.id.toggleButtonNIntentos3);
        bNTries4 = findViewById(R.id.toggleButtonNIntentos4);
        bNTries5 = findViewById(R.id.toggleButtonNIntentos5);
        bNTries6 = findViewById(R.id.toggleButtonNIntentos6);
        bNTries7 = findViewById(R.id.toggleButtonNIntentos7);

        bLWord3 = findViewById(R.id.toggleButtonLPalabra3);
        bLWord4 = findViewById(R.id.toggleButtonLPalabra4);
        bLWord5 = findViewById(R.id.toggleButtonLPalabra5);
        bLWord6 = findViewById(R.id.toggleButtonLPalabra6);
        bLWord7 = findViewById(R.id.toggleButtonLPalabra7);

        bPlay = findViewById(R.id.bPlay);

        // Add action listeners to toggle buttons
        bGallego.setOnCheckedChangeListener(toggleButtonListener);
        bEnglish.setOnCheckedChangeListener(toggleButtonListener);
        bSpanish.setOnCheckedChangeListener(toggleButtonListener);

        bNormal.setOnCheckedChangeListener(toggleButtonListener);
        bContrarreloj.setOnCheckedChangeListener(toggleButtonListener);

        bNTries3.setOnCheckedChangeListener(toggleButtonListener);
        bNTries4.setOnCheckedChangeListener(toggleButtonListener);
        bNTries5.setOnCheckedChangeListener(toggleButtonListener);
        bNTries6.setOnCheckedChangeListener(toggleButtonListener);
        bNTries7.setOnCheckedChangeListener(toggleButtonListener);

        bLWord3.setOnCheckedChangeListener(toggleButtonListener);
        bLWord4.setOnCheckedChangeListener(toggleButtonListener);
        bLWord5.setOnCheckedChangeListener(toggleButtonListener);
        bLWord6.setOnCheckedChangeListener(toggleButtonListener);
        bLWord7.setOnCheckedChangeListener(toggleButtonListener);

        // Add OnClickListener to bPlay button
        bPlay.setOnClickListener(bPlayListener);

        bSpanish.setChecked(true);
        bNormal.setChecked(true);
        bNTries5.setChecked(true);
        bLWord5.setChecked(true);
    }
    private void toggleButtons(ToggleButton checkedButton, List<ToggleButton> buttonsToUncheck) {
        for (ToggleButton button : buttonsToUncheck) {
            button.setChecked(false);
            button.setClickable(true);
        }
        checkedButton.setClickable(false);
    }

    private String getSelectedLanguage() {
        if (bSpanish.isChecked()) {
            return "es";
        } else if (bEnglish.isChecked()) {
            return "en";
        } else if (bGallego.isChecked()) {
            return "gl";
        } else {
            // Si no se ha seleccionado ningún idioma, devolvemos el idioma por defecto
            return "es";
        }
    }
    private String getSelectedMode() {
        if (bNormal.isChecked()) {
            return "normal";
        } else if (bContrarreloj.isChecked()) {
            return "contrarreloj";
        } else {
            // Si no se ha seleccionado ningún modo, devolvemos el modo por defecto
            return "normal";
        }
    }
    private int getSelectedNTries() {
        if (bNTries3.isChecked()) {
            return 3;
        } else if (bNTries4.isChecked()) {
            return 4;
        } else if (bNTries5.isChecked()) {
            return 5;
        } else if (bNTries6.isChecked()) {
            return 6;
        } else if (bNTries7.isChecked()) {
            return 7;
        } else {
            // Si no se ha seleccionado ningún número de intentos, devolvemos el número de intentos por defecto
            return 5;
        }
    }
    private int getSelectedLWord() {
        if (bLWord3.isChecked()) {
            return 3;
        } else if (bLWord4.isChecked()) {
            return 4;
        } else if (bLWord5.isChecked()) {
            return 5;
        } else if (bLWord6.isChecked()) {
            return 6;
        } else if (bLWord7.isChecked()) {
            return 7;
        } else {
            // Si no se ha seleccionado ninguna longitud de palabra, devolvemos la longitud de palabra por defecto
            return 5;
        }
    }

    private void setTheme(String theme){
        if (theme.equals("dark")) {
            setTheme(R.style.Theme_Default);
        } else {
            setTheme(R.style.Theme_White);
        }
    }

    private void setLanguage(String language) {
        Locale locale = new Locale(language);
        Resources res = getResources();
        DisplayMetrics dm = res.getDisplayMetrics();
        Configuration conf = res.getConfiguration();
        conf.locale = locale;
        res.updateConfiguration(conf, dm);
    }

    View.OnClickListener bPlayListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            // Create Intent to start InGameActivity
            Intent intent = new Intent(GameOptionsActivity.this, inGameActivity.class);

            // Put selected data as extras in Intent
            intent.putExtra("idioma", getSelectedLanguage());
            intent.putExtra("modo", getSelectedMode());
            intent.putExtra("intentos", getSelectedNTries());
            intent.putExtra("longitud", getSelectedLWord());

            // Start InGameActivity with Intent
            startActivity(intent);
        }
    };
    CompoundButton.OnCheckedChangeListener toggleButtonListener = new CompoundButton.OnCheckedChangeListener() {
        @Override
        public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
            if (isChecked) {
                // Uncheck other toggle buttons in the same category
                switch (buttonView.getId()) {
                    case R.id.toggleButtonGallego:
                        toggleButtons(bGallego, Arrays.asList(bEnglish, bSpanish));
                        break;
                    case R.id.toggleButtonIngles:
                        toggleButtons(bEnglish, Arrays.asList(bGallego, bSpanish));
                        break;
                    case R.id.toggleButtonEspannol:
                        toggleButtons(bSpanish, Arrays.asList(bEnglish, bGallego));
                        break;
                    case R.id.toggleButtonNormal:
                        toggleButtons(bNormal, Arrays.asList(bContrarreloj));
                        break;
                    case R.id.toggleButtonContrarreloj:
                        toggleButtons(bContrarreloj, Arrays.asList(bNormal));
                        break;
                    case R.id.toggleButtonNIntentos3:
                        toggleButtons(bNTries3, Arrays.asList(bNTries4, bNTries5, bNTries6, bNTries7));
                        break;
                    case R.id.toggleButtonNIntentos4:
                        toggleButtons(bNTries4, Arrays.asList(bNTries3, bNTries5, bNTries6, bNTries7));
                        break;
                    case R.id.toggleButtonNIntentos5:
                        toggleButtons(bNTries5, Arrays.asList(bNTries3, bNTries4, bNTries6, bNTries7));
                        break;
                    case R.id.toggleButtonNIntentos6:
                        toggleButtons(bNTries6, Arrays.asList(bNTries3, bNTries4, bNTries5, bNTries7));
                        break;
                    case R.id.toggleButtonNIntentos7:
                        toggleButtons(bNTries7, Arrays.asList(bNTries3, bNTries4, bNTries5, bNTries6));
                        break;
                    case R.id.toggleButtonLPalabra3:
                        toggleButtons(bLWord3, Arrays.asList(bLWord4, bLWord5, bLWord6, bLWord7));
                        break;
                    case R.id.toggleButtonLPalabra4:
                        toggleButtons(bLWord4, Arrays.asList(bLWord3, bLWord5, bLWord6, bLWord7));
                        break;
                    case R.id.toggleButtonLPalabra5:
                        toggleButtons(bLWord5, Arrays.asList(bLWord3, bLWord4, bLWord6, bLWord7));
                        break;
                    case R.id.toggleButtonLPalabra6:
                        toggleButtons(bLWord6, Arrays.asList(bLWord3, bLWord4, bLWord5, bLWord7));
                        break;
                    case R.id.toggleButtonLPalabra7:
                        toggleButtons(bLWord7, Arrays.asList(bLWord3, bLWord4, bLWord5, bLWord6));
                        break;
                }
            }
        }
    };
}

    