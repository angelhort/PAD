package es.ucm.fdi.sacapalabra;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentStatePagerAdapter;
import androidx.viewpager.widget.PagerAdapter;
import androidx.viewpager.widget.ViewPager;

import android.os.Bundle;

public class HowToPlayActivity extends FragmentActivity {

    private static final int NUM_PAG = 5;
    private ViewPager page;
    private PagerAdapter pagerAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_how_to_play);

        // Instantiate a ViewPager and a PagerAdapter.
        page = (ViewPager) findViewById(R.id.view_pager);
        pagerAdapter = new ScreenSlidePagerAdapter(getSupportFragmentManager());
        page.setAdapter(pagerAdapter);
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