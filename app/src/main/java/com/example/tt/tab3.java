package com.example.tt;


import android.content.Context;
import android.content.res.AssetManager;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.util.Log;
import android.util.Pair;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

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

import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.poifs.filesystem.POIFSFileSystem;
import org.apache.poi.ss.formula.functions.Column;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.lang.reflect.Array;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Locale;


/**
 * A simple {@link Fragment} subclass.
 */
public class tab3 extends Fragment {


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
        location loc = new location(getActivity().getApplicationContext());
        HashMap<Pair<String,String>,Pair<String,String>> loc_hashmap = loc.makeLocationList();
        

        return inflater.inflate(R.layout.fragment_tab3, container, false);
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
        String request = "http://newsky2.kma.go.kr/service/SecndSrtpdFrcstInfoService2/ForecastGrib";
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
    public HashMap<Pair<String, String>, Pair<String,String>> makeLocationList()
    {
        AssetManager am = mContext.getAssets();

        InputStream is = null;
        try {
            is = am.open("location.xlsx");
        } catch (IOException e) {
            e.printStackTrace();
        }

        POIFSFileSystem myFileSystem = null;
        try {
            myFileSystem = new POIFSFileSystem(is);
        } catch (IOException e) {
            e.printStackTrace();
        }

        HSSFWorkbook myWorkBook = null;
        try {
            myWorkBook = new HSSFWorkbook(myFileSystem);
        } catch (IOException e) {
            e.printStackTrace();
        }

        HSSFSheet mySheet = myWorkBook.getSheetAt(0);

        Iterator<Row> rowIter = mySheet.rowIterator();
        int rowno = 0;

        //Create empty hashmap
        HashMap<Pair<String, String>, Pair<String,String>> tmp_hashmap = new HashMap<>();

        while (rowIter.hasNext()) {
            Log.e("location", " row no "+ rowno );

            HSSFRow myRow = (HSSFRow) rowIter.next();
            if(rowno !=0) {
                Iterator<Cell> cellIter = myRow.cellIterator();
                int colno =0;
                String city="", sector="", det="";
                while (cellIter.hasNext()) {
                    HSSFCell myCell = (HSSFCell) cellIter.next();
                    if (colno==0){
                        city = myCell.toString();
                    }else if (colno==1){
                        sector = myCell.toString();
                    }

                    Pair<String, String> tmp_key = Pair.create(city, sector);
                    if (city.equals("") && sector.equals("") && !tmp_hashmap.containsKey(tmp_key)){
                        myCell = (HSSFCell)cellIter.next();
                        myCell = (HSSFCell)cellIter.next();
                        String x = myCell.toString();
                        myCell = (HSSFCell)cellIter.next();
                        String y = myCell.toString();
                        Pair<String, String> tmp_value = Pair.create(x, y);
                        tmp_hashmap.put(tmp_key,tmp_value);
                        Log.e("location",  tmp_key.toString() + ":" + tmp_value.toString());
                    }
                    colno++;
                }
            }
            rowno++;
        }

        return tmp_hashmap;
    }
}