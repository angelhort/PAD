package es.ucm.fdi.sacapalabra;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;

import android.content.Intent;
import android.content.SharedPreferences;
import android.content.res.Resources;
import android.graphics.Typeface;
import android.os.Bundle;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;

public class inGameActivity extends AppCompatActivity implements WordLoaderCallbacksListener {

    private static final int WORD_LOADER_ID = 0;

    private SharedPreferences sharedPreferences;

    //private WordLoaderCallbacks wordLoaderCallbacks;

    private Game game;
    private LinearLayout generalLayout;
    private TextView title;
    private EditText inputText;
    private Button confirmButton;

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

        /* // Create wordLoaderCallbacks
        wordLoaderCallbacks = new WordLoaderCallbacks(this, this);
        LoaderManager loaderManager = LoaderManager.getInstance(this);
        if(loaderManager.getLoader(WORD_LOADER_ID) != null){
            loaderManager.initLoader(WORD_LOADER_ID, null, wordLoaderCallbacks);
        }
        Bundle queryBundle = new Bundle();
        queryBundle.putString(WordLoaderCallbacks.LENGTH, String.valueOf(game.getLenght()));
        queryBundle.putString(WordLoaderCallbacks.LANGUAGE, game.getLanguage());
        LoaderManager.getInstance(this).restartLoader(WORD_LOADER_ID, queryBundle, wordLoaderCallbacks);
        */

        addViews();
    }    

    private void addViews(){

        generalLayout = new LinearLayout(this);
        generalLayout.setLayoutParams(new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT));
        generalLayout.setOrientation(LinearLayout.VERTICAL);

        title = new TextView(this);
        title.setText(R.string.sacapalabra);
        title.setTextSize(48);
        title.setGravity(Gravity.CENTER);
        generalLayout.addView(title);

        TextView[][] myTextViews = new TextView[game.getLenght()][game.getNtries()];
        createBoard(game.getNtries(), game.getLenght(), myTextViews);

        inputText = new EditText(this);
        inputText.setText(R.string.inputText);
        inputText.setGravity(Gravity.CENTER);
        inputText.setTextColor(ContextCompat.getColor(this, R.color.white));

        confirmButton = new Button(this);
        confirmButton.setBackgroundResource(R.drawable.roundbutton);
        confirmButton.setTextAlignment(View.TEXT_ALIGNMENT_CENTER);
        confirmButton.setGravity(Gravity.CENTER_VERTICAL);
        confirmButton.setCompoundDrawablesWithIntrinsicBounds(R.drawable.ic_baseline_arrow_forward, 0, 0, 0);

        LinearLayout.LayoutParams confirmButtonParams = (new LinearLayout.LayoutParams(240, 240, 1));
        confirmButtonParams.gravity = Gravity.CENTER_HORIZONTAL;
        confirmButton.setLayoutParams(confirmButtonParams);

        generalLayout.addView(inputText);
        generalLayout.addView(confirmButton);

    }

    private void createBoard(int rows, int cols, TextView[][] myTextViews) {

        // Layout para tablero

        LinearLayout boardLayout = new LinearLayout(this);
        boardLayout.setLayoutParams(new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT));
        boardLayout.setOrientation(LinearLayout.VERTICAL);
        boardLayout.setPadding(100,100,100,100);

        // Creamos los LinearLayouts horizontales y los TextViews dentro de ellos
        LinearLayout linearLayout;
        TextView textView;

        int screenWidth = Resources.getSystem().getDisplayMetrics().widthPixels;
        int marginSize = getResources().getDimensionPixelSize(R.dimen.grid_margin);
        int squareSize = (screenWidth - 2 * marginSize - (cols - 1) * 5) / cols;

        for (int i = 0; i < rows; i++) {
            linearLayout = new LinearLayout(this);
            linearLayout.setLayoutParams(new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT));
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
                textView.setTypeface(null, Typeface.BOLD);
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
}