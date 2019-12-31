package com.example.tt;


import android.app.Activity;
import android.app.Application;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentStatePagerAdapter;
import androidx.fragment.app.FragmentTransaction;

import android.os.Environment;
import android.provider.MediaStore;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.GridView;
import android.widget.ImageButton;
import android.widget.ImageView;

import java.io.File;
import java.lang.reflect.Array;
import java.util.ArrayList;


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

        File mediaStorageDir = Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOWNLOADS);
//        File a = Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_PICTURES);
        Log.d("aaa",mediaStorageDir.getPath());
//        Log.d("basePath",a.getPath());
//        if (!a.exists()){
//            if(!a.mkdirs()){
//                Log.e("tab2","failed to create parent directory");
//            }
//        }

        if (!mediaStorageDir.exists()){
            if(!mediaStorageDir.mkdirs()){
                Log.e("tab2","failed to create directory");
            }
        }
        basePath = mediaStorageDir.getPath();


        imgAdapter = new ImageAdapter(getContext().getApplicationContext(),basePath);
        gridView.setAdapter(imgAdapter);

        gridView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                FragmentManager fm = getActivity().getSupportFragmentManager();
                FragmentTransaction fragmentTransaction = fm.beginTransaction();
                //Casting getitem value to integer -> can be vurnerable.
                Fragment newFragment = new tab2_photo(position, imgAdapter);
//                FragmentStatePagerAdapter swipe = DemoCollectionPagerAdapter(fm);

                fragmentTransaction.replace(R.id.frame, newFragment);
                fragmentTransaction.addToBackStack(null);
                fragmentTransaction.commit();
            }
        });
//        return inflater.inflate(R.layout.fragment_tab2, container, false);
        return v;
    }
//
//    private void getAlbum(){
//        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT){
//            try{
//                Intent intent = new Intent(Intent.ACTION_PICK);
//                intent.setType("image/*");
////                intent.putExtra(Intent.EXTRA_ALLOW_MULTIPLE, true);
//                intent.setType(MediaStore.Images.Media.CONTENT_TYPE);
//                startActivityForResult(intent, REQUEST_TAKE_ALBUM);
//            }catch(Exception e){
//                Log.e("error",e.toString());
//            }
//        }
//        else{
//            Log.e("kitkat under","..");
//        }
//    }
//
//    protected void onActivityResult(int requestCode, int resultCode, Intent data){
//        Log.i("onActivityResult","CALL");
//        super.onActivityResult(requestCode,resultCode,data);
//        switch(requestCode){
//            case REQUEST_TAKE_ALBUM:
//                Log.i("result",String.valueOf(resultCode));
//                if (resultCode == Activity.RESULT_OK){
//                    data.getData();
//                    Intent intent = new Intent(tab2.this, ImageView.class);
//
//                }
//        }
//    }
}



