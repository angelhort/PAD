package es.ucm.fdi.sacapalabra;

import java.lang.reflect.Array;
import java.util.ArrayList;

public class Game {

    private String word;
    private String language;
    private String mode;
    private int ntries;
    private int lenght;
    private long time;
    private int actualTry;

    private ArrayList<String> wordsTried;

    public Game(String idioma, String modo, int intentos, int longitud){
        language = idioma;
        mode = modo;
        ntries = intentos;
        lenght = longitud;
        actualTry = 0;
        wordsTried = new ArrayList<>();
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
    public int getActualTry(){
        return actualTry;
    }
    public void setActualTry(int intento){
        actualTry = intento;
    }
    public void incrementTry(){actualTry++;}
    public void setTime(long tiempo){
        time = tiempo;
    }
    public long getTime(){
        return time;
    }

    public ArrayList<String> getWordsTried(){
        return wordsTried;
    }
    public void setWordsTried(ArrayList<String> palabras){
        wordsTried.addAll(palabras);
    }
    public void addWord(String palabra){
        wordsTried.add(palabra);
    }

    public boolean validateWord(String palabra){

        boolean correct = true;

        if (lenght != palabra.length())
           correct = false;
        if (palabra.matches(".*\\d+.*"))
            correct = false;
        return correct;
    }
    public boolean isSolution(String palabra){
        return word.trim().equals(palabra.toLowerCase().trim());
    }

}
