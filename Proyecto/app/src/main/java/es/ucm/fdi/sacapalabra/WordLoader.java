package es.ucm.fdi.sacapalabra;

import android.content.Context;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.loader.content.AsyncTaskLoader;

public class WordLoader extends AsyncTaskLoader<String> {

    private String length;
    private String language;


    public WordLoader(@NonNull Context context, String length, String language){
        super(context);
        this.length = length;
        this.language = language;
    }

    @Nullable
    @Override
    public String loadInBackground(){
        return APIConectionUtils.getWord(this.length, this.language);
    }

    @Override
    protected void onStartLoading() {forceLoad();}
}
