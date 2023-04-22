package es.ucm.fdi.sacapalabra;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;

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

public class inGameActivity extends AppCompatActivity implements WordLoaderCallbacksListener {

    private static final int WORD_LOADER_ID = 0;

    private SharedPreferences sharedPreferences;

    private WordLoaderCallbacks wordLoaderCallbacks;

    private Game game;
    private LinearLayout generalLayout;
    private TextView title;
    private EditText inputText;
    private Button submitButton;
    private TextView[][] myTextViews;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);

        // Recuperamos las preferencias
        sharedPreferences = getSharedPreferences("MyPrefs", MODE_PRIVATE);
        String theme = sharedPreferences.getString("theme", "dark");
        // String colourBlind = sharedPreferences.getString("colorBlind","false");
        setTheme(theme);

        // Create Game object
        Intent intent = getIntent();
        game = new Game(intent.getStringExtra("idioma"), intent.getStringExtra("modo"),intent.getIntExtra("intentos", 0),intent.getIntExtra("longitud", 0));

        // Check for internet connectivity
        /*if (isNetworkAvailable() && (game.getLanguage().equals("es") || game.getLanguage().equals("en"))) {
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
            Log.d("game", "no hay internet o idioma es gal. idioma: "+game.getLanguage());
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
        }*/
        myTextViews = new TextView[game.getNtries()][game.getLenght()];
        addViews();

    }

    private void addViews(){

        generalLayout = new LinearLayout(this);
        generalLayout.setLayoutParams(new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT));
        generalLayout.setOrientation(LinearLayout.VERTICAL);

        // Header Title
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

        // Input Text for words
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


        // Submit button to send the word
        submitButton = new Button(this);
        submitButton.setBackgroundResource(R.drawable.roundbutton);
        submitButton.setTextAlignment(View.TEXT_ALIGNMENT_CENTER);
        submitButton.setGravity(Gravity.CENTER);
        submitButton.setText(R.string.confirmWord);
        submitButton.setTextSize(20);

        LinearLayout.LayoutParams confirmButtonParams = (new LinearLayout.LayoutParams(240, 240, 1));
        confirmButtonParams.gravity = Gravity.CENTER_HORIZONTAL;
        confirmButtonParams.setMargins(0,50,0,0);
        submitButton.setLayoutParams(confirmButtonParams);
        submitButton.setOnClickListener(sendWordListener);


        // Add final views
        generalLayout.addView(inputText);
        generalLayout.addView(submitButton);

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
                textView.setBackgroundResource(R.drawable.textview_border_incorrect);
                textView.setTextSize(24);
                textView.setGravity(Gravity.CENTER);
                textView.setTypeface(null, Typeface.BOLD);

                myTextViews[i][j] = textView;
                linearLayout.addView(textView);
            }

            boardLayout.addView(linearLayout);
            Log.d("InGame" ,"Creado linearLayout");
        }
        generalLayout.addView(boardLayout);
        setContentView(generalLayout);
    }
    private void finishGame(boolean win){

        // Borrar antiguos botones
        // Añadir botones de acierto o fallo y sus posibilidades
        // Crear listener con las acciones de los botones debidos

        generalLayout.removeView(inputText);
        generalLayout.removeView(submitButton);

        // Crear texto de acierto/fallo

        TextView endGameText = new TextView(this);
        LinearLayout.LayoutParams endGameLayoutParams = (new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT, 1));
        endGameLayoutParams.gravity = Gravity.CENTER_HORIZONTAL;
        endGameLayoutParams.setMargins(0,50,0,0);
        endGameText.setLayoutParams(endGameLayoutParams);
        endGameText.setTextSize(48);
        endGameText.setGravity(Gravity.CENTER);

        if(win) {
            endGameText.setText(R.string.winText);
            endGameText.setTextColor(getResources().getColor(R.color.green,getTheme()));
        }
        else {
            endGameText.setText(R.string.loseText);
            endGameText.setTextColor(getResources().getColor(R.color.red,getTheme()));
        }

        // Crear boton de jugar de nuevo

        Button playAgainButton = new Button(this);
        playAgainButton.setTextAlignment(View.TEXT_ALIGNMENT_CENTER);
        playAgainButton.setGravity(Gravity.CENTER);
        playAgainButton.setText(R.string.playAgain);
        playAgainButton.setTextSize(28);
        playAgainButton.setBackgroundColor(getResources().getColor(R.color.theme_green,getTheme()));
        playAgainButton.setTextColor(getResources().getColor(R.color.white,getTheme()));

        LinearLayout.LayoutParams playAgainLayoutParams = (new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT, 1));
        playAgainLayoutParams.gravity = Gravity.CENTER_HORIZONTAL;
        playAgainLayoutParams.setMargins(0,50,0,0);
        playAgainButton.setLayoutParams(playAgainLayoutParams);
        playAgainButton.setOnClickListener(playAgainListener);

        // Crear boton de volver al menu principal

        Button returnMenuButton = new Button(this);
        returnMenuButton.setTextAlignment(View.TEXT_ALIGNMENT_CENTER);
        returnMenuButton.setGravity(Gravity.CENTER);
        returnMenuButton.setText(R.string.returnMenu);
        returnMenuButton.setTextSize(28);
        returnMenuButton.setCompoundDrawablesWithIntrinsicBounds(R.drawable.ic_baseline_arrow_back,0,0,0);
        returnMenuButton.setPadding(10,10,10,10);
        returnMenuButton.setBackgroundColor(getResources().getColor(R.color.grey,getTheme()));

        LinearLayout.LayoutParams returnMenuLayoutParams = (new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT, 1));
        returnMenuLayoutParams.gravity = Gravity.CENTER_HORIZONTAL;
        returnMenuLayoutParams.setMargins(0,50,0,0);
        returnMenuButton.setLayoutParams(returnMenuLayoutParams);
        returnMenuButton.setOnClickListener(returnMenuListener);

        // Añadir vistas
        generalLayout.addView(endGameText);
        generalLayout.addView(playAgainButton);
        generalLayout.addView(returnMenuButton);

    }

    private void repaintLetters(String palabra, int nTry){

        String letra;

        for(int i = 0; i < palabra.length();i++) {
            letra = String.valueOf(palabra.charAt(i));
            if(game.getWord().contains(letra)) {
                if (String.valueOf(game.getWord().charAt(i)).equals(letra)) {
                    myTextViews[nTry][i].setBackgroundResource(R.drawable.textview_border_correct);
                } else {
                    myTextViews[nTry][i].setBackgroundResource(R.drawable.textview_border_wrongpos);
                }
            }
            myTextViews[nTry][i].setText(letra.toUpperCase());
        }
    }
    private void setTheme(String theme){
        if (theme.equals("dark")) {
            setTheme(R.style.Theme_Default);
        } else {
            setTheme(R.style.Theme_White);
        }
    }
    private boolean isNetworkAvailable() {
        ConnectivityManager connectivityManager = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo activeNetworkInfo = connectivityManager.getActiveNetworkInfo();
        Log.d("InGame", "Active network info: " + activeNetworkInfo);
        return activeNetworkInfo != null && activeNetworkInfo.isConnected();
    }
    @Override
    public void onWordLoaded(String word) {
        game.setWord(word);
        System.out.println(game.getWord());
    }

    View.OnClickListener sendWordListener = new View.OnClickListener() {
        @Override
        public void onClick(View view) {

            String palabra = inputText.getText().toString();

            if(game.validateWord(palabra)){
                inputText.setError(null);                                               // Borramos posibles errores de palabras anteriores
                repaintLetters(palabra,game.getActualTry());
                game.incrementTry();

                if (game.isSolution(palabra)) {
                    finishGame(true);
                    // GUARDAR PARTIDA DB
                }
                else if (game.getActualTry() == game.getNtries()) {
                    finishGame(false);
                    // GUARDAR PARTIDA DB
                }

            }
            else {
                inputText.setError(getResources().getString(R.string.inputError));
            }
        }
    };
    View.OnClickListener playAgainListener = new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            recreate();
        }
    };
    View.OnClickListener returnMenuListener = new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            Intent intent = new Intent(inGameActivity.this, MainActivity.class);
            startActivity(intent);
            finish();
        }
    };
}