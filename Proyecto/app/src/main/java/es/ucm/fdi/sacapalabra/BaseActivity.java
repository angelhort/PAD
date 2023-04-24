package es.ucm.fdi.sacapalabra;

import android.content.Intent;
import androidx.appcompat.app.AppCompatActivity;

public class BaseActivity extends AppCompatActivity {

    @Override
    public void startActivity(Intent intent) {
        super.startActivity(intent);
        overridePendingTransition(R.transition.fade_in, R.transition.fade_out);
    }
}
