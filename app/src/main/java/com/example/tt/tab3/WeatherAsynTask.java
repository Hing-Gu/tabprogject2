package com.example.tt.tab3;


import android.os.AsyncTask;
import android.util.Log;
import android.widget.TextView;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.io.IOException;

class WeatherAsynTask extends AsyncTask<String,Void,String> {
    TextView textView;
    TextView textview2;

    public WeatherAsynTask(TextView textView, TextView textview2) {
        this.textview2= textview2;
        this.textView = textView;
    }

    @Override
    public String doInBackground(String... params) {
        String URL = params[0];
        String EI = params[1];
        String result = "";

        try {
            Document document = Jsoup.connect(URL).get();
            /*Document document2 = Jsoup.connect(URL).post();
            Connection.Response response = Jsoup.connect(URL).method(Connection.Method.GET).execute();
            Document document3 = response.parse();*/
            Elements elements = document.select(EI);

            for (Element element : elements) {
                String tem;
                if (element != null) {
                    tem = element.text();
                    result=result+" "+tem;

                }

            }
            return result;
        }catch (IOException e){
            e.printStackTrace();
        }

        return null;
    }
    protected void onPostExecute(String s){
        super.onPostExecute(s);


        Log.d("test",s);
        String[] array_word;
        Log.d("test2",s);
        array_word = s.split(" ");

        String now = array_word[13];
        for(int k=14;k<array_word.length-1;k++){
            Log.d("test"+k,array_word[k]);
            if(array_word[k].contains("-"));
            else
                array_word[k]="\t"+array_word[k];
        }
        String now2 = array_word[14]+"\n"+array_word[15]+"\n"+array_word[16]+"\n"+array_word[17];
        textView.setText(now2);
        textview2.setText(now);
    }

}