package com.example.doanandroid;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

public class LoginActivity extends AppCompatActivity {

    private EditText emailEditText, passwordEditText;
    private Button loginButton, registerButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        emailEditText = findViewById(R.id.emailEditText);
        passwordEditText = findViewById(R.id.passwordEditText);
        loginButton = findViewById(R.id.loginButton);
        registerButton = findViewById(R.id.RegisterButton);

        // Kiểm tra thông tin đăng nhập khi mở lại ứng dụng
        checkSavedLogin();

        loginButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String email = emailEditText.getText().toString().trim();
                String password = passwordEditText.getText().toString().trim();

                if (!TextUtils.isEmpty(email) && !TextUtils.isEmpty(password)) {
                    // Đăng nhập
                    loginUser(email, password);
                } else {
                    // Hiển thị thông báo lỗi khi trường email hoặc password trống
                    Toast.makeText(LoginActivity.this, "Email và mật khẩu không được để trống.", Toast.LENGTH_SHORT).show();
                }
            }
        });

        registerButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(LoginActivity.this, RegisterActivity.class));
            }
        });
    }

    private void checkSavedLogin() {
        SharedPreferences prefs = getSharedPreferences("MyPrefs", MODE_PRIVATE);
        String email = prefs.getString("email", "");
        String password = prefs.getString("password", "");

        if (!TextUtils.isEmpty(email) && !TextUtils.isEmpty(password)) {
            loginUser(email, password);
        }
    }

    private void loginUser(String email, String password) {

        String url = getResources().getString(R.string.url) + "login.php";
        StringRequest stringRequest = new StringRequest(Request.Method.POST, url,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        try {

                            JSONObject jsonResponse = new JSONObject(response);
                            String status = jsonResponse.getString("status");
                            if (status.equals("success")) {
                                // Lưu thông tin đăng nhập vào SharedPreferences
                                saveLoginInfo(email, password);
                                // Lấy và lưu thông tin khách hàng
                                String url1 = getResources().getString(R.string.url) + "timKhachHang.php?Email=" + email;
                                StringRequest stringRequest1 = new StringRequest(Request.Method.GET, url1, new Response.Listener<String>() {
                                    @Override
                                    public void onResponse(String responses) {
                                        try {
                                            JSONObject jsonObject = new JSONObject(responses);
                                            JSONArray jsonArray = jsonObject.getJSONArray("data");
                                            if (jsonArray.length() > 0) {
                                                JSONObject jsonObject1 = jsonArray.getJSONObject(0);
                                                // Lấy thông tin của khách hàng từ JSON object
                                                int maKhachHang = jsonObject1.getInt("MaKhachHang");
                                                String tenKhachHang = jsonObject1.getString("TenKhachHang");
                                                String soDienThoai = jsonObject1.getString("SoDienThoai");
                                                String diaChiGiaoHang = jsonObject1.getString("DiaChiGiaoHang");
                                                int diemTichLuy = jsonObject1.getInt("DiemTichLuy");

                                                // Lưu thông tin khách hàng vào SharedPreferences
                                                SharedPreferences.Editor editor = getSharedPreferences("MyPrefs", MODE_PRIVATE).edit();
                                                editor.putInt("maKhachHang", maKhachHang);
                                                editor.putString("tenKhachHang", tenKhachHang);
                                                editor.putString("soDienThoai", soDienThoai);
                                                editor.putString("diaChiGiaoHang", diaChiGiaoHang);
                                                editor.putInt("diemTichLuy", diemTichLuy);
                                                editor.apply();
                                            }

                                        } catch (JSONException e) {
                                            throw new RuntimeException(e);
                                        }
                                    }
                                }, new Response.ErrorListener() {
                                    @Override
                                    public void onErrorResponse(VolleyError error) {
                                        error.printStackTrace();
                                    }
                                });

                                RequestQueue requestQueue1 = Volley.newRequestQueue(LoginActivity.this);
                                requestQueue1.add(stringRequest1);

                                // Chuyển đến màn hình chính
                                startActivity(new Intent(LoginActivity.this, MainActivity.class));
                                finish();
                            } else {
                                String message = jsonResponse.getString("message");
                                Toast.makeText(LoginActivity.this, message, Toast.LENGTH_SHORT).show();
                            }
                        } catch (JSONException e) {
                            e.printStackTrace();
                            Toast.makeText(LoginActivity.this, "JSON parsing error: " + e.getMessage(), Toast.LENGTH_SHORT).show();
                        }
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Toast.makeText(LoginActivity.this, "Error: " + error.getMessage(), Toast.LENGTH_SHORT).show();
                    }
                }) {
            @Override
            protected Map<String, String> getParams() {
                Map<String, String> params = new HashMap<>();
                params.put("email", email);
                params.put("password", password);
                return params;
            }
        };

        RequestQueue requestQueue = Volley.newRequestQueue(this);
        requestQueue.add(stringRequest);
    }


    private void saveLoginInfo(String email, String password) {
        SharedPreferences sharedPreferences = getSharedPreferences("MyPrefs", MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString("email", email);
        editor.putString("password", password);
        editor.apply();
    }

}
