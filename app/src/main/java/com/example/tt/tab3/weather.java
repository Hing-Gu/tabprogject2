package com.example.tt.tab3;

import android.content.Context;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.PopupWindow;
import android.widget.TextView;

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
import com.example.tt.R;
import com.example.tt.helper.Utils;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Locale;

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
        LayoutInflater inflater = (LayoutInflater) mContext.getSystemService(mContext.LAYOUT_INFLATER_SERVICE);
        View popupView = inflater.inflate(R.layout.fragment3_popup, null);
        Utils utils = new Utils(mContext);


        ImageView weathericon = popupView.findViewById(R.id.weathericon);
        TextView amount_rain= popupView.findViewById(R.id.amount_rain);
        TextView type_rain= popupView.findViewById(R.id.type_rain);
        TextView wind_mag= popupView.findViewById(R.id.wind_mag);
        TextView wind_dir= popupView.findViewById(R.id.wind_dir);
        TextView humidity= popupView.findViewById(R.id.humidity);
        TextView temp = popupView.findViewById(R.id.temp);
        int direction;
        SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMdd_HHmm", Locale.KOREA);
        String Date_time = sdf.format(new Date());
        String[] array = Date_time.split("_");
        boolean isNight = Integer.parseInt(array[1]) >= 1800 || Integer.parseInt(array[1]) <= 600;



        final PopupWindow mPopupWindow = new PopupWindow(popupView, LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.WRAP_CONTENT);
        //popupView 에서 (LinearLayout 을 사용) 레이아웃이 둘러싸고 있는 컨텐츠의 크기 만큼 팝업 크기를 지정

        mPopupWindow.setFocusable(true);
        // 외부 영역 선택히 PopUp 종료

        mPopupWindow.showAtLocation(popupView, Gravity.CENTER, 0, 0);


        Button ok = popupView.findViewById(R.id.okbtn);
        ok.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mPopupWindow.dismiss();
            }
        });


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
