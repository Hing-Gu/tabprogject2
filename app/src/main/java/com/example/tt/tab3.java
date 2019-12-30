package com.example.tt;


import android.os.AsyncTask;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import org.jsoup.Connection;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Element;
import org.jsoup.nodes.Document;
import org.jsoup.select.Elements;


import java.io.IOException;


/**
 * A simple {@link Fragment} subclass.
 */
public class tab3 extends Fragment {


    private Object TextView;

    public tab3() {
        // Required empty public constructor
    }


    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                            Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        TextView weather_text;
        getActivity().setContentView(R.layout.activity_main);

        View view= inflater.inflate(R.layout.fragment_tab3, container, false);
        weather_text = view.findViewById(R.id.weather);

        new WeatherAsynTask(weather_text).execute("https://weather.naver.com/","span[class=temp]");
        return view;
    }

}

class WeatherAsynTask extends AsyncTask<String,Void,String> {
    TextView textView;

    public WeatherAsynTask(TextView textView) {
        this.textView = textView;

    }

    @Override
    public String doInBackground(String... params) {
        String URL = params[0];
        String EI = params[1];
        String result = "";

        try {
            Document document1 = Jsoup.connect(URL).get();
            Document document2 = Jsoup.connect(URL).post();
            Connection.Response response = Jsoup.connect(URL).method(Connection.Method.GET).execute();
            Document document3 = response.parse();
            Elements elements = document3.select(EI);

            for (Element element : elements) {
                String tem;
                if (element != null) {
                    tem = element.text();
                    result=result+" "+tem;

                }
            }
            Log.d("test",result);
            return result;
        }catch (IOException e){
            e.printStackTrace();
        }

        return null;
    }
    protected void onPostExecute(String s){
        super.onPostExecute(s);
        textView.setText(s);
    }
}
