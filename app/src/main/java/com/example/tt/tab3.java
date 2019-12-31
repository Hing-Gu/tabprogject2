package com.example.tt;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.res.AssetManager;
import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.util.Log;
import android.util.Pair;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
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

import org.w3c.dom.Text;

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

    location loc;

    public tab3() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_tab3, container, false);
        final weather cur_weather = new weather(container.getContext(), view);

        final Button citybtn = view.findViewById(R.id.citybtn);
        final Button sectorbtn = view.findViewById(R.id.sectorbtn);
        loc = new location(getActivity().getApplicationContext(), cur_weather);

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

    void show_1(Set<String> sectors, final Button btn_view){
        final List<String> ListItems = new ArrayList<>();
        for (String elem: sectors){
            ListItems.add(elem);
        }
        ListItems.sort(null);
        final CharSequence[] items =  ListItems.toArray(new String[ListItems.size()]);

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


class weather {
    String ServiceKey = "GSdxY%2F7j7F0kYUPBy5Lkap8PFngA3%2FlgfMUh44rpvhndVEEXSi1TK3jK6I0qWiKzkGtXpALVJYJE2wYWSvYi2g%3D%3D";
    String base_date;
    String base_time;
    String global_nx = "66";
    String global_ny = "101";
    String type = "json";
    Context mContext;
    String result = "";
    HashMap<String, String> data;
    View fragment_view;

    public weather(Context context, View view){
        SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMdd_HHmm", Locale.KOREA);
        String Date_time = sdf.format(new Date());
        String[] array = Date_time.split("_");
        base_date = array[0];
        Log.d("basedate",base_date);
        base_time = array[1].substring(0,2) + "00";
        if (Integer.parseInt(base_time) - 100 < 0){
            base_time = Integer.toString(Integer.parseInt(base_time) - 100 + 2400);
            base_date = Integer.toString(Integer.parseInt(base_date) - 1);
        }
        else{
            base_time = Integer.toString(Integer.parseInt(base_time) - 100);
        }
        mContext = context;
        fragment_view = view;
    }

    private void getinfo_past(){
        SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMdd_HHmm", Locale.KOREA);
        String Date_time = sdf.format(new Date());
        String[] array = Date_time.split("_");
//        int days = Integer.parseInt(array[0]);
//        int time = Integer.parseInt(array[1]);
        int days = Integer.parseInt(base_date);
        int time = Integer.parseInt(base_time);
        time = time - 100;
        if (time < 0){
            days = days - 1;
            time = time + 2400;
        }

        base_date = Integer.toString(days);
        base_time = Integer.toString(time);
        getinfo(global_nx, global_ny);
    }

    public void getinfo(String nx, String ny){
        global_nx = nx;
        global_ny = ny;

        String request = "http://newsky2.kma.go.kr/service/SecndSrtpdFrcstInfoService2/ForecastGrib";
        request += "?serviceKey="+ServiceKey;
        request += "&base_date="+base_date;
        request += "&base_time="+base_time;
        request += "&nx=" + nx;
        request += "&ny=" + ny;
        request += "&numOfRows=10&pageNo=1&_type=json";
        Log.d("tab3",request);
        RequestQueue queue = Volley.newRequestQueue(mContext);
        StringRequest stringRequest = new StringRequest(Request.Method.GET, request,
            new Response.Listener<String>() {
                @Override
                public void onResponse(String response) {
                    result = response;
                    Log.d("response", result);
                    custom_jsonparsor aa = new custom_jsonparsor();
                    data = aa.weatherjsonParsor(response);
                    Log.d("parsing", data.toString());
                    if (data.isEmpty()){ // Data is not updated yet. Take data from one hour ago.
                        Log.d("getinfo","No data : Take data from past");
//                        getinfo_past();
                    }
                    else{
                        setView(data);
                    }

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

    }

    private void setView(HashMap<String, String> data){
        ImageView weathericon = fragment_view.findViewById(R.id.weathericon);
        TextView amount_rain= fragment_view.findViewById(R.id.amount_rain);
        TextView type_rain= fragment_view.findViewById(R.id.type_rain);
        TextView wind_mag= fragment_view.findViewById(R.id.wind_mag);
        TextView wind_dir= fragment_view.findViewById(R.id.wind_dir);
        TextView humidity= fragment_view.findViewById(R.id.humidity);
        TextView temp = fragment_view.findViewById(R.id.temp);
        int direction;
        SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMdd_HHmm", Locale.KOREA);
        String Date_time = sdf.format(new Date());
        String[] array = Date_time.split("_");
        boolean isNight = Integer.parseInt(array[1]) >= 1800 || Integer.parseInt(array[1]) <= 600;
        for (String key : data.keySet()){
            switch (key){
                case "PTY" :
                    if (data.get(key).equals("0")){
                        if (isNight){
                            weathericon.setImageResource(R.drawable.ic_3);
                        }
                        else{
                            weathericon.setImageResource(R.drawable.ic_2);
                        }
                    }
                    else if(data.get(key).equals("1")){
                        weathericon.setImageResource(R.drawable.ic_18);
                    }
                    else if(data.get(key).equals("2") || data.get(key).equals("3")){
                        weathericon.setImageResource(R.drawable.ic_23);
                    }
                    else{
                        weathericon.setImageResource(R.drawable.ic_20);
                    }
                    break;
                case "REH" :
                    humidity.setText("humidity : " + data.get(key));
                    break;
                case "RN1" :
                    amount_rain.setText("raining magnitude : " + data.get(key));
                    break;
                case "WSD" :
                    wind_dir.setText("wind speed : " + data.get(key));
                    break;
                case "T1H" :
                    temp.setText("temp : " + data.get(key));
                    break;
            }
        }

    }
}

class location{
    private Context mContext;
    private HashMap<Pair<String, String>, Pair<String,String>> LocationList;
    private String city= "";
    private String sector= "";
    private Set<String> cities = new LinkedHashSet<>();
    private Set<String> sectors = new LinkedHashSet<>();
    weather cur_weather;

    public location(Context context, weather cur){
        mContext = context;
        LocationList = makeLocationList();
        cur_weather = cur;
        setCities();
    }

    private HashMap<Pair<String, String>, Pair<String,String>> makeLocationList(){
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
                returnString.append(line);
                returnString.append(" ");
            }
            raw = returnString.toString();
            String[] parsing = raw.split("\\s");
            while(Arrays.asList(parsing).contains("")){
                List<String> list = new ArrayList<>(Arrays.asList(parsing));
                list.remove("");
                parsing = list.toArray(new String[list.size()]);
            }


            for (int i = 0; i < parsing.length; i = i + 4){
                Pair<String,String> tmp_key = Pair.create(parsing[i],parsing[i+1]);
                Pair<String,String> tmp_value = Pair.create(parsing[i+2],parsing[i+3]);
//                Log.d("tmp_key",tmp_key.toString());
//                Log.d("tmp_value",tmp_value.toString());
                result.put(tmp_key,tmp_value);
            }

            if (isr != null) isr.close();
            if (fIn != null) fIn.close();
            if (input != null) input.close();

            return result;
        }
        catch (IOException e){
            e.printStackTrace();
            return null;
        }
    }

    public HashMap<Pair<String, String>, Pair<String,String>> getLocationList(){
        return LocationList;
    }

    public String getCity(){
        return city;
    }

    public void setCity(String newCity){
        city = newCity;
        sectors.clear();
        for (Pair<String, String> elem: getLocationList().keySet()){
            if (elem.first.equals(getCity())){
                sectors.add(elem.second);
            }
        }
    }

    public String getSector(){
        return sector;
    }
    public void setSector(String newSector){
        sector = newSector;
        //Find location of selected city
        if (!getCity().equals("")){
            Pair<String, String> location = getLocationList().get(Pair.create(getCity(),getSector()));
            Log.d("tab3","hello");
            cur_weather.getinfo(location.first, location.second);
            Log.d("tab3","gello");
        }
    }

    public Set<String> getCities(){
        return cities;
    }

    public void setCities(){
        for (Pair<String, String> elem: getLocationList().keySet()){
            cities.add(elem.first);
        }
    }

    public Set<String> getSectors(){
        return sectors;
    }

}