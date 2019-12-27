package com.example.tt;


import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.GridView;

import java.util.ArrayList;


/**
 * A simple {@link Fragment} subclass.
 */
public class tab2 extends Fragment {
    private imageAdapter imgAdapter;
    private GridView gridView;

    public tab2() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View v = inflater.inflate(R.layout.fragment_tab2,container,false);
        gridView = (GridView)v.findViewById(R.id.mygalleryid);

        imgAdapter = new imageAdapter();
        imgAdapter.addItem(new galleryitem());
        Log.d("MainActivity",Integer.toString(imgAdapter.getCount()));
        imgAdapter.addItem(new galleryitem());
        Log.d("MainActivity",Integer.toString(imgAdapter.getCount()));
        gridView.setAdapter(imgAdapter);
        Log.d("MainActivity","CCC");
        //viewPager.setAdapter(pagerAdapter);

        return inflater.inflate(R.layout.fragment_tab2, container, false);
    }

    class imageAdapter extends BaseAdapter {
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
            imageviewer singerViewer = new imageviewer(getContext().getApplicationContext());
            singerViewer.setItem(items.get(i));
            return singerViewer;
        }
    }

}
