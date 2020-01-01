package com.example.tt.tab1;


import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.ContentProviderOperation;
import android.content.OperationApplicationException;
import android.os.Bundle;
import android.os.RemoteException;
import android.provider.ContactsContract;

import java.util.ArrayList;


import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ListView;

import com.example.tt.R;
import com.example.tt.SectionPageAdapter;

public class tab1 extends Fragment{


    public tab1() {
        // Required empty public constructor
    }


    @SuppressLint("WrongViewCast")
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View view= inflater.inflate(R.layout.fragment_tab1, container, false);
        Button addContactbtn = view.findViewById(R.id.add_contact);

        ListView MyListView = view.findViewById(R.id.list);
        ListViewAdapter adapter = new ListViewAdapter(getActivity());
        MyListView.setAdapter(adapter);
        final Activity activity = getActivity();

        addContactbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                FragmentManager fm = getActivity().getSupportFragmentManager();
                FragmentTransaction fragmentTransaction = fm.beginTransaction();
                //Casting getitem value to integer -> can be vulnerable.
                Fragment newFragment = new tab1_addcontacts();

                fragmentTransaction.replace(R.id.outerfragment, newFragment);
//                FragmentStatePagerAdapter swipe = DemoCollectionPagerAdapter(fm);
                fragmentTransaction.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_OPEN);

                fragmentTransaction.addToBackStack(null);
                fragmentTransaction.commit();
            }
        });

        return view;
    }


}
