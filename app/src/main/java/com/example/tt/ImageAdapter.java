package com.example.tt;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
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

import static androidx.core.app.ActivityCompat.startActivityForResult;

public class ImageAdapter extends BaseAdapter {
    private Context mContext;

    // Keep all Images in array
    public Integer[] mThumbIds = {
            R.drawable.maxresdefault , R.drawable.cross
            ,R.drawable.maxresdefault, R.drawable.cross
            ,R.drawable.maxresdefault, R.drawable.maxresdefault
            ,R.drawable.maxresdefault, R.drawable.maxresdefault
            ,R.drawable.maxresdefault, R.drawable.maxresdefault
            ,R.drawable.maxresdefault, R.drawable.maxresdefault
            ,R.drawable.maxresdefault, R.drawable.maxresdefault
            ,R.drawable.maxresdefault, R.drawable.maxresdefault
            ,R.drawable.maxresdefault, R.drawable.maxresdefault
            ,R.drawable.maxresdefault, R.drawable.maxresdefault
            ,R.drawable.maxresdefault, R.drawable.maxresdefault
            ,R.drawable.maxresdefault, R.drawable.maxresdefault

    };

    // Constructor
    public ImageAdapter(Context c){

        mContext = c;
    }

//    private void pickFromGallery(){
//        Intent intent = new Intent(Intent.ACTION_PICK);
//        intent.setType("images/*");
//        String[] mimeTypes = {"images/jpeg","images/png"};
//        intent.putExtra(Intent.EXTRA_MIME_TYPES,mimeTypes);
//        startActivityForResult(intent, GALLERY_REQUEST_CODE);
//    }
//
//    public void onActivityResult(int requestCode, int resultCode, Intent data){
//        if (resultCode == Activity.RESULT_OK){
//            switch (requestCode){
//                case GALLERY_REQUEST_CODE:
//                    Uri selectedImage = data.getData();
//
//                    String[] filePathColumn = {MediaStore.Images.Media.DATA};
//                    Cursor cursor = getContentResolver().query(selectedImage, filePathColumn, null, null, null);
//                    cursor.moveToFirst();
//                    int columnIndex = cursor.getColumnIndex(filePathColumn[0]);
//                    String imgDecodableString = cursor.getString(columnIndex);
//
//                    cursor.close();
//
//                    break;
//            }
//        }
//    }

    @Override
    public int getCount() {
        return mThumbIds.length;
    }

    @Override
    public Object getItem(int position) {
        return mThumbIds[position];
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ImageView imageView = new ImageView(mContext);
        imageView.setImageResource(mThumbIds[position]);
        imageView.setScaleType(ImageView.ScaleType.CENTER_CROP);
//        imageView.setLayoutParams(new GridView.LayoutParams(70, 70));
        imageView.setLayoutParams(new GridView.LayoutParams(250, 250));
        return imageView;
    }

}