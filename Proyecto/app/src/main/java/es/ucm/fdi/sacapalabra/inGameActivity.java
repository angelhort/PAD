package es.ucm.fdi.sacapalabra;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;
import androidx.loader.app.LoaderManager;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.res.Resources;
import android.graphics.Typeface;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class inGameActivity extends AppCompatActivity implements WordLoaderCallbacksListener {

    private static final int WORD_LOADER_ID = 0;

    private SharedPreferences sharedPreferences;

    private WordLoaderCallbacks wordLoaderCallbacks;

    private Game game;
    private LinearLayout generalLayout;
    private TextView title;
    private EditText inputText;
    private Button confirmButton;
    private TextView[][] myTextViews;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);

        // Recuperamos las preferencias
        sharedPreferences = getSharedPreferences("MyPrefs", MODE_PRIVATE);
        String theme = sharedPreferences.getString("theme", "dark");
        setTheme(theme);

        // Create Game object
        Intent intent = getIntent();
        game = new Game(intent.getStringExtra("idioma"), intent.getStringExtra("modo"),intent.getIntExtra("intentos", 0),intent.getIntExtra("longitud", 0));

        // Check for internet connectivity
        if (isNetworkAvailable()) {
            Log.d("game", "hay internet");
            // Create wordLoaderCallbacks
            wordLoaderCallbacks = new WordLoaderCallbacks(this, this);
            LoaderManager loaderManager = LoaderManager.getInstance(this);
            if(loaderManager.getLoader(WORD_LOADER_ID) != null){
                loaderManager.initLoader(WORD_LOADER_ID, null, wordLoaderCallbacks);
            }
            Bundle queryBundle = new Bundle();
            queryBundle.putString(WordLoaderCallbacks.LENGTH, String.valueOf(game.getLenght()));
            queryBundle.putString(WordLoaderCallbacks.LANGUAGE, game.getLanguage());
            LoaderManager.getInstance(this).restartLoader(WORD_LOADER_ID, queryBundle, wordLoaderCallbacks);
        } else {
            Log.d("game", "no hay internet");
            // Load word from local file
            String fileName = game.getLanguage() + "_" + game.getLenght() + ".txt";
            List<String> words = new ArrayList<>();
            try {
                InputStream inputStream = getAssets().open(fileName);
                BufferedReader reader = new BufferedReader(new InputStreamReader(inputStream));
                String line;
                while ((line = reader.readLine()) != null) {
                    words.add(line);
                }
                reader.close();
                inputStream.close();
                Random random = new Random();
                int index = random.nextInt(words.size());
                String randomWord = words.get(index);
                game.setWord(randomWord);
                System.out.println("fichero: "+game.getWord());
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        myTextViews = new TextView[game.getNtries()][game.getLenght()];
        addViews();

        Button exampleButton = new Button(this);
        exampleButton.setText("PULSA");
        exampleButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                myTextViews[0][0].setText("VA");
            }
        });
        generalLayout.addView(exampleButton);

    }

    private void addViews(){

        generalLayout = new LinearLayout(this);
        generalLayout.setLayoutParams(new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT));
        generalLayout.setOrientation(LinearLayout.VERTICAL);

        title = new TextView(this);
        LinearLayout.LayoutParams titleLayoutParams = (new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT, 1));
        titleLayoutParams.gravity = Gravity.CENTER_HORIZONTAL;
        titleLayoutParams.setMargins(0,50,0,0);
        title.setLayoutParams(titleLayoutParams);

        title.setText(R.string.sacapalabra);
        title.setTextSize(48);
        title.setGravity(Gravity.CENTER);
        generalLayout.addView(title);

        createBoard(game.getNtries(), game.getLenght());

        inputText = new EditText(this);
        LinearLayout.LayoutParams inputLayoutParams = (new LinearLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.MATCH_PARENT, 1));
        inputLayoutParams.gravity = Gravity.CENTER_HORIZONTAL;
        inputLayoutParams.setMargins(0,50,0,0);
        inputText.setLayoutParams(inputLayoutParams);
        inputText.setHint(R.string.inputText);
        inputText.setTextSize(28);
        inputText.setGravity(Gravity.CENTER);
        inputText.setTextColor(ContextCompat.getColor(this, R.color.white));
        inputText.setHintTextColor(ContextCompat.getColor(this, R.color.theme_green));

        confirmButton = new Button(this);
        confirmButton.setBackgroundResource(R.drawable.roundbutton);
        confirmButton.setTextAlignment(View.TEXT_ALIGNMENT_CENTER);
        confirmButton.setGravity(Gravity.CENTER);
        confirmButton.setText(R.string.confirmWord);
        confirmButton.setTextSize(20);

        LinearLayout.LayoutParams confirmButtonParams = (new LinearLayout.LayoutParams(240, 240, 1));
        confirmButtonParams.gravity = Gravity.CENTER_HORIZONTAL;
        confirmButtonParams.setMargins(0,50,0,0);
        confirmButton.setLayoutParams(confirmButtonParams);

        generalLayout.addView(inputText);
        generalLayout.addView(confirmButton);

    }

    private void createBoard(int rows, int cols) {

        // Layout para tablero
        LinearLayout boardLayout = new LinearLayout(this);
        LinearLayout.LayoutParams boardLayoutParams = (new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT, 1));

        boardLayout.setOrientation(LinearLayout.VERTICAL);
        boardLayoutParams.gravity = Gravity.CENTER_HORIZONTAL;
        boardLayoutParams.setMargins(0,50,0,50);

        boardLayout.setLayoutParams(boardLayoutParams);

        // Creamos los LinearLayouts horizontales y los TextViews dentro de ellos
        LinearLayout linearLayout;
        TextView textView;

        int screenWidth = Resources.getSystem().getDisplayMetrics().widthPixels - 30;       // 50 actua de margen
        int screenHeight = Resources.getSystem().getDisplayMetrics().heightPixels;
        int marginSize = 3; // Grid margin
        int maxSquareSize = (screenWidth - 2 * marginSize - (cols - 1) * 5) / cols;
        int squareSize = Math.min(Math.min(maxSquareSize, (screenHeight - 2 * marginSize - (rows - 1) * 5) / rows), 150);

        for (int i = 0; i < rows; i++) {
            linearLayout = new LinearLayout(this);
            LinearLayout.LayoutParams rowLayout = (new LinearLayout.LayoutParams(squareSize*cols, ViewGroup.LayoutParams.WRAP_CONTENT, 1));
            rowLayout.gravity = Gravity.CENTER;

            linearLayout.setLayoutParams(rowLayout);
            linearLayout.setOrientation(LinearLayout.HORIZONTAL);
            linearLayout.setWeightSum(cols);

            for (int j = 0; j < cols; j++) {
                textView = new TextView(this);
                LinearLayout.LayoutParams layoutParamsText = (new LinearLayout.LayoutParams(squareSize, squareSize, 1));
                layoutParamsText.setMargins(4,4,4,4);
                textView.setLayoutParams(layoutParamsText);
                textView.setBackgroundResource(R.drawable.textview_border);
                textView.setTextSize(24);
                textView.setGravity(Gravity.CENTER);
                textView.setTypeface(null, Typeface.BOLD);;
                textView.setText(Character.toString((char)('a' + (i*cols)+j))); // Set the text of each TextView to a different letter

                myTextViews[i][j] = textView;
                linearLayout.addView(textView);
            }

            boardLayout.addView(linearLayout);
            Log.d("InGame" ,"Creado linearLayout");
        }
        generalLayout.addView(boardLayout);
        setContentView(generalLayout);
    }

    private void setTheme(String theme){
        if (theme.equals("dark")) {
            setTheme(R.style.Theme_Default);
        } else {
            setTheme(R.style.Theme_White);
        }
    }

    @Override
    public void onWordLoaded(String word) {
        game.setWord(word);
        System.out.println(game.getWord());
    }

    private boolean isNetworkAvailable() {
        ConnectivityManager connectivityManager = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo activeNetworkInfo = connectivityManager.getActiveNetworkInfo();
        Log.d("InGame", "Active network info: " + activeNetworkInfo);
        return activeNetworkInfo != null && activeNetworkInfo.isConnected();
    }
}