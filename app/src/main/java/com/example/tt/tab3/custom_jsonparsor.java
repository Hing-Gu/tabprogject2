package com.example.tt.tab3;

import android.util.Log;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;

public class custom_jsonparsor {
    public void custom_jsonparsor(){
    }

    public HashMap<String, String> weatherjsonParsor(String jsonString){
        String baseDate = null;
        String baseTime = null;
        HashMap<String, String> weather_info = new HashMap<>();

        try {
            Log.d("json","start");
            if (jsonString.isEmpty()){
                jsonString = "{\"response\":{\"header\":{\"resultCode\":\"0000\",\"resultMsg\":\"OK\"},\"body\":{\"items\":{\"item\":[{\"baseDate\":20191231,\"baseTime\":1200,\"category\":\"PTY\",\"nx\":55,\"ny\":126,\"obsrValue\":0},{\"baseDate\":20191231,\"baseTime\":1200,\"category\":\"REH\",\"nx\":55,\"ny\":126,\"obsrValue\":39},{\"baseDate\":20191231,\"baseTime\":1200,\"category\":\"RN1\",\"nx\":55,\"ny\":126,\"obsrValue\":0},{\"baseDate\":20191231,\"baseTime\":1200,\"category\":\"T1H\",\"nx\":55,\"ny\":126,\"obsrValue\":-4.7},{\"baseDate\":20191231,\"baseTime\":1200,\"category\":\"UUU\",\"nx\":55,\"ny\":126,\"obsrValue\":1.9},{\"baseDate\":20191231,\"baseTime\":1200,\"category\":\"VEC\",\"nx\":55,\"ny\":126,\"obsrValue\":288},{\"baseDate\":20191231,\"baseTime\":1200,\"category\":\"VVV\",\"nx\":55,\"ny\":126,\"obsrValue\":-0.5},{\"baseDate\":20191231,\"baseTime\":1200,\"category\":\"WSD\",\"nx\":55,\"ny\":126,\"obsrValue\":2}]},\"numOfRows\":10,\"pageNo\":1,\"totalCount\":8}}}";
            }

            JSONObject jobj = new JSONObject(jsonString);
            Log.d("json","0");

            JSONObject response = jobj.getJSONObject("response");
            Log.d("json","response");

            JSONObject body = response.getJSONObject("body");
            Log.d("json","body");

            String totalCount = body.optString("totalCount");
            Log.d("json","totalCount");

            if (totalCount.equals("0")) return weather_info;

            JSONObject item = body.getJSONObject("items");
            Log.d("json","items");



            JSONArray items = item.getJSONArray("item");
            Log.d("json","item");

            weather_info.put("totalCount",totalCount);
            for (int i = 0; i < items.length(); i++){
                JSONObject tmp_obj = items.getJSONObject(i);
                if (i == 0){
                    baseDate = tmp_obj.optString("baseDate");
                    baseTime = tmp_obj.optString("baseTime");
                    weather_info.put("baseDate",baseDate);
                    weather_info.put("baseTime",baseTime);
                }
                String category = tmp_obj.optString("category");
                String obsrValue = tmp_obj.optString("obsrValue");
                weather_info.put(category,obsrValue);
            }

        } catch (JSONException e) {
            e.printStackTrace();
        }
        return weather_info;
    }
}
