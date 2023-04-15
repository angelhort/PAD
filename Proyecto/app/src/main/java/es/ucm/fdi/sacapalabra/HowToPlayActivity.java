package es.ucm.fdi.sacapalabra;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentStatePagerAdapter;
import androidx.viewpager.widget.PagerAdapter;
import androidx.viewpager.widget.ViewPager;

import android.content.Intent;
import android.content.SharedPreferences;
import android.content.res.Configuration;
import android.content.res.Resources;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.View;
import android.widget.Button;

import java.util.Locale;

public class HowToPlayActivity extends FragmentActivity {

    private static final int NUM_PAG = 5;
    private ViewPager page;
    private PagerAdapter pagerAdapter;

    private Button jugar;

    private View layout;

    private SharedPreferences sharedPreferences;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_how_to_play);
        layout = findViewById(R.id.howtoplay_layout);

        // Recuperamos las preferencias
        sharedPreferences = getSharedPreferences("MyPrefs", MODE_PRIVATE);

        String theme = sharedPreferences.getString("theme", "dark");
        String language = sharedPreferences.getString("language", "es");
        Locale locale;

        // Tema
        if (theme.equals("dark")) {
            setTheme(R.style.Theme_Default);
            layout.setBackgroundResource(R.drawable.bg_dark);
        } else {
            setTheme(R.style.Theme_White);
            layout.setBackgroundResource(R.drawable.bg_white);
        }

        // Idioma
        if (language.equals("es")) {
            locale = new Locale("es");
            Resources res = getResources();
            DisplayMetrics dm = res.getDisplayMetrics();
            Configuration conf = res.getConfiguration();
            conf.locale = locale;
            res.updateConfiguration(conf, dm);
            Log.d("miaaaau","recreate");

        } else {
            locale = new Locale("en");
            Resources res = getResources();
            DisplayMetrics dm = res.getDisplayMetrics();
            Configuration conf = res.getConfiguration();
            conf.locale = locale;
            res.updateConfiguration(conf, dm);
            Log.d("miau","recreate");
        }

        // Instantiate a ViewPager and a PagerAdapter.
        page = (ViewPager) findViewById(R.id.view_pager);
        jugar = findViewById(R.id.button5);
        jugar.setVisibility(View.GONE);
        pagerAdapter = new ScreenSlidePagerAdapter(getSupportFragmentManager());
        page.setAdapter(pagerAdapter);

        page.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
                // No se usa en este caso
            }

            @Override
            public void onPageSelected(int position) {
                if (position == 4) {
                    jugar.setVisibility(View.VISIBLE);
                } else {
                    jugar.setVisibility(View.INVISIBLE);
                }
            }

            @Override
            public void onPageScrollStateChanged(int state) {
                // No se usa en este caso
            }
        });

        jugar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(HowToPlayActivity.this, MainActivity.class);
                startActivity(intent);
            }
        });
    }

    private class ScreenSlidePagerAdapter extends FragmentStatePagerAdapter {
        public ScreenSlidePagerAdapter(FragmentManager fm) {
            super(fm);
        }

        @Override
        public Fragment getItem(int position) {
            if(position == 0){
                return new HowToPlayFragment1();
            }

            else if (position == 1){
                return new HowToPlayFragment2();
            }

            else if (position == 2){
                return new HowToPlayFragment3();
            }

            else if (position == 3){
                return new HowToPlayFragment4();
            }

            else if (position == 4){
                return new HowToPlayFragment5();
            }
            return null;
        }

        @Override
        public int getCount() {
            return 5;
        }
    }

}