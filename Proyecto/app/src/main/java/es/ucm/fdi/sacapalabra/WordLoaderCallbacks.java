package es.ucm.fdi.sacapalabra;

import android.content.Context;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.loader.app.LoaderManager;
import androidx.loader.content.Loader;

public class WordLoaderCallbacks implements LoaderManager.LoaderCallbacks<String> {

    private Context context;
    private WordLoaderCallbacksListener listener;

    public static final String LENGTH = "5";

    public static final String LANGUAGE = "es";

    public WordLoaderCallbacks(Context context, WordLoaderCallbacksListener listener) {
        this.context = context;
        this.listener = listener;
    }

    @NonNull
    @Override
    public Loader<String> onCreateLoader(int id, @Nullable Bundle args) {
        WordLoader wordLoader = new WordLoader(context, args.getString(LENGTH), args.getString(LANGUAGE));
        return wordLoader;
    }

    @Override
    public void onLoadFinished(@NonNull Loader<String> loader, String data) {
        listener.onWordLoaded(data);
    }

    @Override
    public void onLoaderReset(@NonNull Loader<String> loader) {

    }


}
