package com.example.tt;


import android.app.Application;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.GridView;
import android.widget.ImageView;

import java.util.ArrayList;


/**
 * A simple {@link Fragment} subclass.
 */
public class tab2 extends Fragment {
    private ImageAdapter imgAdapter;
    private GridView gridView;

    public tab2() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View v = inflater.inflate(R.layout.fragment_tab2,container,true);
        gridView = v.findViewById(R.id.mygalleryid);

        imgAdapter = new ImageAdapter(getContext().getApplicationContext());
        gridView.setAdapter(imgAdapter);

        return inflater.inflate(R.layout.fragment_tab2, container, false);
    }
}

