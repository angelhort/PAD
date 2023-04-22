package es.ucm.fdi.sacapalabra;

import android.content.Intent;

public class Game {

    private String word;
    private String language;
    private String mode;
    private int ntries;
    private int lenght;

    private int actualTry;

    // private WordLoaderCallbacks wordLoaderCallbacks;

    public Game(String idioma, String modo, int intentos, int longitud){
        language = idioma;
        mode = modo;
        ntries = intentos;
        lenght = longitud;
        actualTry = 0;
        word = "torre";
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
    public int getActualTry(){return actualTry;}
    public void incrementTry(){actualTry++;}

    public boolean validateWord(String palabra){

        boolean correct = true;

        if(lenght != palabra.length())
           correct = false;
        if(palabra.matches(".*\\d+.*"))
            correct = false;
        return correct;
    }
    public boolean isSolution(String palabra){
        return word.equals(palabra);
    }

}
