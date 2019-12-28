package com.example.tt;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ImageView;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;


public class tab2_photo extends Fragment {
    private ImageView selected_image;
    private ImageButton exit;
    private int img_int;

    public tab2_photo(int img) {
        // Required empty public constructor
        img_int = img;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_tab2_photo, container, false);
        selected_image = v.findViewById(R.id.selected_photo);
        selected_image.setImageResource(img_int);
        selected_image.setScaleType(ImageView.ScaleType.CENTER_CROP);

        exit = v.findViewById(R.id.exitbtn);

//        exit.setOnClickListener(new View.OnClickListener(){
//            @Override
//            public void onClick(){
//                FragmentManager fm = getActivity().getSupportFragmentManager();
////                FragmentTransaction fragmentTransaction = fm.beginTransaction();
//                fm.popBackStack();
////                fragmentTransaction.replace();
//            }
//        });
        return v;
    }
}