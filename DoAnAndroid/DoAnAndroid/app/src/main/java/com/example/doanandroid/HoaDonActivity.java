package com.example.doanandroid;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.example.doanandroid.POJO.DonHangInnerJoin;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Locale;

public class HoaDonActivity extends AppCompatActivity {

    TextView tvDiaChiGiao,tvKhoangCachGiao,tvTenQuan,tvTongTien,tvTongCong;
    int maCuaHang;
    ListView lvChiTietHoaDon;
    ArrayList<DonHangInnerJoin> listDonHangInnerJoin = new ArrayList<>();
    CustomAdapterMonHoaDon customAdapterMonHoaDon;
    Button btnDatDon;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_hoa_don);
        mappingIDs();
        // Nhận dữ liệu từ Intent
        Intent intent = getIntent();
        maCuaHang = intent.getIntExtra("MA_CUA_HANG",0);
        // Lấy dữ liệu từ SharedPreferences
        SharedPreferences sharedPreferences = getApplicationContext().getSharedPreferences("MyPrefs", MODE_PRIVATE);
        int maKhachHang = sharedPreferences.getInt("maKhachHang", 0);
        String diaChiGiao = sharedPreferences.getString("diaChiGiaoHang", "");
        tvDiaChiGiao.setText(diaChiGiao);
        String urlDuLieu = getResources().getString(R.string.url)+"DuLieuDonHangVuaTao.php?MaKhachHang="+maKhachHang;
        getAllDataDonHangInnerJoin(urlDuLieu);
        addEvent();
    }

    private void addEvent() {
        btnDatDon.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                int tongTien = Integer.parseInt(tvTongTien.getText().toString());
                int maDonHang = listDonHangInnerJoin.get(0).getMaDonHang();
                String hinhAnhDaiDien = listDonHangInnerJoin.get(0).getHinhAnhDaiDien();
                // Lấy thời gian hiện tại
                Calendar calendar = Calendar.getInstance();
                SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd", Locale.getDefault());
                String currentDate = sdf.format(calendar.getTime());
                String sql = "UPDATE `donhang` SET `MaDonHang`='"+maDonHang+"',`TongTien`='"+tongTien+"',`TrangThai`=N'Chờ xác nhận',`ThoiGianDatHang`='"+currentDate+"',`TrongGioHang`='0',`HinhAnhDaiDien`='"+hinhAnhDaiDien+"' WHERE MaDonHang = '"+maDonHang+"'";
                String thucHienSQL = getResources().getString(R.string.url)+"thuchientruyvan.php?query="+sql;
                khoiChay(thucHienSQL);
                finish();
            }
        });
    }
    public void khoiChay(String url) {
        new MyUrlTask().execute(url);
    }

    private void mappingIDs() {
        tvDiaChiGiao = findViewById(R.id.tvDiaChiGiaoHDAC);
        tvKhoangCachGiao = findViewById(R.id.tvKhoangCachHDAC);
        tvTenQuan = findViewById(R.id.tvHDAC);
        tvTongTien = findViewById(R.id.tvTongTienHDAC);
        tvTongCong = findViewById(R.id.tvTongCongHDAC);
        btnDatDon = findViewById(R.id.btnDatDonHDAC);
        lvChiTietHoaDon = findViewById(R.id.lvChiTietHDAC);
    }
    public void getAllDataDonHangInnerJoin(String url) {
        RequestQueue requestQueue = Volley.newRequestQueue(getApplicationContext());
        StringRequest stringRequest = new StringRequest(Request.Method.GET, url,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        try {
                            parseJsonDataDHIJ(response);
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

    public void parseJsonDataDHIJ(String response) throws JSONException {

        JSONArray cuaHangArray = new JSONArray(response);
        for (int i = 0; i < cuaHangArray.length(); i++) {
            JSONObject cuaHang = cuaHangArray.getJSONObject(i);
            DonHangInnerJoin a = new DonHangInnerJoin();
            a.setMaDonHang(Integer.parseInt(cuaHang.getString("MaDonHang")));
            a.setMaKhachHang(Integer.parseInt(cuaHang.getString("MaKhachHang")));
            a.setMaCuaHang(Integer.parseInt(cuaHang.getString("MaCuaHang")));
            a.setMaMon(Integer.parseInt(cuaHang.getString("MaMon")));
            a.setSoLuong(Integer.parseInt(cuaHang.getString("SoLuong")));
            a.setThanhTien(Integer.parseInt(cuaHang.getString("ThanhTien")));
            a.setTrongGioHang(Integer.parseInt(cuaHang.getString("TrongGioHang")));
            a.setTenMon(cuaHang.getString("TenMon"));
            a.setGiaMon(Integer.parseInt(cuaHang.getString("GiaMon")));
            a.setGiaBan(Integer.parseInt(cuaHang.getString("GiaBanRa")));
            a.setAnhMon(cuaHang.getString("AnhMon"));
            a.setTenCuaHang(cuaHang.getString("TenCuaHang"));
            a.setHinhAnhDaiDien(cuaHang.getString("HinhAnhDaiDien"));
            a.setKhoangCachDiaLy(Double.parseDouble(cuaHang.getString("KhoangCachDiaLy")));
            a.setDangHoatDong(Integer.parseInt(cuaHang.getString("DangHoatDong")));
            if (a.getMaCuaHang() == maCuaHang && a.getTrongGioHang() == 1)
            {
                listDonHangInnerJoin.add(a);
            }
        }
        // Duyệt qua danh sách đơn hàng để kiểm tra
        int tongTien = 0;
        for (DonHangInnerJoin donHang : listDonHangInnerJoin) {
            tongTien = tongTien + donHang.getThanhTien();
            tvTenQuan.setText(donHang.getTenCuaHang());
            tvKhoangCachGiao.setText("Khoảng cách tới chỗ bạn : "+donHang.getKhoangCachDiaLy()+" km");
        }
        tvTongTien.setText(String.valueOf(tongTien));
        tvTongCong.setText(String.valueOf(tongTien));
        customAdapterMonHoaDon = new CustomAdapterMonHoaDon(getApplicationContext(), R.layout.item_mon_hoa_don, listDonHangInnerJoin);
        lvChiTietHoaDon.setAdapter(customAdapterMonHoaDon);
    }
}