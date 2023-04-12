package es.ucm.fdi.sacapalabra;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.ToggleButton;

public class ConfigActivity extends AppCompatActivity {

    private ToggleButton myToggleButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_config);

    }
}