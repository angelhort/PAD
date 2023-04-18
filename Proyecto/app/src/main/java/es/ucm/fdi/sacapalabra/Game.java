package es.ucm.fdi.sacapalabra;

import android.content.Intent;

public class Game {

    private String word;
    private String language;
    private String mode;
    private int ntries;
    private int lenght;

    // private WordLoaderCallbacks wordLoaderCallbacks;

    public Game(String idioma, String modo, int intentos, int longitud){
        language = idioma;
        mode = modo;
        ntries = intentos;
        lenght = longitud;
    }

    public void setWord(String palabra){
        word = palabra;
    }

    public String getWord(){
        return word;
    }
    public String getLanguage(){
        return language;
    }
    public String getMode(){
        return mode;
    }
    public int getNtries(){
        return ntries;
    }
    public int getLenght(){
        return lenght;
    }
}
