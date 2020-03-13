package cxp.demo.utils;

import com.google.gson.Gson;
import cxp.demo.utils.model.Keys;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

public class AadUtils {

    public static Keys getKeys(String tenant) throws Exception{
        Keys keys = null;

        String url = "https://login.microsoftonline.com/" + tenant + "/discovery/v2.0/keys";
        HttpURLConnection httpURLConnection = (HttpURLConnection) new URL(url).openConnection();
        httpURLConnection.setRequestMethod("GET");
        httpURLConnection.setRequestProperty("Accept", "application/json");
        httpURLConnection.setDoInput(true);
        httpURLConnection.connect();

        int code = httpURLConnection.getResponseCode();
        if(code >= 200 && code <= 300) {
            try(InputStream inputStream = httpURLConnection.getInputStream();
                InputStreamReader inputStreamReader = new InputStreamReader(inputStream);
                BufferedReader bufferedReader = new BufferedReader(inputStreamReader)){
                StringBuilder sb = new StringBuilder();
                String line = "";

                while ((line = bufferedReader.readLine()) != null) {
                    sb.append(line);
                }
                keys = new Gson().fromJson(sb.toString(), Keys.class);
            }
        }else{
            throw new RuntimeException("Failed to get keys");
        }

        httpURLConnection.disconnect();

        return keys;
    }

}
