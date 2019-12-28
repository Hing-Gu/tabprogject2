package com.example.tt;

import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.provider.ContactsContract;
import android.util.Log;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import com.google.android.material.tabs.TabItem;
import com.google.android.material.tabs.TabLayout;


public class MainActivity extends AppCompatActivity {
    private TabLayout tabLayout;
   // private ViewPager viewPager;
    private TabItem tab1, tab2,  tab3;
    public PageAdapter pagerAdapter;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        tabLayout=(TabLayout)findViewById(R.id.tabs);
        tab1=(TabItem)findViewById(R.id.tab1);
        tab2=(TabItem)findViewById(R.id.tab2);
        tab3=(TabItem)findViewById(R.id.tab3);
        //viewPager = findViewById(R.id.viewpager);


        pagerAdapter =new PageAdapter(getSupportFragmentManager(),tabLayout.getTabCount());



        tabLayout.setOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                //viewPager.setCurrentItem(tab.getPosition());
                Fragment fr = null;
                if(tab.getPosition()==0) {
                    //pagerAdapter.notifyDataSetChanged();
                    fr = new tab1();
                }else if(tab.getPosition()==1) {
                    //pagerAdapter.notifyDataSetChanged();
                    fr = new tab2();
                } else if(tab.getPosition()==2){
                   // pagerAdapter.notifyDataSetChanged();
                    fr = new tab3();
                }
                FragmentManager fm = getSupportFragmentManager();
                FragmentTransaction fragmentTransaction = fm.beginTransaction();
                fragmentTransaction.replace(R.id.frame,fr);
                fragmentTransaction.commit();

            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {

            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {

            }
        });
        FragmentManager fm = getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = fm.beginTransaction();
        fragmentTransaction.add(R.id.frame, new tab1());
        fragmentTransaction.commit();
        //viewPager.addOnPageChangeListener(new TabLayout.TabLayoutOnPageChangeListener(tabLayout));

    }

}





