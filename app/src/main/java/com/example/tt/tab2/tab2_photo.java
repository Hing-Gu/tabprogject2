package com.example.tt.tab2;

import android.content.Intent;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.viewpager.widget.ViewPager;

import com.example.tt.R;
import com.example.tt.helper.Utils;

import java.io.File;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;


public class tab2_photo extends Fragment {
    private ImageView selected_image;
    private ImageButton exit;
    private ViewPager viewPager;

    private int position;
    private com.example.tt.tab2.ImageAdapter ImageAdapter;
    public static final String ARG_OBJECT = "object";

    public tab2_photo(int img, ImageAdapter imageAdapter) {
        // Required empty public constructor
        position = img;
        ImageAdapter = imageAdapter;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_tab2_photo, container, false);
//        View v = inflater.inflate(R.layout.fragment_tab2_photo_element, container, false);
        int image_value = 0;

        viewPager = v.findViewById(R.id.pager);
        ImageView imgDisplay;
        Button btnClose;
        final Utils utils = new Utils(getActivity());

//        viewPager.addView(viewLayout);
        FullScreenImageAdapter adapter = new FullScreenImageAdapter(getActivity(), utils.getFilePaths());
        Log.d("Tab2 photo","hmmm");
        viewPager.setAdapter(adapter);
        Log.d("Tab2 photo","wellll");
//        return viewLayout;

        exit = v.findViewById(R.id.exitbtn);


        exit.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){
                FragmentManager fm = getActivity().getSupportFragmentManager();
//                FragmentTransaction fragmentTransaction = fm.beginTransaction();
                fm.popBackStack();
//                fragmentTransaction.replace();
            }
        });


        return v;
    }

}
