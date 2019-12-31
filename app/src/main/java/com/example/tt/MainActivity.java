package com.example.tt;

import android.os.Bundle;
import android.util.Log;
import android.widget.FrameLayout;
import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.viewpager.widget.ViewPager;

import com.google.android.material.tabs.TabItem;
import com.google.android.material.tabs.TabLayout;


public class MainActivity extends AppCompatActivity {
    private TabLayout tabLayout;
    private TabItem tab1, tab2,  tab3;
    public SectionPageAdapter pagerAdapter;
    private FrameLayout frame;
    private ViewPager mViewpager;
    SectionPageAdapter adapter = new SectionPageAdapter(getSupportFragmentManager());


    @SuppressWarnings("ClickableViewAccessibility")


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        tabLayout=(TabLayout)findViewById(R.id.tabs);
        tab1=(TabItem)findViewById(R.id.tab1);
        tab2=(TabItem)findViewById(R.id.tab2);
        tab3=(TabItem)findViewById(R.id.tab3);

        mViewpager = (ViewPager) findViewById(R.id.frame);
        setupViewPager(mViewpager);

        tabLayout.setupWithViewPager(mViewpager);


        FragmentManager fm = getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = fm.beginTransaction();
        fragmentTransaction.add(R.id.frame, new tab1());
        fragmentTransaction.commit();
    }

    public void setupViewPager(ViewPager viewPager) {
        adapter.addFragment(new tab1(), "추천");
        adapter.addFragment(new tab2(), "오늘의 추천");
        adapter.addFragment(new tab3(), "최근 등록");
        viewPager.setAdapter(adapter);
    }
}





