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
import android.widget.ToggleButton;

import java.util.Locale;

public class ConfigActivity extends AppCompatActivity {

    private ToggleButton bSpanish;
    private ToggleButton bEnglish;

    private ToggleButton bDarkTheme;
    private ToggleButton bWhiteTheme;

    private Button confirm_button;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_config);

        bEnglish = findViewById(R.id.config_language_op2);

        // Cambia el idioma
        bEnglish.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked) {
                    Locale locale = new Locale("en");
                    Configuration configuration = getResources().getConfiguration();
                    configuration.setLocale(locale);
                    Context context = createConfigurationContext(configuration);
                    Resources resources = context.getResources();

                } else {
                }
            }
        });

    }
}
