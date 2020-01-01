package com.example.tt.tab3;

import android.content.Context;
import android.content.res.AssetManager;
import android.util.Log;
import android.util.Pair;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Set;

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
            cur_weather.getinfo(location.first, location.second, getCity(), getSector());
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

