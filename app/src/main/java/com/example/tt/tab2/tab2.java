package com.example.tt.tab2;


import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;

import androidx.core.content.FileProvider;
import androidx.fragment.app.Fragment;

import android.os.Environment;
import android.provider.MediaStore;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.GridView;

import com.example.tt.R;
import com.example.tt.SectionPageAdapter;
import com.example.tt.helper.AppConstant;
import com.example.tt.helper.Utils;

import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

import static android.app.Activity.RESULT_OK;


/**
 * A simple {@link Fragment} subclass.
 */
public class tab2 extends Fragment {
    private ImageAdapter imgAdapter;
    private GridView gridView;
    final int REQUEST_TAKE_ALBUM = 1;
    String basePath = null;
    final int TAKE_CAMERA = 2;
    final int TAKE_GALLERY = 3;
    private Button camera;
    String currentPhotoPath = null;


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

        camera = v.findViewById(R.id.takepicbtn);
        imgAdapter = new ImageAdapter(getContext().getApplicationContext());
        gridView.setAdapter(imgAdapter);
        final Utils utils = new Utils(getActivity());

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

        camera.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                Intent takePictureIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                // Ensure that there's a camera activity to handle the intent
                if (takePictureIntent.resolveActivity(getActivity().getPackageManager()) != null) {
                    // Create the File where the photo should go
                    File photoFile = null;
                    try {
                        photoFile = createImageFile();
                    } catch (IOException ex) {
                        // Error occurred while creating the File
                    }
                    // Continue only if the File was successfully created
                    if (photoFile != null) {
                        Uri photoURI = FileProvider.getUriForFile(getActivity(),
                                "com.example.tt.fileprovider",
                                photoFile);
                        takePictureIntent.putExtra(MediaStore.EXTRA_OUTPUT, photoURI);
                        startActivityForResult(takePictureIntent, TAKE_CAMERA);
                    }
                }

//                // 카메라 실행
//                Intent it = new Intent( ) ;
//                SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMdd_HHmm", Locale.KOREA);
//                String Date_time = sdf.format(new Date());
//                Log.d("path",Environment.getExternalStorageDirectory().getPath());
////                File file = new File( Environment.getExternalStorageDirectory(), utils.getFilePaths()+ "/" + Date_time ) ;
//                File file = new File(utils.getFilePaths()+ "/" + Date_time ) ;
//                String tempPictuePath = file.getAbsolutePath( ) ;
//                it.putExtra( MediaStore.EXTRA_OUTPUT, tempPictuePath ) ;
//                it.setAction( MediaStore.ACTION_IMAGE_CAPTURE ) ; // 모든 단말에서 안될 수 있기 때문에 수정해야 함
//
//                startActivityForResult( it, TAKE_CAMERA ) ;
            }
        });
//        return inflater.inflate(R.layout.fragment_tab2, container, false);
        return v;
    }
    private File createImageFile() throws IOException {
        // Create an image file name
        String timeStamp = new SimpleDateFormat("yyyyMMdd_HHmmss").format(new Date());
        String imageFileName = "JPEG_" + timeStamp + "_";
        File storageDir = new File(basePath);
        File image = File.createTempFile(
                imageFileName,  /* prefix */
                ".jpg",         /* suffix */
                storageDir      /* directory */
        );

        // Save a file: path for use with ACTION_VIEW intents
        currentPhotoPath = image.getAbsolutePath();
        Log.d("AA",currentPhotoPath);
        return image;
    }

    public void onActivityResult( int requestCode, int resultCode, Intent data)
    {
        if( resultCode == RESULT_OK )
        {
// 카메라로 찍었을 떄
            if( requestCode == TAKE_CAMERA ) //1
            {
                if( data == null) {
                    Log.e("BB","AAAABBBAA");
                    imgAdapter.updateData();
                    imgAdapter.notifyDataSetChanged();
                    return ;
                }

// 찍은 사진을 이미지뷰에 보여준다.
                Log.e("AAAAA","AAAAAA");
                imgAdapter.updateData();
                imgAdapter.notifyDataSetChanged();
                gridView.setAdapter(imgAdapter);
            }

// 앨범에서 가져올 때
//            else if( requestCode == TAKE_GALLERY ) //2
//            {
//                Uri currImageURI = data.getData( ) ;
//                String path = getRealPathFromURI( currImageURI ) ;
//                tempPictuePath = path ;
//// 찍은 사진을 이미지뷰에 보여준다.
//                setImage.setAlbumImage( path, ivCardImage ) ;
//            }
//        }
            else
            {
                System.out.println( "camera return error" ) ;
                return ;
            }
        }
    }
}




