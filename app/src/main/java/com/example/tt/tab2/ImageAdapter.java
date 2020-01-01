package com.example.tt.tab2;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.res.TypedArray;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.media.ThumbnailUtils;
import android.net.Uri;
import android.provider.MediaStore;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AbsListView;
import android.widget.BaseAdapter;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.LinearLayout;

import androidx.annotation.Nullable;

import com.example.tt.helper.Utils;

import java.io.File;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

import static androidx.core.app.ActivityCompat.startActivityForResult;


public class ImageAdapter extends BaseAdapter {
    int CustomGalleryItemBg; // 앞서 정의해 둔 attrs.xml의 resource를 background로 받아올 변수 선언
    String mBasePath; // CustomGalleryAdapter를 선언할 때 지정 경로를 받아오기 위한 변수
    Context mContext; // CustomGalleryAdapter를 선언할 때 해당 activity의 context를 받아오기 위한 context 변수
    ArrayList<String> mImgs; // 위 mBasePath내의 file list를 String 배열로 저장받을 변수
    Bitmap bm; // 지정 경로의 사진을 Bitmap으로 받아오기 위한 변수

    public String TAG = "Gallery Adapter Example :: ";

    public ImageAdapter(Context context){ // CustomGalleryAdapter의 생성자
        this.mContext = context;
//        this.mBasePath = basepath;
        String[] tmp_mimgs;
        Utils utils = new Utils(context);
        mImgs = utils.getFilePaths();
//        File file = new File(mBasePath); // 지정 경로의 directory를 File 변수로 받아
//        if(!file.exists()){
//            if(!file.mkdirs()){
//                Log.e(TAG, "failed to create directory");
//            }
//        }
//        Log.d(TAG, "basePath : " + mBasePath);
//        tmp_mimgs = file.list(); // file.list() method를 통해 directory 내 file 명들을 String[] 에 저장
////        Log.d(TAG, "length : " + mImgs.length);
//
//        ArrayList<String> filtered_list = new ArrayList<>();
//
//        for (String str : tmp_mimgs){
//            if (str.endsWith(".jpg") || str.endsWith(".png") || str.endsWith(".jpeg")){
//                filtered_list.add(mBasePath + File.separator+str);
//                Log.d(TAG, str);
//            }
//        }
//        Log.d(TAG, Integer.toString(filtered_list.size()));
//        mImgs = new String[filtered_list.size()];
//        mImgs = filtered_list.toArray(mImgs);
//
//        /* 앞서 정의한 attrs.xml에서 gallery array의 배경 style attribute를 받아옴 */
//        TypedArray array = mContext.obtainStyledAttributes(R.styleable.GalleryTheme);
//        CustomGalleryItemBg = array.getResourceId(R.styleable.GalleryTheme_android_galleryItemBackground, 0);
//        array.recycle();
    }

    @Override
    public int getCount() { // Gallery array의 객체 갯수를 앞서 세어 둔 file.list()를 받은 String[]의 원소 갯수와 동일하다는 가정 하에 반환
        return mImgs.size();
    }

    @Override
    public Object getItem(int position) { // Gallery array의 해당 position을 반환
        return mImgs.get(position); // String value
    }

    @Override
    public long getItemId(int position) { // Gallery array의 해당 position을 long 값으로 반환
        return position;
    }

    public String getmBasePath(){
        return mBasePath;
    }

    public ArrayList<String> getmImgs(){
        return mImgs;
    }

    // Override this method according to your need
    // 지정 경로 내 사진들을 보여주는 method.
    // Bitmap을 사용할 경우, memory 사용량이 커서 Thumbnail을 사용하거나 크기를 줄일 필요가 있음
    // setImageDrawable()이나 setImageURI() 등의 method로 대체 가능
    @Override
    public View getView(int position, View convertView, ViewGroup parent)
    {
        ImageView imageView;
        if (convertView == null) {
            // if it's not recycled, initialize some attributes
            imageView = new ImageView(mContext);
        } else {
            imageView = (ImageView) convertView;
        }
        ///////
        BitmapFactory.Options options = new BitmapFactory.Options();
        options.inJustDecodeBounds = true;

        int width = options.outWidth;
        int height = options.outHeight;
        int inSampleSize = 1;
        int reqWidth = 256;
        int reqHeight = 192;
        if((width > reqWidth) || (height > reqHeight)){
            final int halfHeight = height / 2;
            final int halfWidth = width / 2;

            // Calculate the largest inSampleSize value that is a power of 2 and keeps both
            // height and width larger than the requested height and width.
            while ((halfHeight / inSampleSize) > reqHeight
                    && (halfWidth / inSampleSize) > reqWidth) {
                inSampleSize *= 2;
            }
        }
        options.inSampleSize = inSampleSize;
        options.inJustDecodeBounds = false;
        //////


//        bm = BitmapFactory.decodeFile(mBasePath + File.separator + mImgs[position]);
        bm = BitmapFactory.decodeFile(mImgs.get(position));
        Bitmap mThumbnail = ThumbnailUtils.extractThumbnail(bm, 250, 250);
        imageView.setPadding(8, 8, 8, 8);
        imageView.setScaleType(ImageView.ScaleType.CENTER_CROP);
//        imageView.setLayoutParams(new GridView.LayoutParams(250, 250));
        imageView.setLayoutParams(new GridView.LayoutParams(GridView.LayoutParams.WRAP_CONTENT, GridView.LayoutParams.WRAP_CONTENT));
        imageView.setImageBitmap(mThumbnail);
        return imageView;
    }
}



//
//public class ImageAdapter extends BaseAdapter {
//    private Context mContext;
//    private ArrayList<String> pathList;
//    static final int GALLERY_REQUEST_CODE = 1;
//    String imgDecodableString;
//    // Keep all Images in array
////    public Integer[] mThumbIds = {
////            R.drawable.maxresdefault , R.drawable.cross
////            ,R.drawable.maxresdefault, R.drawable.cross
////            ,R.drawable.maxresdefault, R.drawable.maxresdefault
////            ,R.drawable.maxresdefault, R.drawable.maxresdefault
////            ,R.drawable.maxresdefault, R.drawable.maxresdefault
////            ,R.drawable.maxresdefault, R.drawable.maxresdefault
////            ,R.drawable.maxresdefault, R.drawable.maxresdefault
////            ,R.drawable.maxresdefault, R.drawable.maxresdefault
////            ,R.drawable.maxresdefault, R.drawable.maxresdefault
////            ,R.drawable.maxresdefault, R.drawable.maxresdefault
////            ,R.drawable.maxresdefault, R.drawable.maxresdefault
////            ,R.drawable.maxresdefault, R.drawable.maxresdefault
////
////    };
//
//    // Constructor
//    public ImageAdapter(Context c){
//
//        mContext = c;
//        pathList = getImagesPath(mContext);
//    }
//
//    public static ArrayList<String> getImagesPath(Context context) {
//        Uri uri;
//        ArrayList<String> listOfAllImages = new ArrayList<String>();
//        Cursor cursor;
//        int column_index_data, column_index_folder_name;
//        String PathOfImage = null;
//        uri = android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI;
//
//        String[] projection = {MediaStore.MediaColumns.DATA,
//                MediaStore.Images.Media.BUCKET_DISPLAY_NAME};
//        cursor = context.getContentResolver().query(uri, projection, null, null, null);
//
//        column_index_data = cursor.getColumnIndexOrThrow(MediaStore.MediaColumns.DATA);
//        column_index_folder_name = cursor
//                .getColumnIndexOrThrow(MediaStore.Images.Media.BUCKET_DISPLAY_NAME);
//
////        cursor.moveToFirst();
//
//        while (cursor.moveToNext()) {
//            PathOfImage = cursor.getString(column_index_data);
//            listOfAllImages.add(PathOfImage);
//        }
//
//        cursor.close();
//        return listOfAllImages;
//    }
//
//
//    @Override
//    public int getCount() {
////        return mThumbIds.length;
//        return pathList.size();
//    }
//
//    @Override
//    public Object getItem(int position) {
////        try {
////            return mThumbIds[position];
////        }catch(Exception ArrayIndexOutOfBoundsExcetion){
////            return -1;
////        }
//        return pathList.get(position);
//    }
//
//    @Override
//    public long getItemId(int position) {
////        return 0;
//        return position;
////        return mThumbIds[position];
//    }
//
//
//    @Override
//    public View getView(int position, View convertView, ViewGroup parent) {
//        ImageView imageView = new ImageView(mContext);
//        ArrayList<String> urilist = getImagesPath(mContext);
//
//        if (position < urilist.size()){
//            Uri new_uri = Uri.parse(urilist.get(position));
//            Log.d("ImageAdapter",urilist.get(position));
//            imageView.setImageURI(Uri.parse(urilist.get(position)));
//
////            imageView.setImageResource(mThumbIds[position]);
//            imageView.setScaleType(ImageView.ScaleType.CENTER_CROP);
////        imageView.setLayoutParams(new GridView.LayoutParams(70, 70));
//            imageView.setLayoutParams(new GridView.LayoutParams(250, 250));
//        }
//        else{
//            Log.d("getView",Integer.toString(position));
//        }
////        imageView.setImageResource(mThumbIds[position]);
//        imageView.setScaleType(ImageView.ScaleType.CENTER_CROP);
////        imageView.setLayoutParams(new GridView.LayoutParams(70, 70));
//        imageView.setLayoutParams(new GridView.LayoutParams(250, 250));
//        return imageView;
//    }
//
//}