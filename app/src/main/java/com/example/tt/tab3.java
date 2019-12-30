package com.example.tt;


import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.res.AssetManager;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.util.Log;
import android.util.Pair;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.NetworkError;
import com.android.volley.NoConnectionError;
import com.android.volley.ParseError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.ServerError;
import com.android.volley.TimeoutError;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.HashMap;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Locale;
import java.util.Set;


/**
 * A simple {@link Fragment} subclass.
 */
public class tab3 extends Fragment {
    String city = "";
    String sector = "";
    final Set<String> cities = new LinkedHashSet<>();
    final Set<String> sectors = new LinkedHashSet<>();
    location loc;
    HashMap<Pair<String,String>,Pair<String,String>> loc_hashmap;

    public tab3() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_tab3, container, false);
        weather cur_weather = new weather(container.getContext());
        String cur = cur_weather.getinfo();

        final Button citybtn = view.findViewById(R.id.citybtn);
        final Button sectorbtn = view.findViewById(R.id.sectorbtn);
        loc = new location(getActivity().getApplicationContext());
        loc_hashmap = loc.makeLocationList();

        for (Pair<String, String> elem: loc_hashmap.keySet()){
            cities.add(elem.first);
        }

        citybtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View btn_view) {
                sectors.clear();
                show(cities, citybtn);
        }
        });
        sectorbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View btn_view) {
                if (sectors.isEmpty()){
                    Toast.makeText(getActivity().getApplicationContext(), "광역시/도 를 먼저 선택해주세요.", Toast.LENGTH_SHORT).show();
                }
                else{
                    show_1(sectors, sectorbtn);
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

        final CharSequence[] items =  ListItems.toArray(new String[ListItems.size()]);

        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        builder.setTitle("광역시/도 를 고르십시오.");

        builder.setItems(items, new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int pos) {
                String selectedText = items[pos].toString();
                Toast.makeText(getActivity().getApplicationContext(), selectedText, Toast.LENGTH_SHORT).show();
                city = selectedText;
                btn_view.setText(city);
                for (Pair<String, String> elem: loc_hashmap.keySet()){
                    if (elem.first.equals(city)){
                        sectors.add(elem.second);
                    }
                }
            }
        });
        builder.show();
    }

    void show_1(Set<String> sectors, final Button btn_view){
        final List<String> ListItems = new ArrayList<>();
        for (String elem: sectors){
            ListItems.add(elem);
        }

        final CharSequence[] items =  ListItems.toArray(new String[ListItems.size()]);

        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        builder.setTitle("구/군/면 을 고르십시오.");

        builder.setItems(items, new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int pos) {
                String selectedText = items[pos].toString();
                Toast.makeText(getActivity().getApplicationContext(), selectedText, Toast.LENGTH_SHORT).show();
                sector = selectedText;
                btn_view.setText(sector);
            }
        });
        builder.show();
    }
}


class weather {
    String ServiceKey = "GSdxY%2F7j7F0kYUPBy5Lkap8PFngA3%2FlgfMUh44rpvhndVEEXSi1TK3jK6I0qWiKzkGtXpALVJYJE2wYWSvYi2g%3D%3D";
    String base_date;
    String base_time;
    String nx = "66";
    String ny = "101";
    String type = "json";
    Context mContext;

    public weather(Context context){
        SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMdd_HHmm", Locale.KOREA);
        String Date_time = sdf.format(new Date());
        String[] array = Date_time.split("_");
        base_date = array[0];
        base_time = array[1];
        mContext = context;
    }

    public String getinfo(){
        String request = ";http://newsky2.kma.go.kr/service/SecndSrtpdFrcstInfoService2/ForecastGrib"
        request += "?ServiceKey="+ServiceKey;
        request += "&base_date="+base_date;
        request += "&base_time="+base_time;
        request += "&nx=" + nx;
        request += "&ny=" + ny;
        request += "&pageNo=1&numOfRows=1";
        final String[] result = {""};
        Log.d("tab3",request);
        RequestQueue queue = Volley.newRequestQueue(mContext);
        StringRequest stringRequest = new StringRequest(Request.Method.GET, request,
            new Response.Listener<String>() {
                @Override
                public void onResponse(String response) {

                    result[0] = response;
                    Log.d("tab3", result[0]);
                }
            }, new Response.ErrorListener() {
                @Override
                public void onErrorResponse(VolleyError error) {
                    Log.d("tab3", "Connecting problem");
                    if (error instanceof TimeoutError) {
                        Log.d("tab3", "Timeout");
                    } else if (error instanceof NoConnectionError) {
                        //TODO
                        Log.d("tab3", "NoConnectionError problem");
                        error.printStackTrace();
                    } else if (error instanceof AuthFailureError) {
                        //TODO
                        Log.d("tab3", "AuthFailureError problem");
                    } else if (error instanceof ServerError) {
                        //TODO
                        Log.d("tab3", "ServerError problem");
                    } else if (error instanceof NetworkError) {
                        //TODO
                        Log.d("tab3", "NetworkError problem");
                    } else if (error instanceof ParseError) {
                        //TODO
                        Log.d("tab3", "ParseError problem");
                    }
                }
            });
        queue.add(stringRequest);

        return result[0];
    }
}

class location{
    Context mContext;
    public location(Context context){
        mContext = context;
    }
    public HashMap<Pair<String, String>, Pair<String,String>> makeLocationList(){
        try{
            AssetManager am = mContext.getAssets();
            StringBuilder returnString = new StringBuilder();
            InputStream fIn = null;
            InputStreamReader isr = null;
            BufferedReader input = null;
            String raw;
            HashMap<Pair<String, String>, Pair<String,String>> result = new HashMap<>();

            fIn = am.open("location.txt", Context.MODE_WORLD_READABLE);
            isr = new InputStreamReader(fIn);
            input = new BufferedReader(isr);
            String line = "";
            while ((line = input.readLine()) != null) {
                Log.d("tab3",line);
                returnString.append(line);
                returnString.append(" ");
            }
            raw = returnString.toString();
            String[] parsing = raw.split("\\s");
            Log.d("tab3",Integer.toString(parsing.length));
            while(Arrays.asList(parsing).contains("")){
                List<String> list = new ArrayList<>(Arrays.asList(parsing));
                list.remove("");
                parsing = list.toArray(new String[list.size()]);
            }
            Log.d("tab3",Integer.toString(parsing.length));


            for (int i = 0; i < parsing.length; i = i + 4){
                Log.d("tab3",parsing[i]);
                Log.d("tab3",parsing[i+1]);
                Log.d("tab3",parsing[i+2]);
                Log.d("tab3",parsing[i+3]);
                Log.d("tab3",Integer.toString(i/4));
                Pair<String,String> tmp_key = Pair.create(parsing[i],parsing[i+1]);
                Pair<String,String> tmp_value = Pair.create(parsing[i+2],parsing[i+3]);
//                Log.d("tmp_key",tmp_key.toString());
//                Log.d("tmp_value",tmp_value.toString());
                result.put(tmp_key,tmp_value);
            }
            Log.d("tab3",result.toString());
            if (isr != null) isr.close();
            if (fIn != null) fIn.close();
            if (input != null) input.close();

            return result;
        }
        catch (IOException e){
            e.printStackTrace();
            return null;
        }



//        InputStream is = null;
//        try {
//            is = am.open("location.txt");
//        } catch (IOException e) {
//            e.printStackTrace();
//        }
//
//        POIFSFileSystem myFileSystem = null;
//        try {
//            myFileSystem = new POIFSFileSystem(is);
//        } catch (IOException e) {
//            e.printStackTrace();
//        }
//
//        HSSFWorkbook myWorkBook = null;
//        try {
//            myWorkBook = new HSSFWorkbook(myFileSystem);
//        } catch (IOException e) {
//            e.printStackTrace();
//        }
//
//        HSSFSheet mySheet = myWorkBook.getSheetAt(0);
//
//        Iterator<Row> rowIter = mySheet.rowIterator();
//        int rowno = 0;
//
//        //Create empty hashmap
//        HashMap<Pair<String, String>, Pair<String,String>> tmp_hashmap = new HashMap<>();
//
//        while (rowIter.hasNext()) {
//            Log.e("location", " row no "+ rowno );
//
//            HSSFRow myRow = (HSSFRow) rowIter.next();
//            if(rowno !=0) {
//                Iterator<Cell> cellIter = myRow.cellIterator();
//                int colno =0;
//                String city="", sector="", det="";
//                while (cellIter.hasNext()) {
//                    HSSFCell myCell = (HSSFCell) cellIter.next();
//                    if (colno==0){
//                        city = myCell.toString();
//                    }else if (colno==1){
//                        sector = myCell.toString();
//                    }
//
//                    Pair<String, String> tmp_key = Pair.create(city, sector);
//                    if (city.equals("") && sector.equals("") && !tmp_hashmap.containsKey(tmp_key)){
//                        myCell = (HSSFCell)cellIter.next();
//                        myCell = (HSSFCell)cellIter.next();
//                        String x = myCell.toString();
//                        myCell = (HSSFCell)cellIter.next();
//                        String y = myCell.toString();
//                        Pair<String, String> tmp_value = Pair.create(x, y);
//                        tmp_hashmap.put(tmp_key,tmp_value);
//                        Log.e("location",  tmp_key.toString() + ":" + tmp_value.toString());
//                    }
//                    colno++;
//                }
//            }
//            rowno++;
//        }

//        return tmp_hashmap;
    }
}