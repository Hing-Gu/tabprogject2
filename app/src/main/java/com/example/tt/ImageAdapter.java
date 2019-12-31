package com.example.tt;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.provider.MediaStore;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.LinearLayout;

import androidx.annotation.Nullable;

import java.io.InputStream;
import java.util.ArrayList;

import static androidx.core.app.ActivityCompat.startActivityForResult;



public class ImageAdapter extends BaseAdapter {
    private Context mContext;
    private ArrayList<String> pathList;
    static final int GALLERY_REQUEST_CODE = 1;
    String imgDecodableString;
    // Keep all Images in array
//    public Integer[] mThumbIds = {
//            R.drawable.maxresdefault , R.drawable.cross
//            ,R.drawable.maxresdefault, R.drawable.cross
//            ,R.drawable.maxresdefault, R.drawable.maxresdefault
//            ,R.drawable.maxresdefault, R.drawable.maxresdefault
//            ,R.drawable.maxresdefault, R.drawable.maxresdefault
//            ,R.drawable.maxresdefault, R.drawable.maxresdefault
//            ,R.drawable.maxresdefault, R.drawable.maxresdefault
//            ,R.drawable.maxresdefault, R.drawable.maxresdefault
//            ,R.drawable.maxresdefault, R.drawable.maxresdefault
//            ,R.drawable.maxresdefault, R.drawable.maxresdefault
//            ,R.drawable.maxresdefault, R.drawable.maxresdefault
//            ,R.drawable.maxresdefault, R.drawable.maxresdefault
//
//    };

    // Constructor
    public ImageAdapter(Context c){

        mContext = c;
        pathList = getImagesPath(mContext);
    }

    public static ArrayList<String> getImagesPath(Context context) {
        Uri uri;
        ArrayList<String> listOfAllImages = new ArrayList<String>();
        Cursor cursor;
        int column_index_data, column_index_folder_name;
        String PathOfImage = null;
        uri = android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI;

        String[] projection = {MediaStore.MediaColumns.DATA,
                MediaStore.Images.Media.BUCKET_DISPLAY_NAME};
        cursor = context.getContentResolver().query(uri, projection, null, null, null);

        column_index_data = cursor.getColumnIndexOrThrow(MediaStore.MediaColumns.DATA);
        column_index_folder_name = cursor
                .getColumnIndexOrThrow(MediaStore.Images.Media.BUCKET_DISPLAY_NAME);

//        cursor.moveToFirst();

        while (cursor.moveToNext()) {
            PathOfImage = cursor.getString(column_index_data);
            listOfAllImages.add(PathOfImage);
        }

        cursor.close();
        return listOfAllImages;
    }


    @Override
    public int getCount() {
//        return mThumbIds.length;
        return pathList.size();
    }

    @Override
    public Object getItem(int position) {
//        try {
//            return mThumbIds[position];
//        }catch(Exception ArrayIndexOutOfBoundsExcetion){
//            return -1;
//        }
        return pathList.get(position);
    }

    @Override
    public long getItemId(int position) {
//        return 0;
        return position;
//        return mThumbIds[position];
    }


    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ImageView imageView = new ImageView(mContext);
        ArrayList<String> urilist = getImagesPath(mContext);

        if (position < urilist.size()){
            Uri new_uri = Uri.parse(urilist.get(position));
            Log.d("ImageAdapter",urilist.get(position));
            imageView.setImageURI(Uri.parse(urilist.get(position)));

//            imageView.setImageResource(mThumbIds[position]);
            imageView.setScaleType(ImageView.ScaleType.CENTER_CROP);
//        imageView.setLayoutParams(new GridView.LayoutParams(70, 70));
            imageView.setLayoutParams(new GridView.LayoutParams(250, 250));
        }
        else{
            Log.d("getView",Integer.toString(position));
        }
//        imageView.setImageResource(mThumbIds[position]);
        imageView.setScaleType(ImageView.ScaleType.CENTER_CROP);
//        imageView.setLayoutParams(new GridView.LayoutParams(70, 70));
        imageView.setLayoutParams(new GridView.LayoutParams(250, 250));
        return imageView;
    }

}