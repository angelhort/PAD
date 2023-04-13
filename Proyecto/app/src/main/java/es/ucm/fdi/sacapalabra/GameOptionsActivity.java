package es.ucm.fdi.sacapalabra;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.CompoundButton;
import android.widget.ToggleButton;

    public class GameOptionsActivity extends AppCompatActivity {
        private ToggleButton bEspannol;
        private ToggleButton bIngles;
        private ToggleButton bGallego;

        private ToggleButton bNormal;
        private ToggleButton bContrarreloj;

        private ToggleButton bNIntentos3;
        private ToggleButton bNIntentos4;
        private ToggleButton bNIntentos5;
        private ToggleButton bNIntentos6;
        private ToggleButton bNIntentos7;

        private ToggleButton bLPalabra3;
        private ToggleButton bLPalabra4;
        private ToggleButton bLPalabra5;
        private ToggleButton bLPalabra6;
        private ToggleButton bLPalabra7;
        @Override
        protected void onCreate(Bundle savedInstanceState) {
            super.onCreate(savedInstanceState);
            setContentView(R.layout.activity_game_options);
            addListeners();

            bEspannol.setChecked(true);
            bNormal.setChecked(true);
            bNIntentos5.setChecked(true);
            bLPalabra5.setChecked(true);
        }

        private void addListeners(){
            bEspannol = findViewById(R.id.toggleButtonEspannol);
            bIngles = findViewById(R.id.toggleButtonIngles);
            bGallego = findViewById(R.id.toggleButtonGallego);

            bNormal = findViewById(R.id.toggleButtonNormal);
            bContrarreloj = findViewById(R.id.toggleButtonContrarreloj);

            bNIntentos3 = findViewById(R.id.toggleButtonNIntentos3);
            bNIntentos4 = findViewById(R.id.toggleButtonNIntentos4);
            bNIntentos5 = findViewById(R.id.toggleButtonNIntentos5);
            bNIntentos6 = findViewById(R.id.toggleButtonNIntentos6);
            bNIntentos7 = findViewById(R.id.toggleButtonNIntentos7);

            bLPalabra3 = findViewById(R.id.toggleButtonLPalabra3);
            bLPalabra4 = findViewById(R.id.toggleButtonLPalabra4);
            bLPalabra5 = findViewById(R.id.toggleButtonLPalabra5);
            bLPalabra6 = findViewById(R.id.toggleButtonLPalabra6);
            bLPalabra7 = findViewById(R.id.toggleButtonLPalabra7);

            // Add action listeners to toggle buttons
            bGallego.setOnCheckedChangeListener(toggleButtonListener);
            bIngles.setOnCheckedChangeListener(toggleButtonListener);
            bEspannol.setOnCheckedChangeListener(toggleButtonListener);

            bNormal.setOnCheckedChangeListener(toggleButtonListener);
            bContrarreloj.setOnCheckedChangeListener(toggleButtonListener);

            bNIntentos3.setOnCheckedChangeListener(toggleButtonListener);
            bNIntentos4.setOnCheckedChangeListener(toggleButtonListener);
            bNIntentos5.setOnCheckedChangeListener(toggleButtonListener);
            bNIntentos6.setOnCheckedChangeListener(toggleButtonListener);
            bNIntentos7.setOnCheckedChangeListener(toggleButtonListener);

            bLPalabra3.setOnCheckedChangeListener(toggleButtonListener);
            bLPalabra4.setOnCheckedChangeListener(toggleButtonListener);
            bLPalabra5.setOnCheckedChangeListener(toggleButtonListener);
            bLPalabra6.setOnCheckedChangeListener(toggleButtonListener);
            bLPalabra7.setOnCheckedChangeListener(toggleButtonListener);
        }

        CompoundButton.OnCheckedChangeListener toggleButtonListener = new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked) {
                    // Uncheck other toggle buttons in the same category
                    switch (buttonView.getId()) {
                        case R.id.toggleButtonGallego:
                            bIngles.setChecked(false);
                            bEspannol.setChecked(false);
                            break;
                        case R.id.toggleButtonIngles:
                            bGallego.setChecked(false);
                            bEspannol.setChecked(false);
                            break;
                        case R.id.toggleButtonEspannol:
                            bGallego.setChecked(false);
                            bIngles.setChecked(false);
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
                    bNIntentos4.setChecked(false);
                    bNIntentos5.setChecked(false);
                    bNIntentos6.setChecked(false);
                    bNIntentos7.setChecked(false);
                    break;
                case R.id.toggleButtonNIntentos4:
                    bNIntentos3.setChecked(false);
                    bNIntentos5.setChecked(false);
                    bNIntentos6.setChecked(false);
                    bNIntentos7.setChecked(false);
                    break;
                case R.id.toggleButtonNIntentos5:
                    bNIntentos3.setChecked(false);
                    bNIntentos4.setChecked(false);
                    bNIntentos6.setChecked(false);
                    bNIntentos7.setChecked(false);
                    break;
                case R.id.toggleButtonNIntentos6:
                    bNIntentos3.setChecked(false);
                    bNIntentos4.setChecked(false);
                    bNIntentos5.setChecked(false);
                    bNIntentos7.setChecked(false);
                    break;
                case R.id.toggleButtonNIntentos7:
                    bNIntentos3.setChecked(false);
                    bNIntentos4.setChecked(false);
                    bNIntentos5.setChecked(false);
                    bNIntentos6.setChecked(false);
                    break;

                case R.id.toggleButtonLPalabra3:
                    bLPalabra4.setChecked(false);
                    bLPalabra5.setChecked(false);
                    bLPalabra6.setChecked(false);
                    bLPalabra7.setChecked(false);
                    break;
                case R.id.toggleButtonLPalabra4:
                    bLPalabra3.setChecked(false);
                    bLPalabra5.setChecked(false);
                    bLPalabra6.setChecked(false);
                    bLPalabra7.setChecked(false);
                    break;
                case R.id.toggleButtonLPalabra5:
                    bLPalabra3.setChecked(false);
                    bLPalabra4.setChecked(false);
                    bLPalabra6.setChecked(false);
                    bLPalabra7.setChecked(false);
                    break;
                case R.id.toggleButtonLPalabra6:
                    bLPalabra3.setChecked(false);
                    bLPalabra4.setChecked(false);
                    bLPalabra5.setChecked(false);
                    bLPalabra7.setChecked(false);
                    break;
                case R.id.toggleButtonLPalabra7:
                    bLPalabra3.setChecked(false);
                    bLPalabra4.setChecked(false);
                    bLPalabra5.setChecked(false);
                    bLPalabra6.setChecked(false);
                    break;
            }
        }
    }