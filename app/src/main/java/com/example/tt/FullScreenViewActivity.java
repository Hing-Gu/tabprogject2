package com.example.tt;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;

import androidx.viewpager.widget.ViewPager;

import com.example.tt.helper.Utils;


public class FullScreenViewActivity extends Activity {

    private ViewPager viewPager;
    private FullScreenImageAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.fragment_tab2_photo);

        Utils utils = new Utils(this);

        viewPager = findViewById(R.id.pager);
        adapter = new FullScreenImageAdapter(this, utils.getFilePaths());
        viewPager.setAdapter(adapter);
        ImageButton exitbtn = findViewById(R.id.exitbtn);
        exitbtn.setOnClickListener(new Button.OnClickListener(){
            @Override
            public void onClick(View view){
                finish();
            }
        });
    }

    @Override
    protected void onStart() {
        super.onStart();
        int pos = getIntent().getIntExtra("position", 0);
        viewPager.setCurrentItem(pos);
    }
}

//public class FullScreenViewActivity extends Activity {
//
//    private Utils utils;
//    private FullScreenImageAdapter adapter;
//    private ViewPager viewPager;
//
//    @Override
//    protected void onCreate(Bundle savedInstanceState) {
//        super.onCreate(savedInstanceState);
//        setContentView(R.layout.fragment_tab2_photo);
//
//        viewPager = (ViewPager) findViewById(R.id.pager);
//
//        utils = new Utils(getApplicationContext());
//
//        Intent i = getIntent();
//        int position = i.getIntExtra("position", 0);
//
//        adapter = new FullScreenImageAdapter(FullScreenViewActivity.this,
//                utils.getFilePaths());
//
//        viewPager.setAdapter(adapter);
//
//        // displaying selected image first
//        viewPager.setCurrentItem(position);
//    }
//}