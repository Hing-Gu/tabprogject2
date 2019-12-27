package com.example.tt;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.viewpager.widget.PagerAdapter;
import androidx.viewpager.widget.ViewPager;

import android.app.Application.*;
import android.content.Context;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.GridView;

import com.google.android.material.tabs.TabItem;
import com.google.android.material.tabs.TabLayout;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {
    private TabLayout tabLayout;
   // private ViewPager viewPager;
    private TabItem tab1, tab2,  tab3;
    public PageAdapter pagerAdapter;
    private GridView gridView;
    private imageAdapter imgAdapter;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);



        tabLayout=(TabLayout)findViewById(R.id.tabs);
        tab1=(TabItem)findViewById(R.id.tab1);
        tab2=(TabItem)findViewById(R.id.tab2);
        tab3=(TabItem)findViewById(R.id.tab3);
        gridView = (GridView)findViewById(R.id.mygalleryid);
        //viewPager = findViewById(R.id.viewpager);


        pagerAdapter =new PageAdapter(getSupportFragmentManager(),tabLayout.getTabCount());

        imgAdapter = new imageAdapter();
        imgAdapter.addItem(new galleryitem(R.drawable.ic_launcher_background));
        Log.d("MainActivity",Integer.toString(imgAdapter.getCount()));
        imgAdapter.addItem(new galleryitem(R.drawable.ic_launcher_background));
        Log.d("MainActivity",Integer.toString(imgAdapter.getCount()));
        gridView.setAdapter(imgAdapter);
        Log.d("MainActivity","CCC");
        //viewPager.setAdapter(pagerAdapter);

        tabLayout.setOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                //viewPager.setCurrentItem(tab.getPosition());
                if(tab.getPosition()==0) {
                    pagerAdapter.notifyDataSetChanged();
                }else if(tab.getPosition()==1) {
                    pagerAdapter.notifyDataSetChanged();
                } else if(tab.getPosition()==2){
                            pagerAdapter.notifyDataSetChanged();
                }

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
        fragmentTransaction.add(R.id.frame, new tab2());
        fragmentTransaction.add(R.id.frame, new tab3());
        fragmentTransaction.commit();
        //viewPager.addOnPageChangeListener(new TabLayout.TabLayoutOnPageChangeListener(tabLayout));

    }

    class imageAdapter extends BaseAdapter{
//        ArrayList<galleryitem> items = new ArrayList<galleryitem>();
        ArrayList<galleryitem> items = new ArrayList<>();
        @Override
        public int getCount() {
            return items.size();
        }

        public void addItem(galleryitem singerItem){
            items.add(singerItem);
        }

        @Override
        public galleryitem getItem(int i) {
            return items.get(i);
        }

        @Override
        public long getItemId(int i) {
            return i;
        }

        @Override
        public View getView(int i, View view, ViewGroup viewGroup) {
            imageviewer singerViewer = new imageviewer(getApplicationContext());
//            imageviewer singerViewer = new imageviewer(this);
            singerViewer.setItem(items.get(i));
            return singerViewer;
        }
    }

}





