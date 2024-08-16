package com.example.doanandroid;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;

import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.example.doanandroid.POJO.CuaHang;
import com.example.doanandroid.POJO.LoaiMon;
import com.example.doanandroid.POJO.Mon;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

public class CuaHangActivity extends AppCompatActivity {

    ListView lvMon;
    TextView tvSoSao,tvSoLuong,tvKhoangCach,tvTenQuan;
    ImageView anhCuaQuan;

    int maCuaHang;
    ArrayList<Mon> listMon = new ArrayList<>();
    CustomAdapterMon customAdapterMon;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cua_hang);
        // Nhận dữ liệu từ Intent
        Intent intent = getIntent();
        maCuaHang = intent.getIntExtra("MA_CUA_HANG",0);
        String tenCuaHang = intent.getStringExtra("TEN_CUA_HANG");
        double soSao = intent.getDoubleExtra("SO_SAO", 0);
        double khoangCach = intent.getDoubleExtra("KHOANG_CACH", 0);
        int soLuong = intent.getIntExtra("SO_LUONG",0);
        String anh = intent.getStringExtra("ANH");
        // Hiển thị thông tin quán trong activity
        mappingIDs();
        tvTenQuan.setText(tenCuaHang);
        tvSoLuong.setText("("+String.valueOf(soLuong)+")");
        tvSoSao.setText(String.valueOf(soSao));
        tvKhoangCach.setText(String.valueOf(khoangCach));
        int resID = getResources().getIdentifier(anh, "drawable", getPackageName());
        if (resID != 0) {
            Drawable drawable = getResources().getDrawable(resID);
            anhCuaQuan.setImageDrawable(drawable);
        }
        String urlMon = getResources().getString(R.string.url)+"DuLieuMon.php";
        getAllDataMon(urlMon);
        //Xử lí sự kiện khi click item listView
        lvMon.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                // Lấy sản phẩm được chọn từ danh sách hiển thị
                customAdapterMon = (CustomAdapterMon) lvMon.getAdapter();
                listMon = customAdapterMon.getListMon();
                Mon selectedMon = listMon.get(position);
                // Tạo Intent để mở activity mới
                Intent intent = new Intent(getApplicationContext(), MonActivity.class);

                // Truyền dữ liệu của sản phẩm sang activity mới
                intent.putExtra("MA_CUA_HANG", selectedMon.getMaCuaHang());
                intent.putExtra("MA_MON", selectedMon.getMaMon());
                intent.putExtra("TEN_MON", selectedMon.getTenMon());
                intent.putExtra("GIA_MON", selectedMon.getGiaBanRa());
                intent.putExtra("MO_TA", selectedMon.getMoTaMon());
                intent.putExtra("ANH", selectedMon.getAnh());
                // Bắt đầu activity mới
                startActivity(intent);
            }
        });
    }
    void mappingIDs()
    {
        tvSoSao = findViewById(R.id.tvSoSaoDanhGiaCHA);
        tvSoLuong = findViewById(R.id.tvSoLuongDanhGiaCHA);
        tvKhoangCach = findViewById(R.id.tvKhoangCachCHA);
        tvTenQuan = findViewById(R.id.tvTenQuanCHA);
        anhCuaQuan = findViewById(R.id.imgAnhDaiDienCHA);
        lvMon = findViewById(R.id.lvMonCHA);
    }
    public void getAllDataMon(String url) {
        RequestQueue requestQueue = Volley.newRequestQueue(getApplicationContext());
        StringRequest stringRequest = new StringRequest(Request.Method.GET, url,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        try {
                            parseJsonDataMon(response);
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

    public void parseJsonDataMon(String response) throws JSONException {
        listMon.clear();
        JSONArray loaiMonArray = new JSONArray(response);
        for (int i = 0; i < loaiMonArray.length(); i++) {
            JSONObject cuaHang = loaiMonArray.getJSONObject(i);
            Mon a = new Mon();
            a.setMaMon(Integer.parseInt(cuaHang.getString("MaMon")));
            a.setMaLoaiMon(Integer.parseInt(cuaHang.getString("MaLoaiMon")));
            a.setMaCuaHang(Integer.parseInt(cuaHang.getString("MaCuaHang")));
            a.setTenMon(cuaHang.getString("TenMon"));
            a.setMoTaMon(cuaHang.getString("MoTaMon"));
            a.setGiaMon(Integer.parseInt(cuaHang.getString("GiaMon")));
            a.setGiaBanRa(Integer.parseInt(cuaHang.getString("GiaBanRa")));
            a.setAnh(cuaHang.getString("Anh"));
            a.setHienThi(cuaHang.getInt("HienThi"));
            if (a.getHienThi() == 1 && a.getMaCuaHang() == maCuaHang)
            {
                listMon.add(a);
            }
        }
        customAdapterMon = new CustomAdapterMon(getApplicationContext(), R.layout.item_mon, listMon);
        lvMon.setAdapter(customAdapterMon);
    }
}