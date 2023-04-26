package es.ucm.fdi.sacapalabra;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.NotificationCompat;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.res.Configuration;
import android.content.res.Resources;
import android.os.Build;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.Switch;
import android.widget.ToggleButton;

import java.util.Locale;

public class ConfigActivity extends BaseActivity {

    private ToggleButton bSpanish;
    private ToggleButton bEnglish;
    private ToggleButton bDarkTheme;
    private ToggleButton bWhiteTheme;
    private Button confirm_button;

    private Switch dSwitch;
    private Switch notifSwitch;

    private SharedPreferences sharedPreferences;

    private NotificationCompat.Builder notif;
    NotificationManager notifManager;

    private final static int id = 10000;
    private final static String CHANNEL_ID = "channel_01";

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);

        sharedPreferences = getSharedPreferences("MyPrefs", MODE_PRIVATE);
        String language = sharedPreferences.getString("language", "es");
        String theme = sharedPreferences.getString("theme", "dark");
        boolean colors = sharedPreferences.getBoolean("colorblind", false);
        boolean notifs = sharedPreferences.getBoolean("notifs", false);

        setLanguage(language);
        setTheme(theme);

        setContentView(R.layout.activity_config);
        assignButtons();
        setButtons(language, theme,colors,notifs);

        notif = new NotificationCompat.Builder(this, CHANNEL_ID);
        notif.setAutoCancel(true);
        setTheme(theme);
        setContentView(R.layout.activity_config);
        assignButtons();
        setButtons(language, theme, colors, notifs);
        recoverSavedInstance(savedInstanceState);

        initChannels(this);

       if (savedInstanceState != null)
           recoverSavedInstance(savedInstanceState);
    }

    private void assignButtons(){

        bSpanish = findViewById(R.id.config_language_op1);
        bEnglish = findViewById(R.id.config_language_op2);
        bDarkTheme = findViewById(R.id.config_theme_op1);
        bWhiteTheme = findViewById(R.id.config_theme_op2);
        dSwitch = findViewById(R.id.config_switch_daltonism);
        notifSwitch = findViewById(R.id.config_switch_notif);
        confirm_button = findViewById(R.id.button_confirm);

        bSpanish.setOnCheckedChangeListener(bSpanishListener);
        bEnglish.setOnCheckedChangeListener(bEnglishListener);
        bDarkTheme.setOnCheckedChangeListener(bDarkThemeListener);
        bWhiteTheme.setOnCheckedChangeListener(bWhiteThemeListener);
        dSwitch.setOnCheckedChangeListener(dSwitchListener);
        notifSwitch.setOnCheckedChangeListener(notifSwitchListener);
        confirm_button.setOnClickListener(confirmListener);
    }

    private void setButtons(String language, String theme, boolean colors, boolean notifs) {

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

        dSwitch.setChecked(colors);
        notifSwitch.setChecked(notifs);

    }
    private void setTheme(String theme) {
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

    public void initChannels(Context context) {
        if (Build.VERSION.SDK_INT < 26) {
            return;
        }
        notifManager =
                (NotificationManager) context.getSystemService(Context.NOTIFICATION_SERVICE);
        NotificationChannel channel = new NotificationChannel(CHANNEL_ID,
                "Channel name",
                NotificationManager.IMPORTANCE_DEFAULT);
        channel.setDescription("Channel description");
        notifManager.createNotificationChannel(channel);
    }


    private void recoverSavedInstance(Bundle savedInstanceState) {
        // Recuperar la instancia si se ha cambiado la configuración
        if (savedInstanceState != null) {
            boolean languageSelected = savedInstanceState.getBoolean("language");
            boolean themeSelected = savedInstanceState.getBoolean("theme");
            boolean colorSwitch = savedInstanceState.getBoolean("colorblind");
            boolean notificationSwitch = savedInstanceState.getBoolean("notifications");

            // Idioma
            if (languageSelected) {
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

            // Tema
            if (themeSelected) {
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
            // Daltonismo
            dSwitch.setChecked(colorSwitch);
            // Notificaciones
            notifSwitch.setChecked(notificationSwitch);
        }
    }
    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putBoolean("language", bSpanish.isChecked());
        outState.putBoolean("theme", bDarkTheme.isChecked());
        outState.putBoolean("colorblind", dSwitch.isChecked());
        outState.putBoolean("notifications", notifSwitch.isChecked());
    }

    View.OnClickListener confirmListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            finish();
            Intent intent = new Intent(ConfigActivity.this, MainActivity.class);
            startActivity(intent);
            overridePendingTransition(R.transition.fade_in, R.transition.fade_out);

        }
    };
    CompoundButton.OnCheckedChangeListener bSpanishListener = new CompoundButton.OnCheckedChangeListener() {
        public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
            if (isChecked) {

                bEnglish.setChecked(false);
                bEnglish.setClickable(true);
                bSpanish.setClickable(false);

                sharedPreferences.edit().putString("language", "es").apply();
                setLanguage("es");
            }
        }
    };
    CompoundButton.OnCheckedChangeListener bEnglishListener = new CompoundButton.OnCheckedChangeListener() {
        public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
            if (isChecked) {

                bSpanish.setChecked(false);
                bSpanish.setClickable(true);
                bEnglish.setClickable(false);

                sharedPreferences.edit().putString("language", "en").apply();
                setLanguage("en");
            }
        }
    };
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

    CompoundButton.OnCheckedChangeListener dSwitchListener = new CompoundButton.OnCheckedChangeListener() {
        public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
            if (isChecked) {
                sharedPreferences.edit().putBoolean("colorblind", true).apply();
            } else {
                sharedPreferences.edit().putBoolean("colorblind", false).apply();
            }
        }
    };

    CompoundButton.OnCheckedChangeListener notifSwitchListener = new CompoundButton.OnCheckedChangeListener() {
        public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
            if (isChecked) {
                sharedPreferences.edit().putBoolean("notifs", true).apply();
                notifSwitch.setChecked(true);

                startNotificationService(); // Arrancamos el servicio de notificaciones
            } else {
                sharedPreferences.edit().putBoolean("notifs", false).apply();
                notifSwitch.setChecked(false);
                stopNotificationService(); // Paramos el envío de notificaciones
            }
        }
    };

    private void startNotificationService() {
        Intent intent = new Intent(this, NotificationService.class);
        intent.putExtra("notification_enabled", true);
        startService(intent);
    }

    private void stopNotificationService() {
        Intent intent = new Intent(this, NotificationService.class);
        stopService(intent);
    }

}
