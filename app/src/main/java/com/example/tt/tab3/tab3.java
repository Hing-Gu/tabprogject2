package com.example.tt.tab3;

import android.app.AlertDialog;
import android.content.DialogInterface;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.example.tt.R;

import android.graphics.Typeface;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;


/**
 * A simple {@link Fragment} subclass.
 */
public class tab3 extends Fragment {

    location loc;
    private TextView weather_text;
    private TextView now_tem;
    private Object TextView;

    public tab3() {
        // Required empty public constructor
    }

    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_tab3, container, false);
        final weather cur_weather = new weather(container.getContext(), view);

        final Button citybtn = view.findViewById(R.id.citybtn);
        final Button sectorbtn = view.findViewById(R.id.sectorbtn);
        loc = new location(getActivity().getApplicationContext(), cur_weather);

        super.onCreate(savedInstanceState);

        weather_text = view.findViewById(R.id.weather);
        now_tem = view.findViewById(R.id.nowtext);
        Typeface face = Typeface.createFromAsset(getActivity().getAssets(),"fonts/NanumSquareRoundB.ttf");
        weather_text.setTypeface(face);
        now_tem.setTypeface(face);

        new WeatherAsynTask(weather_text,now_tem).execute("https://weather.naver.com/","span[class=temp]");

        citybtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View btn_view) {
                show(loc.getCities(), citybtn);
                sectorbtn.setText("시/구/군/면");
            }
        });
        sectorbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View btn_view) {
            if (loc.getCity().equals("")){
                Toast.makeText(getActivity().getApplicationContext(), "광역시/도 를 먼저 선택해주세요.", Toast.LENGTH_SHORT).show();
            }
            else{
                show_1(loc.getSectors(), sectorbtn);
            }
            }
        });
        return view;
    }

    void show(Set<String> cities, final Button btn_view){
        final List<String> ListItems = new ArrayList<>();
        for (String elem: cities){
            ListItems.add(elem);
        }
        ListItems.sort(null);
        final CharSequence[] items =  ListItems.toArray(new String[ListItems.size()]);

        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        builder.setTitle("광역시/도 를 고르십시오.");

        builder.setItems(items, new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int pos) {
                String selectedText = items[pos].toString();
                Toast.makeText(getActivity().getApplicationContext(), selectedText, Toast.LENGTH_SHORT).show();
                loc.setCity(selectedText);
                btn_view.setText(loc.getCity());
            }
        });
        builder.show();
    }

    void show_1(Set<String> sectors, final Button btn_view) {
        final List<String> ListItems = new ArrayList<>();
        for (String elem : sectors) {
            ListItems.add(elem);
        }
        ListItems.sort(null);
        final CharSequence[] items = ListItems.toArray(new String[ListItems.size()]);

        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        builder.setTitle("시/구/군/면 을 고르십시오.");

        builder.setItems(items, new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int pos) {
                String selectedText = items[pos].toString();
                Toast.makeText(getActivity().getApplicationContext(), selectedText, Toast.LENGTH_SHORT).show();
                loc.setSector(selectedText);
                btn_view.setText(loc.getSector());
            }
        });
        builder.show();
    }
}


