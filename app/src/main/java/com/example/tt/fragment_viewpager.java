package com.example.tt;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.example.tt.tab1.tab1;
import com.example.tt.tab2.tab2;
import com.example.tt.tab3.tab3;
import com.google.android.material.tabs.TabItem;
import com.google.android.material.tabs.TabLayout;

public class fragment_viewpager extends Fragment{
    private TabLayout tabLayout;
    private TabItem tab1, tab2,  tab3;
    private androidx.viewpager.widget.ViewPager mViewpager;
    SectionPageAdapter sectionPageAdapter;

    public fragment_viewpager() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View view= inflater.inflate(R.layout.pagerview, container, false);
        Button addContactbtn = view.findViewById(R.id.add_contact);

//        ListView MyListView = view.findViewById(R.id.list);
//        ListViewAdapter adapter = new ListViewAdapter(getActivity());
//        MyListView.setAdapter(adapter);
//        final Activity activity = getActivity();


        tabLayout = getActivity().findViewById(R.id.tabs);
        tab1 = getActivity().findViewById(R.id.tab1);
        tab2 = getActivity().findViewById(R.id.tab2);
        tab3 = getActivity().findViewById(R.id.tab3);

        sectionPageAdapter = new SectionPageAdapter(getActivity().getSupportFragmentManager());
//        ActivityCompat.requestPermissions(this,
//                new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE, Manifest.permission.READ_EXTERNAL_STORAGE, Manifest.permission_group.CONTACTS},1);


        mViewpager = view.findViewById(R.id.frame);

        setupViewPager(mViewpager);
        tabLayout.setupWithViewPager(mViewpager);


        FragmentManager fm = getActivity().getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = fm.beginTransaction();
//        fragmentTransaction.add(R.id.frame, new tab1());
        fragmentTransaction.add(R.id.frame, new tab1());
        fragmentTransaction.commit();

        return view;
    }
    public void setupViewPager(androidx.viewpager.widget.ViewPager viewPager) {
        sectionPageAdapter.addFragment(new tab1(), "Tel");
        sectionPageAdapter.addFragment(new tab2(), "Gallery");
        sectionPageAdapter.addFragment(new tab3(), "Weather");
        viewPager.setAdapter(sectionPageAdapter);
    }

}
