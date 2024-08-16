package com.example.doanandroid;

import android.os.AsyncTask;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

public class MyUrlTask extends AsyncTask<String, Void, String> {

    @Override
    protected String doInBackground(String... urls) {
        HttpURLConnection connection = null;
        BufferedReader reader = null;
        StringBuilder result = new StringBuilder();

        try {
            // Mở kết nối đến URL được cung cấp
            URL url = new URL(urls[0]);
            connection = (HttpURLConnection) url.openConnection();
            connection.connect();

            // Đọc dữ liệu từ kết nối
            reader = new BufferedReader(new InputStreamReader(connection.getInputStream()));
            String line;
            while ((line = reader.readLine()) != null) {
                result.append(line).append("\n");
            }
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            // Đóng kết nối và đối tượng đọc
            if (connection != null) {
                connection.disconnect();
            }
            if (reader != null) {
                try {
                    reader.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
        return result.toString();
    }

    @Override
    protected void onPostExecute(String result) {
        // Xử lý kết quả sau khi thực hiện xong
        // Ví dụ: hiển thị dữ liệu trong giao diện người dùng
        // result chứa dữ liệu trả về từ URL
    }
}
