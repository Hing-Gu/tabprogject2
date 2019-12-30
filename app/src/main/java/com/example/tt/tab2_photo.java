package com.example.tt;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentStatePagerAdapter;
import androidx.viewpager.widget.ViewPager;


public class tab2_photo extends Fragment {
    private ImageView selected_image;
    private ImageButton exit;
//    private int img_int;
    private int position;
    private ImageAdapter ImageAdapter;
    public static final String ARG_OBJECT = "object";

    public tab2_photo(int img, ImageAdapter imageAdapter) {
        // Required empty public constructor
        position = img;
        ImageAdapter = imageAdapter;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
//        View v = inflater.inflate(R.layout.fragment_tab2_photo, container, false);
        View v = inflater.inflate(R.layout.fragment_tab2_photo_element, container, false);
        int image_value = 0;
        selected_image = v.findViewById(R.id.selected_photo);
        selected_image.setImageResource((int) ImageAdapter.getItem(position));
        selected_image.setScaleType(ImageView.ScaleType.CENTER_INSIDE);

//        exit = v.findViewById(R.id.exitbtn);
//
//        exit.setOnClickListener(new View.OnClickListener(){
//            @Override
//            public void onClick(View v){
//                FragmentManager fm = getActivity().getSupportFragmentManager();
////                FragmentTransaction fragmentTransaction = fm.beginTransaction();
//                fm.popBackStack();
////                fragmentTransaction.replace();
//            }
//        });
        return v;
    }
}
//
//
//
//
//class CollectionDemoFragment extends Fragment{
//    DemoCollectionPagerAdapter demoCollectionPagerAdapter;
//    ViewPager viewPager;
//
//    private ImageAdapter imgadapter;
//    private int position;
//
//    public CollectionDemoFragment(ImageAdapter ia, int pos){
//        imgadapter = ia;
//        position = pos;
//    }
//
//    @Nullable
//    @Override
//    public View onCreateView(@NonNull LayoutInflater inflater,
//                             @Nullable ViewGroup container,
//                             @Nullable Bundle savedInstanceState) {
////        return inflater.inflate(R.layout.collection_demo, container, false);
//        return inflater.inflate(R.layout.fragment_tab2_photo, container, false);
//    }
//
//    @Override
//    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
//        demoCollectionPagerAdapter = new DemoCollectionPagerAdapter(getChildFragmentManager());
//        viewPager = view.findViewById(R.id.pager);
//        viewPager.setAdapter(demoCollectionPagerAdapter);
//    }
//}
//
//// Since this is an object collection, use a FragmentStatePagerAdapter,
//// and NOT a FragmentPagerAdapter.
//class DemoCollectionPagerAdapter extends FragmentStatePagerAdapter {
//    public DemoCollectionPagerAdapter(FragmentManager fm) {
//        super(fm);
//    }
//
//    @Override
//    public Fragment getItem(int i) {
//        Fragment fragment = new tab2_photo();
//        Bundle args = new Bundle();
//        // Our object is just an integer :-P
//        args.putInt(tab2_photo.ARG_OBJECT, i + 1);
//        fragment.setArguments(args);
//        return fragment;
//    }
//
//    @Override
//    public int getCount() {
//        return 100;
//    }
//
//    @Override
//    public CharSequence getPageTitle(int position) {
//        return "OBJECT " + (position + 1);
//    }
//}

// Instances of this class are fragments representing a single
// object in our collection.
//public class DemoObjectFragment extends Fragment {
//    public static final String ARG_OBJECT = "object";
//
//    @Override
//    public View onCreateView(LayoutInflater inflater,
//                             ViewGroup container, Bundle savedInstanceState) {
//        return inflater.inflate(R.layout.fragment_tab2_photo_element, container, false);
//    }
//
//    @Override
//    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
//        Bundle args = getArguments();
//        ((TextView) view.findViewById(android.R.id.text1))
//                .setText(Integer.toString(args.getInt(ARG_OBJECT)));
//    }
//}