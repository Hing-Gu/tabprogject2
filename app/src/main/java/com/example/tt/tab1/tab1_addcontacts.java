package com.example.tt.tab1;


import android.annotation.SuppressLint;
import android.app.ProgressDialog;
import android.content.ContentProviderOperation;
import android.content.OperationApplicationException;
import android.os.Bundle;
import android.os.RemoteException;
import android.provider.ContactsContract;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.ProgressBar;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import com.example.tt.R;
import com.example.tt.fragment_viewpager;

import java.util.ArrayList;

public class tab1_addcontacts extends Fragment {


    public tab1_addcontacts() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        Log.d("tab1_addcontacts", "start");
        View view = inflater.inflate(R.layout.fragment_tab1_addcontact, container, false);
//        return view;
        Button okbtn = view.findViewById(R.id.fragment1_ok);
        Button cancelbtn = view.findViewById(R.id.fragment1_cancel);
        final EditText name = view.findViewById(R.id.Edit_name);
        final EditText phone = view.findViewById(R.id.Edit_phone);
        final EditText email = view.findViewById(R.id.Edit_email);

        okbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String nameValue = name.getText().toString();
                String phoneValue = phone.getText().toString();
                String emailValue = email.getText().toString();
                ContactAdd(nameValue, phoneValue, emailValue);
            }
        });

        cancelbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                FragmentManager fm = getActivity().getSupportFragmentManager();
                FragmentTransaction fragmentTransaction = fm.beginTransaction();
                //Casting getitem value to integer -> can be vulnerable.
                Fragment newFragment = new fragment_viewpager();

                fragmentTransaction.replace(R.id.outerfragment, newFragment);
//                FragmentStatePagerAdapter swipe = DemoCollectionPagerAdapter(fm);
//        fragmentTransaction.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_OPEN);
                fragmentTransaction.commit();
            }
        });
        return view;
    }
    public void ContactAdd(final String nameValue, final String phoneValue, final String emailValue){
//        ProgressDialog asyncDialog = new ProgressDialog(getActivity());
        ContactThread thr = new ContactThread(nameValue, phoneValue, emailValue, getActivity());
        thr.start();

        try {
            // 해당 쓰레드가 멈출때까지 멈춤
            thr.join();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        FragmentManager fm = getActivity().getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = fm.beginTransaction();
        //Casting getitem value to integer -> can be vulnerable.
        Fragment newFragment = new fragment_viewpager();

        fragmentTransaction.replace(R.id.outerfragment, newFragment);
//                FragmentStatePagerAdapter swipe = DemoCollectionPagerAdapter(fm);
//        fragmentTransaction.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_OPEN);
        fragmentTransaction.commit();
    }
}
