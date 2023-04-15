package es.ucm.fdi.sacapalabra;

import androidx.appcompat.app.AppCompatActivity;

import android.content.SharedPreferences;
import android.content.res.Configuration;
import android.content.res.Resources;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.View;
import android.widget.CompoundButton;
import android.widget.ToggleButton;

import java.util.Locale;

public class GameOptionsActivity extends AppCompatActivity {
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

        private View layout;

        private SharedPreferences sharedPreferences;

        @Override
        protected void onCreate(Bundle savedInstanceState) {
            super.onCreate(savedInstanceState);
            setContentView(R.layout.activity_game_options);
            addListeners();
            layout = findViewById(R.id.options_layout);

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

            bSpanish.setChecked(true);
            bNormal.setChecked(true);
            bNTries5.setChecked(true);
            bLWord5.setChecked(true);
        }

        private void addListeners(){
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
        }

        CompoundButton.OnCheckedChangeListener toggleButtonListener = new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked) {
                    // Uncheck other toggle buttons in the same category
                    switch (buttonView.getId()) {
                        case R.id.toggleButtonGallego:
                            bEnglish.setChecked(false);
                            bSpanish.setChecked(false);
                            break;
                        case R.id.toggleButtonIngles:
                            bGallego.setChecked(false);
                            bSpanish.setChecked(false);
                            break;
                        case R.id.toggleButtonEspannol:
                            bGallego.setChecked(false);
                            bEnglish.setChecked(false);
                            break;
                        case R.id.toggleButtonNormal:
                            bContrarreloj.setChecked(false);
                            break;
                        case R.id.toggleButtonContrarreloj:
                            bNormal.setChecked(false);
                            break;
                        case R.id.toggleButtonNIntentos3:
                            uncheckOtherToggleButtonsInSameCategory(R.id.toggleButtonNIntentos3);
                            break;
                        case R.id.toggleButtonNIntentos4:
                            uncheckOtherToggleButtonsInSameCategory(R.id.toggleButtonNIntentos4);
                            break;
                        case R.id.toggleButtonNIntentos5:
                            uncheckOtherToggleButtonsInSameCategory(R.id.toggleButtonNIntentos5);
                            break;
                        case R.id.toggleButtonNIntentos6:
                            uncheckOtherToggleButtonsInSameCategory(R.id.toggleButtonNIntentos6);
                            break;
                        case R.id.toggleButtonNIntentos7:
                            uncheckOtherToggleButtonsInSameCategory(R.id.toggleButtonNIntentos7);
                            break;
                        case R.id.toggleButtonLPalabra3:
                            uncheckOtherToggleButtonsInSameCategory(R.id.toggleButtonLPalabra3);
                            break;
                        case R.id.toggleButtonLPalabra4:
                            uncheckOtherToggleButtonsInSameCategory(R.id.toggleButtonLPalabra4);
                            break;
                        case R.id.toggleButtonLPalabra5:
                            uncheckOtherToggleButtonsInSameCategory(R.id.toggleButtonLPalabra5);
                            break;
                        case R.id.toggleButtonLPalabra6:
                            uncheckOtherToggleButtonsInSameCategory(R.id.toggleButtonLPalabra6);
                            break;
                        case R.id.toggleButtonLPalabra7:
                            uncheckOtherToggleButtonsInSameCategory(R.id.toggleButtonLPalabra7);
                            break;
                    }
                }
            }
        };

        // Private method to uncheck other toggle buttons in the same category
        private void uncheckOtherToggleButtonsInSameCategory(int checkedButtonId) {
            switch (checkedButtonId) {
                case R.id.toggleButtonNIntentos3:
                    bNTries4.setChecked(false);
                    bNTries5.setChecked(false);
                    bNTries6.setChecked(false);
                    bNTries7.setChecked(false);
                    break;
                case R.id.toggleButtonNIntentos4:
                    bNTries3.setChecked(false);
                    bNTries5.setChecked(false);
                    bNTries6.setChecked(false);
                    bNTries7.setChecked(false);
                    break;
                case R.id.toggleButtonNIntentos5:
                    bNTries3.setChecked(false);
                    bNTries4.setChecked(false);
                    bNTries6.setChecked(false);
                    bNTries7.setChecked(false);
                    break;
                case R.id.toggleButtonNIntentos6:
                    bNTries3.setChecked(false);
                    bNTries4.setChecked(false);
                    bNTries5.setChecked(false);
                    bNTries7.setChecked(false);
                    break;
                case R.id.toggleButtonNIntentos7:
                    bNTries3.setChecked(false);
                    bNTries4.setChecked(false);
                    bNTries5.setChecked(false);
                    bNTries6.setChecked(false);
                    break;

                case R.id.toggleButtonLPalabra3:
                    bLWord4.setChecked(false);
                    bLWord5.setChecked(false);
                    bLWord6.setChecked(false);
                    bLWord7.setChecked(false);
                    break;
                case R.id.toggleButtonLPalabra4:
                    bLWord3.setChecked(false);
                    bLWord5.setChecked(false);
                    bLWord6.setChecked(false);
                    bLWord7.setChecked(false);
                    break;
                case R.id.toggleButtonLPalabra5:
                    bLWord3.setChecked(false);
                    bLWord4.setChecked(false);
                    bLWord6.setChecked(false);
                    bLWord7.setChecked(false);
                    break;
                case R.id.toggleButtonLPalabra6:
                    bLWord3.setChecked(false);
                    bLWord4.setChecked(false);
                    bLWord5.setChecked(false);
                    bLWord7.setChecked(false);
                    break;
                case R.id.toggleButtonLPalabra7:
                    bLWord3.setChecked(false);
                    bLWord4.setChecked(false);
                    bLWord5.setChecked(false);
                    bLWord6.setChecked(false);
                    break;
            }
        }
    }