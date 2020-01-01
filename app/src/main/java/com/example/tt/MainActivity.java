package com.example.tt;

import android.Manifest;
import android.content.Intent;
import android.os.Bundle;
import android.widget.FrameLayout;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.viewpager.widget.ViewPager;

import com.example.tt.tab1.tab1;
import com.example.tt.tab1.tab1_addcontacts;
import com.example.tt.tab2.tab2;
import com.example.tt.tab3.tab3;
import com.google.android.material.tabs.TabItem;
import com.google.android.material.tabs.TabLayout;


public class MainActivity extends AppCompatActivity {
    private TabLayout tabLayout;
    private TabItem tab1, tab2, tab3;
    public SectionPageAdapter pagerAdapter;
    private FrameLayout frame;
    private ViewPager mViewpager;
    SectionPageAdapter adapter = new SectionPageAdapter(getSupportFragmentManager());


    @SuppressWarnings("ClickableViewAccessibility")


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        tabLayout = findViewById(R.id.tabs);
        tab1 = findViewById(R.id.tab1);
        tab2 = findViewById(R.id.tab2);
        tab3 = findViewById(R.id.tab3);

        ActivityCompat.requestPermissions(this,
                new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE, Manifest.permission.READ_EXTERNAL_STORAGE, Manifest.permission_group.CONTACTS}, 1);


//        mViewpager = findViewById(R.id.frame);

//        tabLayout.setupWithViewPager(mViewpager);


        FragmentManager fm = getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = fm.beginTransaction();
//        fragmentTransaction.add(R.id.frame, new tab1());
        fragmentTransaction.add(R.id.outerfragment, new fragment_viewpager());
        fragmentTransaction.commit();
    }

}





