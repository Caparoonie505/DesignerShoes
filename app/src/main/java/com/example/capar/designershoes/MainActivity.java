package com.example.capar.designershoes;

import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;

public class MainActivity extends AppCompatActivity {

    private static final String TAG = "MainActivity";

    private SectionStatePageAdapter mSectionStatePageAdapter;
    private CustomViewPager mViewPager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Log.d(TAG, "onCreate: Started");

        mSectionStatePageAdapter = new SectionStatePageAdapter(getSupportFragmentManager());

        mViewPager = (CustomViewPager) findViewById(R.id.container);

        //Disable swipe transitions
        mViewPager.setPagingEnabled(false);

        setupViewPager(mViewPager);

    }

    private void setupViewPager(ViewPager viewPager){
        SectionStatePageAdapter adapter = new SectionStatePageAdapter(getSupportFragmentManager());
        mViewPager.setOffscreenPageLimit(7);
        adapter.addFragment(new fragmentMenu(), "fragmentMenu");
        adapter.addFragment(new fragmentMensCasual(), "fragmentMensCasual");
        adapter.addFragment(new fragmentMensActive(),"fragmentMensActive");
        adapter.addFragment(new fragmentWomensCasual(),"fragmentWomensCasual");
        adapter.addFragment(new fragmentWomensActive(),"fragmentWomensActive");
        viewPager.setAdapter(adapter);
    }

    public void setViewPager(int fragmentNumber){ mViewPager.setCurrentItem(fragmentNumber);}

    @Override
    public void onBackPressed() {

        if(!(mViewPager.getCurrentItem() == 0)){
            mViewPager.setCurrentItem(0);
        }else{
            System.exit(0);
        }

    }
}
