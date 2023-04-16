package es.ucm.fdi.sacapalabra;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.content.res.Configuration;
import android.content.res.Resources;
import android.graphics.Color;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.view.Gravity;
import android.view.ViewGroup;
import android.widget.GridLayout;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.util.Locale;

public class inGame extends AppCompatActivity {

    private SharedPreferences sharedPreferences;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_in_game);

        // Recuperamos las preferencias
        sharedPreferences = getSharedPreferences("MyPrefs", MODE_PRIVATE);

        String theme = sharedPreferences.getString("theme", "dark");
        String language = sharedPreferences.getString("language", "es");

        setTheme(theme);
        setLanguage(language);

        Intent intent = getIntent();
        String idioma = intent.getStringExtra("idioma");
        String modo = intent.getStringExtra("modo");
        int intentos = intent.getIntExtra("intentos", 0);
        int longitud = intent.getIntExtra("longitud", 0);

        TextView[] myTextViews = new TextView[longitud*intentos];

        createTextViewGrid(intentos, longitud, myTextViews);
    }    

private void createTextViewGrid(int rows, int cols, TextView[] myTextViews) {
    LinearLayout linearLayout;
    TextView textView;

    // Creamos los LinearLayouts horizontales y los TextViews dentro de ellos
    for (int i = 0; i < rows; i++) {
        linearLayout = new LinearLayout(this);
        linearLayout.setLayoutParams(new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT));
        linearLayout.setOrientation(LinearLayout.HORIZONTAL);
        linearLayout.setGravity(Gravity.CENTER);

        for (int j = 0; j < cols; j++) {
            textView = new TextView(this);
            textView.setLayoutParams(new LinearLayout.LayoutParams(0, ViewGroup.LayoutParams.WRAP_CONTENT, 1));
            textView.setGravity(Gravity.CENTER);
            textView.setBackground(getResources().getDrawable(R.drawable.textview_border));
            textView.setText(Character.toString((char)('a' + (i*cols)+j))); // Set the text of each TextView to a different letter
            textView.setTextColor(Color.RED);
            myTextViews[(i*cols)+j] = textView;
            linearLayout.addView(textView);
        }

        ((LinearLayout) findViewById(R.id.juego)).addView(linearLayout);
        linearLayout.requestLayout();
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
}