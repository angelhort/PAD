package es.ucm.fdi.sacapalabra;

import android.net.Uri;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Reader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

public class ConnectionUtils {

    public static String getWord(String length, String language){

        final String API_URL_ES = "https://clientes.api.greenborn.com.ar/public-random-word?";
        final String API_URL_EN = "";
        Uri resultUri;

        if(language.equals("es")){
            resultUri = Uri.parse(API_URL_ES).buildUpon()
                    .appendQueryParameter("c", "1")
                    .appendQueryParameter("l", length)
                    .build();
        }else{
            resultUri = Uri.parse(API_URL_ES).buildUpon()
                    .appendQueryParameter("c", "1")
                    .appendQueryParameter("l", length)
                    .build();
        }


        String url = resultUri.toString();

        URL wordUrl = null;
        InputStream input = null;
        HttpURLConnection conn = null;
        String resultString = "";

        try{
            wordUrl = new URL(url);
            conn = (HttpURLConnection) wordUrl.openConnection();
            conn.setRequestMethod("GET");
            conn.connect();

            int responseCode = conn.getResponseCode();
            input = conn.getInputStream();
            resultString = convertInputToString(input);
        } catch (MalformedURLException e){
            throw new RuntimeException(e);
        } catch (IOException e){
            throw new RuntimeException(e);
        }

        finally{
            conn.disconnect();
            if(input != null){
                try{
                    input.close();
                } catch (IOException e){
                    e.printStackTrace();
                }
            }
        }

        return removeBracketsAndQuotes(resultString);
    }

    private static String convertInputToString(InputStream input) throws IOException {

        Reader read = new InputStreamReader(input);
        BufferedReader buff = new BufferedReader(read);
        StringBuilder builder = new StringBuilder();

        String line;
        while((line = buff.readLine()) != null){
            builder.append(line);
            builder.append("\n");
        }

        return builder.toString();
    }

    public static String removeBracketsAndQuotes(String input) {
        return input.replaceAll("[\\[\\]\"]", "");
    }

}
