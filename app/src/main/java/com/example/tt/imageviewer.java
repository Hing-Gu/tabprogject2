package com.example.tt;

import android.content.Context;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.widget.ImageView;
import android.widget.LinearLayout;

import androidx.annotation.Nullable;

public class imageviewer extends LinearLayout {
    ImageView imageView;

    public imageviewer(Context context) {
        super(context);

        init(context);
    }

    public imageviewer(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);

        init(context);
    }

    public void init(Context context){
        LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        inflater.inflate(R.layout.galleryitem,this,true);

        imageView = (ImageView) findViewById(R.id.imageView);
    }

    public void setItem(galleryitem img){
        imageView.setImageResource(img.getImage());
    }
}