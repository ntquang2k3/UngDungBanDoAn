package com.example.doanandroid;


import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.example.doanandroid.POJO.Mon;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

public class RegisterActivity extends AppCompatActivity {

    int maDonHang;
    private EditText tenEditText, sdtEditText, emailEditText, matKhauEditText, diaChiEditText;
    private Button dangKyButton, dangNhapButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        tenEditText = findViewById(R.id.edtTen);
        sdtEditText = findViewById(R.id.edtSDT);
        emailEditText = findViewById(R.id.edtEmail);
        matKhauEditText = findViewById(R.id.edtMatKhau);
        diaChiEditText = findViewById(R.id.edtDiaChi);
        dangKyButton = findViewById(R.id.btnDangKy);
        dangNhapButton = findViewById(R.id.btnDangNhap);

        dangKyButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dangKy();
            }
        });
        dangNhapButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
    }

    private void dangKy() {
        String ten = tenEditText.getText().toString().trim();
        String sdt = sdtEditText.getText().toString().trim();
        String email = emailEditText.getText().toString().trim();
        String matKhau = matKhauEditText.getText().toString().trim();
        String diaChi = diaChiEditText.getText().toString().trim();

        if (TextUtils.isEmpty(ten) || TextUtils.isEmpty(sdt) || TextUtils.isEmpty(email)
                || TextUtils.isEmpty(matKhau) || TextUtils.isEmpty(diaChi)) {
            Toast.makeText(RegisterActivity.this, "Vui lòng điền đầy đủ thông tin", Toast.LENGTH_SHORT).show();
            return;
        }

        // Gửi yêu cầu đăng ký đến server
        String url = getResources().getString(R.string.url) + "Register.php";
        StringRequest stringRequest = new StringRequest(Request.Method.POST, url,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        try {
                            JSONObject jsonResponse = new JSONObject(response);
                            String status = jsonResponse.getString("status");
                            String message = jsonResponse.getString("message");
                            Toast.makeText(RegisterActivity.this, message, Toast.LENGTH_SHORT).show();
                            if (status.equals("success")) {
                                // Đăng ký thành công, chuyển đến activity khác hoặc thực hiện các hành động khác
                                // Tìm mã khách hàng dựa vào email
                                String url1 = getResources().getString(R.string.url) + "timKhachHang.php?Email=" + email;
                                StringRequest stringRequest1 = new StringRequest(Request.Method.GET, url1, new Response.Listener<String>() {
                                    @Override
                                    public void onResponse(String responses) {
                                        try {
                                            JSONObject jsonObject = new JSONObject(responses);
                                            JSONArray jsonArray = jsonObject.getJSONArray("data");
                                            if (jsonArray.length() > 0) {
                                                JSONObject jsonObject1 = jsonArray.getJSONObject(0);
                                                // Lấy mã khách hàng vừa tạo
                                                int maKhachHang = jsonObject1.getInt("MaKhachHang");
                                                // Tạo 1 đơn hàng ảo
                                                String url = getResources().getString(R.string.url) + "thuchientruyvan.php?query=" + "INSERT INTO `donhang`(`MaKhachHang`) VALUES ('" + maKhachHang + "')";
                                                khoiChay(url);
                                                // Tạo 1 chi tiết hóa đơn
                                                String urlLayDonVuaTao = getResources().getString(R.string.url) + "DuLieuDonHang.php?laymoi=true";
                                                layMoiDonHangThemChiTiet(urlLayDonVuaTao);
                                                //Thanh toán
                                                // Chuyển hướng đến trang chủ đăng nhập
                                                Intent intent = new Intent(RegisterActivity.this, LoginActivity.class);
                                                startActivity(intent);
                                                finish();
                                            }

                                        } catch (JSONException e) {
                                            throw new RuntimeException(e);
                                        }
                                    }
                                }, new Response.ErrorListener() {
                                    @Override
                                    public void onErrorResponse(VolleyError error) {
                                        error.printStackTrace();
                                        Toast.makeText(RegisterActivity.this, "Lỗi kết nối đến server (url1): " + error.getMessage(), Toast.LENGTH_SHORT).show();
                                    }
                                });

                                RequestQueue requestQueue = Volley.newRequestQueue(RegisterActivity.this);
                                requestQueue.add(stringRequest1);

                            }
                        } catch (JSONException e) {
                            e.printStackTrace();
                            Toast.makeText(RegisterActivity.this, "Lỗi khi xử lý dữ liệu từ server", Toast.LENGTH_SHORT).show();
                        }
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        // Xử lý lỗi khi kết nối đến server
                        Toast.makeText(RegisterActivity.this, "Lỗi kết nối đến server: " + error.getMessage(), Toast.LENGTH_SHORT).show();
                    }
                }) {
            @Override
            protected Map<String, String> getParams() {
                Map<String, String> params = new HashMap<>();
                params.put("tenKhachHang", ten);
                params.put("soDienThoai", sdt);
                params.put("email", email);
                params.put("matKhau", matKhau);
                params.put("diaChiGiaoHang", diaChi);
                return params;
            }
        };

        RequestQueue requestQueue = Volley.newRequestQueue(this);
        requestQueue.add(stringRequest);
    }

    public void khoiChay(String url) {
        new MyUrlTask().execute(url);
    }
    public void layMoiDonHangThemChiTiet(String url) {
        RequestQueue requestQueue = Volley.newRequestQueue(getApplicationContext());
        StringRequest stringRequest = new StringRequest(Request.Method.GET, url,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        try {
                            parseJsonDataDonHang(response);
                        } catch (JSONException e) {
                            throw new RuntimeException(e);
                        }
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(getApplicationContext(), "error", Toast.LENGTH_SHORT).show();
            }
        });
        requestQueue.add(stringRequest);

    }
    public void parseJsonDataDonHang(String response) throws JSONException {
        JSONArray loaiMonArray = new JSONArray(response);
        TextView tv= findViewById(R.id.tvMaDonHangRegister);
        for (int i = 0; i < loaiMonArray.length(); i++) {
            JSONObject cuaHang = loaiMonArray.getJSONObject(i);
            Mon a = new Mon();
            maDonHang = cuaHang.getInt("MaDonHang");
            tv.setText(String.valueOf(maDonHang));
        }
        maDonHang = Integer.parseInt(tv.getText().toString());
        //Sau khi lấy rôi thì thêm mới đơn hàng đó vào chi tiết thêm chi tiết đơn hàng
        String urlThemChiTietDonHang = getResources().getString(R.string.url)+"thuchientruyvan.php?query="+"INSERT INTO `chitietdonhang`(`MaDonHang`, `MaMon`, `SoLuong`, `ThanhTien`) VALUES ('"+maDonHang+"','100','1','100000')";
        khoiChay(urlThemChiTietDonHang);
    }
}
