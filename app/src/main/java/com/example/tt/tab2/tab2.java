package com.example.tt.tab2;


import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.GridView;

import com.example.tt.R;
import com.example.tt.SectionPageAdapter;
import com.example.tt.helper.AppConstant;

import java.io.File;


/**
 * A simple {@link Fragment} subclass.
 */
public class tab2 extends Fragment {
    private ImageAdapter imgAdapter;
    private GridView gridView;
    final int REQUEST_TAKE_ALBUM = 1;
    String basePath = null;


    public tab2() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View v = inflater.inflate(R.layout.fragment_tab2, container, false);
        gridView = v.findViewById(R.id.mygalleryid);

//        File mediaStorageDir = Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOWNLOADS);
        File directory = new File(android.os.Environment.getExternalStorageDirectory() + File.separator + AppConstant.PHOTO_ALBUM);
//        File a = Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_PICTURES);
//        Log.d("aaa",mediaStorageDir.getPath());

        if (!directory.exists()){
            if(!directory.mkdirs()){
                Log.e("tab2","failed to create directory");
            }
        }
        basePath = directory.getPath();


        imgAdapter = new ImageAdapter(getContext().getApplicationContext());
        gridView.setAdapter(imgAdapter);

        //Set activity to send parameter to event listener
        final Activity activity = getActivity();

        gridView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
//                Intent i = new Intent(_activity, FullScreenViewActivity.class);
//                i.putExtra("position", _postion);
//                _activity.startActivity(i);
                Intent i = new Intent(activity, FullScreenViewActivity.class);
                i.putExtra("position", position);
                activity.startActivity(i);
            }
        });
//        return inflater.inflate(R.layout.fragment_tab2, container, false);
        return v;
    }
}



