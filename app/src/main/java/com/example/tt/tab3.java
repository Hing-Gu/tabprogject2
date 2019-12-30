package com.example.tt;


import android.os.AsyncTask;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Element;
import org.jsoup.nodes.Document;
import org.jsoup.select.Elements;
/*import org.w3c.dom.Document;*/

import java.io.IOException;


/**
 * A simple {@link Fragment} subclass.
 */
public class tab3 extends Fragment {

    TextView weather_text;
    private Object TextView;

    public tab3() {
        // Required empty public constructor
    }


    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                            Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getActivity().setContentView(R.layout.activity_main);

        View view= inflater.inflate(R.layout.fragment_tab3, container, false);
        weather_text = (TextView) view.findViewById(R.id.weather);

        new WeatherAsynTask(weather_text).execute("http://www.kma.go.kr/index.jsp","dl[class=temp  plus]");

        return view;
    }

}

class WeatherAsynTask extends AsyncTask<String,Void,String> {
    TextView textView;
    public WeatherAsynTask(TextView textView){
        this.textView = textView;

    }

    @Override
    protected String doInBackground(String... params) {
        String URL = params[0];
        String EI = params[1];
        String result = "";

        try{
            Document document = (Document) Jsoup.connect(URL).get();
            Elements elements = document.select(EI);

            for(Element element : elements){
                System.out.println(result = result+element.text());
            }
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