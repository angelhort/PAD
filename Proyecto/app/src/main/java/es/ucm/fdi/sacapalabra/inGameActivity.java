package es.ucm.fdi.sacapalabra;

import androidx.core.content.ContextCompat;
import androidx.loader.app.LoaderManager;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.res.Configuration;
import android.content.res.Resources;
import android.graphics.Typeface;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.util.DisplayMetrics;
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
import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
import java.util.Random;

public class inGameActivity extends BaseActivity implements WordLoaderCallbacksListener {

    private static final int WORD_LOADER_ID = 0;
    private WordLoaderCallbacks wordLoaderCallbacks;
    private SharedPreferences sharedPreferences;

    private Game game;

    private LinearLayout generalLayout;
    private LinearLayout boardLayout;
    private LinearLayout buttonsLayout;

    private TextView titleText;
    private TextView timeText;
    private EditText inputText;
    private Button submitButton;
    private TextView[][] myTextViews;
    private LinearLayout.LayoutParams layoutParams;

    private boolean colorblind;
    private boolean timeTrial;
    private boolean gameFinished;
    private CountDownTimer countDownTimer;
    private GameDBHelper dbHelper;


    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);

        dbHelper = DataBase.getDbHelper(this.getApplicationContext());

        // Recuperamos las preferencias como es habitual en todas las activities
        sharedPreferences = getSharedPreferences("MyPrefs", MODE_PRIVATE);
        String theme = sharedPreferences.getString("theme", "dark");
        String language = sharedPreferences.getString("language", "es");
        colorblind = sharedPreferences.getBoolean("colorblind",false);
        setTheme(theme);
        setLanguage(language);

        // Creamos el objeto game, que contiene la informacion de la partida
        Intent intent = getIntent();
        game = new Game(intent.getStringExtra("idioma"), intent.getStringExtra("modo"),intent.getIntExtra("intentos", 0),intent.getIntExtra("longitud", 0));
        if(game.getMode().equals("contrarreloj")) timeTrial = true;

        // Añadimos vistas a la actividad
        if (getResources().getConfiguration().orientation == Configuration.ORIENTATION_PORTRAIT)
            addViews(true);
        else
            addViews(false);

        // Solo hay 2 posibilidades para ejecutarse onCreate(), diferenciamos entre ambas

        gameFinished = false; // Este booleano solo existe por la posibilidad de rotar la pantalla cuando la partida ha terminado

        // 1º Se ejecuta tras rotar el dispositivo, la partida ya existia, debemos recuperar la información
        if (savedInstanceState != null) {
            recoverSavedInstance(savedInstanceState);
        }
        // 2º Se ejecuta la primera vez que se crea la actividad, no por cambios de configuración, establecemos
        // los parametros variables de la partida (obtener la palabra y, en caso de serlo, establecer el cronometro)
        else {
            getAPIword();
            if(timeTrial) createTimer(60000);
        }

        setContentView(generalLayout);
    }

    private void createTimer(long time) {

        if(timeTrial) {
            countDownTimer = new CountDownTimer(time, 1000) {
                public void onTick(long millisUntilFinished) {
                    // Actualizar la etiqueta de texto con el tiempo restante
                    game.setTime(millisUntilFinished);
                    timeText.setText(R.string.timeRemaining);
                    timeText.append(" ");
                    timeText.append(String.valueOf(millisUntilFinished / 1000));
                    if(String.valueOf(millisUntilFinished / 1000).equals("10"))
                        timeText.setTextColor(getResources().getColor(R.color.red,getTheme()));
                }

                @Override
                public void onFinish() {
                    timeText.setText(R.string.noTime);
                    finishGame(false);
                    saveStats(false);
                }
            }.start();
        } else {
            countDownTimer = null;
        }
    }
    private void addViews(boolean orientation) {

        generalLayout = new LinearLayout(this);
        generalLayout.setLayoutParams(new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT));

        // Es necesario comprobar la orientacion para establecer la direccion del layout general
        if(orientation) {
            generalLayout.setOrientation(LinearLayout.VERTICAL);
            layoutParams = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
            layoutParams.setMargins(0,10,0,0);
        } else {
            generalLayout.setOrientation(LinearLayout.HORIZONTAL);
            layoutParams = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT,1);
            layoutParams.setMargins(10,20,10,0);
        }
        layoutParams.gravity = Gravity.CENTER_HORIZONTAL;

        boardLayout = new LinearLayout(this);
        boardLayout.setOrientation(LinearLayout.VERTICAL);
        boardLayout.setLayoutParams(layoutParams);

        buttonsLayout = new LinearLayout(this);
        buttonsLayout.setLayoutParams(layoutParams);
        buttonsLayout.setOrientation(LinearLayout.VERTICAL);

        // Header Title
        titleText = new TextView(this);
        titleText.setLayoutParams(layoutParams);

        titleText.setText(R.string.sacapalabra);
        titleText.setTextSize(48);
        titleText.setGravity(Gravity.CENTER);

        if(orientation) generalLayout.addView(titleText);
        else buttonsLayout.addView(titleText);

        // Tiempo (solo si estamos en modo contrarreloj
        if (timeTrial) {
            timeText = new TextView(this);
            timeText.setLayoutParams(layoutParams);
            timeText.setTextSize(20);
            timeText.setGravity(Gravity.CENTER);
            buttonsLayout.addView(timeText);
        }

        // Input Text para introducir las palabras
        inputText = new EditText(this);
        inputText.setLayoutParams(layoutParams);
        inputText.setHint(R.string.inputText);
        inputText.setTextSize(28);
        inputText.setGravity(Gravity.CENTER);
        inputText.setHintTextColor(ContextCompat.getColor(this, R.color.theme_green));
        buttonsLayout.addView(inputText);

        // Boton para enviar la palabra a comprobar
        submitButton = new Button(this);
        submitButton.setBackgroundResource(R.drawable.roundbutton);
        submitButton.setTextAlignment(View.TEXT_ALIGNMENT_CENTER);
        submitButton.setGravity(Gravity.CENTER);
        submitButton.setText(R.string.confirmWord);
        submitButton.setTextSize(20);

        LinearLayout.LayoutParams confirmButtonParams = (new LinearLayout.LayoutParams(240, 240));
        confirmButtonParams.gravity = Gravity.CENTER_HORIZONTAL;
        confirmButtonParams.setMargins(0,50,0,0);
        submitButton.setLayoutParams(confirmButtonParams);
        submitButton.setOnClickListener(sendWordListener);
        buttonsLayout.addView(submitButton);

        // Finalmente, creamos el tablero según la orientación y añadimos las vistas
        createBoard(orientation);
        generalLayout.addView(boardLayout);
        generalLayout.addView(buttonsLayout);
    }
    private void createBoard(boolean orientation) {

        int rows = game.getNtries();
        int cols = game.getLenght();
        myTextViews = new TextView[game.getNtries()][game.getLenght()];

        int screenWidth,screenHeight = 0;
        int marginSize,maxSquareSize,maxSquareSize2, squareSize;

        screenWidth = (int) (0.85 * Resources.getSystem().getDisplayMetrics().widthPixels);
        screenHeight =(int) (0.9 * Resources.getSystem().getDisplayMetrics().heightPixels);

        if(orientation) {
            maxSquareSize = (screenHeight / 2)  / Math.max(cols,rows);
        } else {
            maxSquareSize = (screenWidth / 2)  / Math.max(cols,rows);
        }

        squareSize = maxSquareSize;

        LinearLayout.LayoutParams layoutParamsText = (new LinearLayout.LayoutParams(squareSize, squareSize,1));
        layoutParamsText.setMargins(4,4,4,4);

        for (int i = 0; i < rows; i++) {
            LinearLayout linearLayout = new LinearLayout(this);
            LinearLayout.LayoutParams rowLayout = (new LinearLayout.LayoutParams(squareSize*cols, ViewGroup.LayoutParams.WRAP_CONTENT));
            rowLayout.gravity = Gravity.CENTER;
            rowLayout.setMargins(0,0,0,0);

            linearLayout.setLayoutParams(rowLayout);
            linearLayout.setOrientation(LinearLayout.HORIZONTAL);
            linearLayout.setWeightSum(cols);

            for (int j = 0; j < cols; j++) {
                myTextViews[i][j] = new TextView(this);
                myTextViews[i][j].setLayoutParams(layoutParamsText);
                myTextViews[i][j].setBackgroundResource(R.drawable.textview_border_incorrect);
                myTextViews[i][j].setTextSize(24);
                myTextViews[i][j].setGravity(Gravity.CENTER);
                myTextViews[i][j].setTypeface(null, Typeface.BOLD);

                linearLayout.addView(myTextViews[i][j]);
            }

            boardLayout.addView(linearLayout);
            Log.d("InGame" ,"Creado linearLayout");
        }
    }
    private void finishGame(boolean win){

        // Borrar botones anteriores
        buttonsLayout.removeView(inputText);
        buttonsLayout.removeView(submitButton);

        // Crear texto de acierto/fallo

        TextView endGameText = new TextView(this);
        endGameText.setLayoutParams(layoutParams);
        endGameText.setTextSize(48);
        endGameText.setGravity(Gravity.CENTER);

        TextView solutionText = new TextView(this);
        solutionText.setLayoutParams(layoutParams);

        if(win) {
            endGameText.setText(R.string.winText);
            endGameText.setTextColor(getResources().getColor(R.color.green,getTheme()));
        }
        else {
            endGameText.setText(R.string.loseText);
            endGameText.setTextColor(getResources().getColor(R.color.red,getTheme()));

            solutionText.setTextSize(20);
            solutionText.setGravity(Gravity.CENTER);
            solutionText.setText(R.string.solutionText);
            solutionText.append(" ");
            solutionText.append(game.getWord());
        }

        // Boton de jugar de nuevo
        Button playAgainButton = new Button(this);
        playAgainButton.setTextAlignment(View.TEXT_ALIGNMENT_CENTER);
        playAgainButton.setGravity(Gravity.CENTER);
        playAgainButton.setText(R.string.playAgain);
        playAgainButton.setTextSize(28);
        playAgainButton.setBackgroundColor(getResources().getColor(R.color.theme_green,getTheme()));

        playAgainButton.setLayoutParams(layoutParams);
        playAgainButton.setOnClickListener(playAgainListener);

        // Boton de volver al menu principal
        Button returnMenuButton = new Button(this);
        returnMenuButton.setTextAlignment(View.TEXT_ALIGNMENT_CENTER);
        returnMenuButton.setGravity(Gravity.CENTER);
        returnMenuButton.setText(R.string.returnMenu);
        returnMenuButton.setTextSize(28);
        returnMenuButton.setCompoundDrawablesWithIntrinsicBounds(R.drawable.ic_baseline_arrow_back,0,0,0);
        returnMenuButton.setPadding(10,10,10,10);
        returnMenuButton.setBackgroundColor(getResources().getColor(R.color.grey,getTheme()));
        returnMenuButton.setLayoutParams(layoutParams);
        returnMenuButton.setOnClickListener(returnMenuListener);

        // Añadir vistas
        buttonsLayout.addView(endGameText);
        if(timeTrial) {
            buttonsLayout.removeView(timeText);
            buttonsLayout.addView(timeText);
        }
        buttonsLayout.addView(solutionText);
        buttonsLayout.addView(playAgainButton);
        buttonsLayout.addView(returnMenuButton);
        if(timeTrial)
            countDownTimer.cancel();
    }
    private void saveStats(boolean win){
        // Guardar estadisticas partida
        int plays = sharedPreferences.getInt("plays", 0);
        sharedPreferences.edit().putInt("plays", plays + 1).apply();
        plays++;
        int wins = sharedPreferences.getInt("wins", 0);
        
        // GUARDAR PARTIDA DB
        dbHelper.insertGame(game.getLanguage(), game.getMode(), game.getWord(), win ? 1 : 0, game.getNtries());

        if(win) {
            // Guardar victorias para luego hacer el porcentaje
            sharedPreferences.edit().putInt("wins", wins+1).apply();
            wins++;

            // Racha victorias
            int currentStreak = sharedPreferences.getInt("currentStreak", 0);
            sharedPreferences.edit().putInt("currentStreak", currentStreak + 1).apply();
            currentStreak++;

            // Mejor racha
            int bestStreak = sharedPreferences.getInt("bestStreak", 0);
            if(currentStreak > bestStreak) {
                sharedPreferences.edit().putInt("bestStreak", currentStreak).apply();
            }
        }
        else {
            // Racha de victorias
            sharedPreferences.edit().putInt("currentStreak", 0).apply();
        }

        //Porcentaje partidas
        sharedPreferences.edit().putFloat("percentage", ((float)wins/(float)plays)*100).apply();
        Log.d("w", String.valueOf(wins));
        Log.d("p", String.valueOf(plays));
        Log.d("%", String.valueOf((wins/plays)*100));
    }
    private void paintLetters(String palabra, int nTry) {
        String letra;
        boolean letterCorrectlyPlaced, sameLetterCorrectlyPlaced;

        for (int i = 0; i < palabra.length(); i++) {
            letra = String.valueOf(palabra.charAt(i)).toLowerCase();
            letterCorrectlyPlaced = false;
            sameLetterCorrectlyPlaced = false;

            if (game.getWord().contains(letra)) {
                if (String.valueOf(game.getWord().charAt(i)).equals(letra)) {
                    if (!colorblind) {
                        myTextViews[nTry][i].setBackgroundResource(R.drawable.textview_border_correct);
                    } else {
                        myTextViews[nTry][i].setBackgroundResource(R.drawable.textview_border_correct_colorblind);
                    }
                    letterCorrectlyPlaced = true;
                } else {
                    for (int j = 0; j < palabra.length(); j++) {
                        if (i != j && String.valueOf(palabra.charAt(j)).toLowerCase().equals(letra)) {
                            if (String.valueOf(game.getWord().charAt(j)).equals(letra)) {
                                sameLetterCorrectlyPlaced = true;
                                break;
                            }
                        }
                    }
                    if (!letterCorrectlyPlaced && !alreadyPainted(letra, i, nTry) && !sameLetterCorrectlyPlaced && ((game.getWord().indexOf(letra) < i || game.getWord().lastIndexOf(letra) > i))) {
                        myTextViews[nTry][i].setBackgroundResource(R.drawable.textview_border_wrongpos);
                    }
                }
            }
            myTextViews[nTry][i].setText(letra.toUpperCase());
        }
    }
    private boolean alreadyPainted(String letra, int index, int nTry) {
        for (int i = 0; i < index; i++) {
            if (myTextViews[nTry][i].getText().toString().toLowerCase().equals(letra)) {
                if (game.getWord().charAt(i) != letra.charAt(0)) {
                    return true;
                }
            }
        }
        return false;
    }

    private void getAPIword() {

        /* Como el juego dispone de jugabilidad local, si tenemos conexión llamaremos a la API para que nos sumistre la palabra
        * en caso contrario la palabra se tomara de un .txt local de forma aleatoria
        *
        * Para las palabras en gallego no existe API, por tanto siempre se tomarán de un txt local con palabras en gallego
        *
        * */

        // A) Hay conexion a internet
        if (isNetworkAvailable() && (game.getLanguage().equals("es") || game.getLanguage().equals("en"))) {
            Log.d("inGame", "hay internet");
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
        }
        // B) No hay conexión a internet
        else {
            Log.d("inGame", "no hay internet o idioma es gal. idioma: " + game.getLanguage());
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
                Random random = new Random();                       // Se desplaza la posición para leer de forma aleatoria
                int index = random.nextInt(words.size());
                String randomWord = words.get(index);
                game.setWord(randomWord);
                System.out.println("fichero: "+ game.getWord());
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

    }
    private boolean isNetworkAvailable() {
        ConnectivityManager connectivityManager = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo activeNetworkInfo = connectivityManager.getActiveNetworkInfo();
        Log.d("inGame", "Active network info: " + activeNetworkInfo);
        return activeNetworkInfo != null && activeNetworkInfo.isConnected();
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
    @Override
    public void onWordLoaded(String word) {
        game.setWord(word);
        Log.d("inGame", "Palabra cargada correctamente: "+word);
    }

    View.OnClickListener sendWordListener = new View.OnClickListener() {
        @Override
        public void onClick(View view) {

            String palabra = inputText.getText().toString();

            if(game.validateWord(palabra)){
                inputText.setError(null);                                               // Borramos posibles errores de palabras anteriores
                inputText.setText("");
                paintLetters(palabra,game.getActualTry());
                game.incrementTry();
                game.addWord(palabra);
                if (game.isSolution(palabra)) {
                    finishGame(true);
                    saveStats(true);
                    gameFinished = true;
                }
                else if (game.getActualTry() == game.getNtries()) {
                    finishGame(false);
                    saveStats(false);
                    gameFinished = true;
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
            finish();
            // Create Intent to start InGameActivity
            Intent intent = new Intent(inGameActivity.this, inGameActivity.class);

            // Put selected data as extras in Intent
            intent.putExtra("idioma", game.getLanguage().trim());
            intent.putExtra("modo", game.getMode().trim());
            intent.putExtra("intentos", game.getNtries());
            intent.putExtra("longitud", game.getLenght());

            // Start InGameActivity with Intent
            startActivity(intent);

        }
    };
    View.OnClickListener returnMenuListener = new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            finish();
            Intent intent = new Intent(inGameActivity.this, MainActivity.class);
            startActivity(intent);
        }
    };

    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putBoolean("gameFinished", gameFinished);
        outState.putString("currentWord", game.getWord());
        outState.putInt("actualTry", game.getActualTry());
        outState.putStringArrayList("wordsTried", game.getWordsTried());

        if(timeTrial)
            outState.putLong("actualTime", game.getTime());

    }
    private void recoverSavedInstance(Bundle savedInstanceState) {

        gameFinished = savedInstanceState.getBoolean("gameFinished");
        game.setWord(savedInstanceState.getString("currentWord"));
        game.setActualTry(savedInstanceState.getInt("actualTry"));
        game.setWordsTried(savedInstanceState.getStringArrayList("wordsTried"));

        int i = 0;
        for(String b : game.getWordsTried()){
            paintLetters(b,i);
            i++;
        }

        if(gameFinished) {
            if (game.isSolution(game.getWord( i - 1))) {
                finishGame(true);
            } else if (game.getActualTry() == game.getNtries()) {
                finishGame(false);
            }
        } else {
            if (timeTrial)
                createTimer(savedInstanceState.getLong("actualTime"));
        }
    }
}

