package es.ucm.fdi.sacapalabra;

import static es.ucm.fdi.sacapalabra.ConnectionUtils.getWord;

import androidx.appcompat.app.AppCompatActivity;
import androidx.loader.app.LoaderManager;
import androidx.loader.content.Loader;

import android.content.Intent;
import android.content.SharedPreferences;
import android.content.res.Configuration;
import android.content.res.Resources;
import android.graphics.Color;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.GridLayout;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.util.Locale;

public class inGame extends AppCompatActivity implements WordLoaderCallbacksListener{

    private SharedPreferences sharedPreferences;

    private WordLoaderCallbacks wordLoaderCallbacks;

    private static final int WORD_LOADER_ID = 0;

    private String palabra;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        // Recuperamos las preferencias
        sharedPreferences = getSharedPreferences("MyPrefs", MODE_PRIVATE);
        String theme = sharedPreferences.getString("theme", "dark");
        setTheme(theme);

        Intent intent = getIntent();
        String idioma = intent.getStringExtra("idioma");
        String modo = intent.getStringExtra("modo");
        int intentos = intent.getIntExtra("intentos", 0);
        int longitud = intent.getIntExtra("longitud", 0);

        wordLoaderCallbacks = new WordLoaderCallbacks(this, this);
        LoaderManager loaderManager = LoaderManager.getInstance(this);
        if(loaderManager.getLoader(WORD_LOADER_ID) != null){
            loaderManager.initLoader(WORD_LOADER_ID, null, wordLoaderCallbacks);
        }
        
        Bundle queryBundle = new Bundle();
        queryBundle.putString(WordLoaderCallbacks.LENGTH, String.valueOf(longitud));
        queryBundle.putString(WordLoaderCallbacks.LANGUAGE, idioma);
        LoaderManager.getInstance(this)
                .restartLoader(WORD_LOADER_ID, queryBundle, wordLoaderCallbacks);

        TextView[] myTextViews = new TextView[longitud*intentos];

        createTextViewGrid(intentos, longitud, myTextViews);
    }    

private void createTextViewGrid(int rows, int cols, TextView[] myTextViews) {

    LinearLayout generalLayout = new LinearLayout(this);
    generalLayout.setLayoutParams(new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT));
    generalLayout.setOrientation(LinearLayout.VERTICAL);


    LinearLayout linearLayout;
    TextView textView;
    ViewGroup view = null;

    // Creamos los LinearLayouts horizontales y los TextViews dentro de ellos
    for (int i = 0; i < rows; i++) {
        linearLayout = new LinearLayout(this);
        linearLayout.setLayoutParams(new LinearLayout.LayoutParams(50, 50));
        linearLayout.setOrientation(LinearLayout.HORIZONTAL);
        linearLayout.setGravity(Gravity.CENTER);

        for (int j = 0; j < cols; j++) {
            textView = new TextView(this);
            textView.setLayoutParams(new LinearLayout.LayoutParams(60, 60, 1));
            textView.setGravity(Gravity.CENTER);
            textView.setBackground(getResources().getDrawable(R.drawable.textview_border));
            textView.setText(Character.toString((char)('a' + (i*cols)+j))); // Set the text of each TextView to a different letter
            textView.setTextColor(Color.RED);
            myTextViews[(i*cols)+j] = textView;
            linearLayout.addView(textView);
        }


        generalLayout.addView(linearLayout);
        Log.d("InGame" ,"Creado linearLayout");
    }
    setContentView(generalLayout);
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

    @Override
    public void onWordLoaded(String word) {
        this.palabra = word;
        System.out.println(this.palabra);
    }
}