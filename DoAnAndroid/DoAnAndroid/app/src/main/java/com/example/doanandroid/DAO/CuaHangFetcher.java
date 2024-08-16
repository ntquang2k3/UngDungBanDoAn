package com.example.doanandroid.DAO;

import android.os.AsyncTask;
import android.util.Log;

import com.example.doanandroid.POJO.CuaHang;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.concurrent.ExecutionException;

public class CuaHangFetcher {

    public static ArrayList<CuaHang> fetchCuaHangList(String urlString) {
        ArrayList<CuaHang> cuaHangList = new ArrayList<>();

        try {
            URL url = new URL(urlString);
            HttpURLConnection conn = (HttpURLConnection) url.openConnection();
            conn.setRequestMethod("GET");
            conn.connect();

            int responseCode = conn.getResponseCode();
            if (responseCode != 200) {
                throw new RuntimeException("HttpResponseCode: " + responseCode);
            }

            BufferedReader in = new BufferedReader(new InputStreamReader(conn.getInputStream()));
            StringBuilder content = new StringBuilder();
            String inputLine;
            while ((inputLine = in.readLine()) != null) {
                content.append(inputLine);
            }
            in.close();
            conn.disconnect();

            Gson gson = new Gson();
            cuaHangList = gson.fromJson(content.toString(), new TypeToken<ArrayList<CuaHang>>() {}.getType());

        } catch (Exception e) {
            Log.e("FetchCuaHangTask", "Error fetching data from URL: " + e.getMessage());
        }



        return cuaHangList;

    }
}

