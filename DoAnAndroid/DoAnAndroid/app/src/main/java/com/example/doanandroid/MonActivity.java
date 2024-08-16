package com.example.doanandroid;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.example.doanandroid.POJO.Mon;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.text.NumberFormat;
import java.util.Locale;

public class MonActivity extends AppCompatActivity {

    TextView tvTenMon,tvMoTa,tvGiaBan,tvSoLuong, tvMaDonHang;
    Button btnTru,btnCong,btnCapNhat;
    ImageView anhmon;
    int maMon;
    int maCuaHang;
    int maKhachHang;
    int maDonHang;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_mon);
        // Nhận dữ liệu từ Intent
        Intent intent = getIntent();
        maCuaHang = intent.getIntExtra("MA_CUA_HANG",0);
        maMon = intent.getIntExtra("MA_MON",0);
        String tenMon = intent.getStringExtra("TEN_MON");
        String moTa = intent.getStringExtra("MO_TA");
        String anh = intent.getStringExtra("ANH");
        int giaMon = intent.getIntExtra("GIA_MON",0);
        //Lấy dữ liệu từ SharedPreferences
        SharedPreferences sharedPreferences = this.getSharedPreferences("MyPrefs", MODE_PRIVATE);
        maKhachHang = sharedPreferences.getInt("maKhachHang", 0);
        // Hiển thị thông tin món trong activity
        mappingIDs();
        tvTenMon.setText(tenMon);
        tvMoTa.setText(moTa);
        tvGiaBan.setText(String.valueOf(giaMon));
        tvSoLuong.setText(String.valueOf(1));
        int resID = getResources().getIdentifier(anh, "drawable", getPackageName());
        if (resID != 0) {
            Drawable drawable = getResources().getDrawable(resID);
            anhmon.setImageDrawable(drawable);
        }
        // Sử dụng NumberFormat để định dạng số
        NumberFormat numberFormat = NumberFormat.getInstance(new Locale("vi", "VN"));
        String formattedNumber = numberFormat.format(giaMon);
        btnCapNhat.setText("Cập nhật giỏ hàng "+formattedNumber+" VNĐ");
        addEvents();
    }

    private void addEvents() {
        btnTru.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                int soLuongDat = Integer.parseInt(tvSoLuong.getText().toString());
                int giaBan = Integer.parseInt(tvGiaBan.getText().toString());
                if (soLuongDat > 1 )
                {
                    soLuongDat = soLuongDat - 1;
                    tvSoLuong.setText(String.valueOf(soLuongDat));
                    int thanhTien = soLuongDat * giaBan;
                    // Sử dụng NumberFormat để định dạng số
                    NumberFormat numberFormat = NumberFormat.getInstance(new Locale("vi", "VN"));
                    String formattedNumber = numberFormat.format(thanhTien);
                    btnCapNhat.setText("Cập nhật giỏ hàng "+formattedNumber+" VNĐ");
                }
            }
        });
        btnCong.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                int soLuongDat = Integer.parseInt(tvSoLuong.getText().toString());
                int giaBan = Integer.parseInt(tvGiaBan.getText().toString());
                soLuongDat = soLuongDat + 1;
                tvSoLuong.setText(String.valueOf(soLuongDat));
                int thanhTien = soLuongDat * giaBan;
                // Sử dụng NumberFormat để định dạng số
                NumberFormat numberFormat = NumberFormat.getInstance(new Locale("vi", "VN"));
                String formattedNumber = numberFormat.format(thanhTien);
                btnCapNhat.setText("Cập nhật giỏ hàng "+formattedNumber+" VNĐ");
            }
        });
        btnCapNhat.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(MonActivity.this, "Đã thêm vào giỏ hàng thành công", Toast.LENGTH_SHORT).show();
                //Thực hiện cho khách hàng
                String urlTimKiemDonHang = getResources().getString(R.string.url)+"DuLieuDonHangVuaTao.php?MaKhachHang="+maKhachHang;
                getTimCuaHangTrongDon(urlTimKiemDonHang);
                //Thêm mới đơn hàng
                //String url = "http://"+"192.168.60.62"+"/DoAnAndroid/thuchientruyvan.php?query="+"INSERT INTO `donhang`(`MaKhachHang`) VALUES ('"+maKhachHang+"')";
                //khoiChay(url);
                //Sau khi tạo mới xong thì lấy mã đơn hàng vừa tạo
                //String urlLayDonVuaTao = "http://"+"192.168.60.62"+"/DoAnAndroid/DuLieuDonHang.php?laymoi=true";
                //layMoiDonHangThemChiTiet(urlLayDonVuaTao);
                //Thêm mới chi tiết đơn hàng
                finish();
            }
        });
    }
    public void khoiChay(String url) {
        new MyUrlTask().execute(url);
    }


    private void mappingIDs() {
        tvTenMon = findViewById(R.id.tvTenMonMA);
        tvMoTa = findViewById(R.id.tvMoTaMonMA);
        tvGiaBan = findViewById(R.id.tvGiaMonMA);
        tvSoLuong = findViewById(R.id.tvSoLuongMA);
        btnTru = findViewById(R.id.btnTruMA);
        btnCong = findViewById(R.id.btnCongMA);
        btnCapNhat = findViewById(R.id.btnThanhTienMA);
        tvMaDonHang = findViewById(R.id.tvMaDonHangMA);
        anhmon = findViewById(R.id.imgAnhMonMA);
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
        for (int i = 0; i < loaiMonArray.length(); i++) {
            JSONObject cuaHang = loaiMonArray.getJSONObject(i);
            Mon a = new Mon();
            maDonHang = cuaHang.getInt("MaDonHang");
            tvMaDonHang.setText(String.valueOf(maDonHang));
        }
        maDonHang = Integer.parseInt(tvMaDonHang.getText().toString());
        //Sau khi lấy rôi thì thêm mới đơn hàng đó vào chi tiết thêm chi tiết đơn hàng
        int soLuongDat = Integer.parseInt(tvSoLuong.getText().toString());
        int thanhTien = soLuongDat * Integer.parseInt(tvGiaBan.getText().toString());
        String urlThemChiTietDonHang = getResources().getString(R.string.url)+"thuchientruyvan.php?query="+"INSERT INTO `chitietdonhang`(`MaDonHang`, `MaMon`, `SoLuong`, `ThanhTien`) VALUES ('"+maDonHang+"','"+maMon+"','"+soLuongDat+"','"+thanhTien+"')";
        khoiChay(urlThemChiTietDonHang);
    }

    public void getTimCuaHangTrongDon(String url) {
        RequestQueue requestQueue = Volley.newRequestQueue(getApplicationContext());
        StringRequest stringRequest = new StringRequest(Request.Method.GET, url,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        try {
                            parseJsonTimCuaHangTrongDon(response);
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

    public void parseJsonTimCuaHangTrongDon(String response) throws JSONException {
        JSONArray loaiMonArray = new JSONArray(response);
        boolean existCuaHang = false;
        for (int i = 0; i < loaiMonArray.length(); i++) {
            JSONObject cuaHang = loaiMonArray.getJSONObject(i);
            int maDonHangTrongGio = cuaHang.getInt("MaDonHang");
            int maCuaHangItem = cuaHang.getInt("MaCuaHang");
            int trongGio = cuaHang.getInt("TrongGioHang");
            int maMonGio = cuaHang.getInt("MaMon");
            int soLuongCu = cuaHang.getInt("SoLuong");
            if (maCuaHangItem == maCuaHang && trongGio == 1)
                //Đã có cửa hàng này trong giỏ
            {
                existCuaHang = true;
                //Thực hiện thêm món vào chitietdonhang
                //Nếu món này đã có trong giỏ
                if (maMon == maMonGio)
                {
                    int soLuonThem = Integer.parseInt(tvSoLuong.getText().toString());
                    int soLuongMoi = soLuongCu + soLuonThem;
                    int thanhTien = soLuongMoi * Integer.parseInt(tvGiaBan.getText().toString());
                    //Thực hiện cập nhật số lượng và thành tiền
                    String url = getResources().getString(R.string.url)+"thuchientruyvan.php?query="+"UPDATE `chitietdonhang` SET `SoLuong`='"+soLuongMoi+"',`ThanhTien`='"+thanhTien+"' WHERE MaDonHang = '"+maDonHangTrongGio+"' and MaMon = '"+maMonGio+"'";
                    khoiChay(url);
                    return;
                }
                else{
                    //Món này món mới chưa có trong giỏ
                    //Thêm mới đơn hàng đó vào chi tiết thêm chi tiết đơn hàng
                    int soLuongDat = Integer.parseInt(tvSoLuong.getText().toString());
                    int thanhTien = soLuongDat * Integer.parseInt(tvGiaBan.getText().toString());
                    String urlThemChiTietDonHang = getResources().getString(R.string.url)+"thuchientruyvan.php?query="+"INSERT INTO `chitietdonhang`(`MaDonHang`, `MaMon`, `SoLuong`, `ThanhTien`) VALUES ('"+maDonHangTrongGio+"','"+maMon+"','"+soLuongDat+"','"+thanhTien+"')";
                    khoiChay(urlThemChiTietDonHang);
                }
            }
        }
        //Hết vòng  for
        if (existCuaHang == false) {
            // Thêm mới đơn hàng
            String url = getResources().getString(R.string.url) + "thuchientruyvan.php?query=" + "INSERT INTO `donhang`(`MaKhachHang`) VALUES ('" + maKhachHang + "')";

            khoiChay(url, new VolleyCallback() {
                @Override
                public void onSuccess() {
                    // Sau khi tạo mới xong thì lấy mã đơn hàng vừa tạo
                    String urlLayDonVuaTao = getResources().getString(R.string.url) + "DuLieuDonHang.php?laymoi=true";
                    layMoiDonHangThemChiTiet(urlLayDonVuaTao);
                }

                @Override
                public void onFailure(String errorMessage) {
                    // Xử lý lỗi nếu có
                    Toast.makeText(getApplicationContext(), "Lỗi: " + errorMessage, Toast.LENGTH_SHORT).show();
                }
            });
        }

    }
    private void khoiChay(String url, final VolleyCallback callback) {
        RequestQueue requestQueue = Volley.newRequestQueue(getApplicationContext());
        StringRequest stringRequest = new StringRequest(Request.Method.GET, url,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        // Xử lý response nếu cần
                        callback.onSuccess();
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        callback.onFailure(error.getMessage());
                    }
                });
        requestQueue.add(stringRequest);
    }

}