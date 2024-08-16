package com.example.doanandroid;

import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.example.doanandroid.DAO.CuaHangFetcher;
import com.example.doanandroid.POJO.CuaHang;
import com.example.doanandroid.POJO.DonHangInnerJoin;
import com.example.doanandroid.POJO.LoaiMon;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;
import java.util.ListIterator;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link FragmentHome#newInstance} factory method to
 * create an instance of this fragment.
 */
public class FragmentHome extends Fragment {

    CustomAdapterCuaHang adapterCuaHang;
    ListView lvCuaHang;

    EditText edtTimKiem;
    Button btnTimKiem;
    ArrayList<CuaHang> listCuaHang = new ArrayList<>();
    RecyclerView recyclerView;
    List<LoaiMon> listLoaiMon = new ArrayList<>();

    MyAdapter myAdapter;
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public FragmentHome() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment FragmentHome.
     */
    // TODO: Rename and change types and number of parameters
    public static FragmentHome newInstance(String param1, String param2) {
        FragmentHome fragment = new FragmentHome();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_home, container, false);

        lvCuaHang = view.findViewById(R.id.lvCuaHang);
        edtTimKiem = view.findViewById(R.id.editTextTextPersonName);
        btnTimKiem = view.findViewById(R.id.btnTimKiemMonFHome);
        //Xử lí sự kiện onclick của button tìm kiếm
        btnTimKiem.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String chuoiTimKiem = edtTimKiem.getText().toString();
                String urlTimKiem = getResources().getString(R.string.url)+"DuLieuCuaHangInnerJoin.php?tenMon="+chuoiTimKiem;
                getAllDataCuaHangLoc(urlTimKiem);
            }
        });

        String url = getResources().getString(R.string.url)+"DuLieuCuaHang.php";
        getAllDataCuaHang(url);
        //Xử lí sự kiện khi click item listView
        lvCuaHang.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                // Lấy sản phẩm được chọn từ danh sách hiển thị
                adapterCuaHang = (CustomAdapterCuaHang) lvCuaHang.getAdapter();
                listCuaHang = adapterCuaHang.getListCuaHang();
                CuaHang selectedCuaHang = listCuaHang.get(position);
                // Tạo Intent để mở activity mới
                Intent intent = new Intent(getContext(), CuaHangActivity.class);

                // Truyền dữ liệu của sản phẩm sang activity mới
                intent.putExtra("MA_CUA_HANG", selectedCuaHang.getMaCuaHang());
                intent.putExtra("TEN_CUA_HANG", selectedCuaHang.getTenCuaHang());
                intent.putExtra("SO_SAO", selectedCuaHang.getSoSaoDanhGia());
                intent.putExtra("SO_LUONG", selectedCuaHang.getSoLuongDanhGia());
                intent.putExtra("KHOANG_CACH", selectedCuaHang.getKhoangCachDiaLy());
                intent.putExtra("ANH", selectedCuaHang.getHinhAnhDaiDien());
                // Bắt đầu activity mới
                startActivity(intent);
            }
        });


        String urlLoaiMon = getResources().getString(R.string.url)+"DuLieuLoaiMon.php";
        recyclerView = view.findViewById(R.id.rcv_loaiMon);
        getAllDataLoaiMon(urlLoaiMon);
        return view;
    }
    public void getAllDataCuaHang(String url) {
        RequestQueue requestQueue = Volley.newRequestQueue(getContext());
        StringRequest stringRequest = new StringRequest(Request.Method.GET, url,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        try {
                            parseJsonData(response);
                        } catch (JSONException e) {
                            throw new RuntimeException(e);
                        }
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(getContext(), "error", Toast.LENGTH_SHORT).show();
            }
        });
        requestQueue.add(stringRequest);

    }

    public void parseJsonData(String response) throws JSONException {

        JSONArray cuaHangArray = new JSONArray(response);
        for (int i = 0; i < cuaHangArray.length(); i++) {
            JSONObject cuaHang = cuaHangArray.getJSONObject(i);
            CuaHang a = new CuaHang();
            a.setMaCuaHang(Integer.parseInt(cuaHang.getString("MaCuaHang")));
            a.setTenCuaHang(cuaHang.getString("TenCuaHang"));
            a.setSoDienThoai(cuaHang.getString("SoDienThoai"));
            a.setDiaChiQuan(cuaHang.getString("DiaChiQuan"));
            a.setEmail(cuaHang.getString("Email"));
            a.setMatKhau(cuaHang.getString("MatKhau"));
            a.setHinhAnhDaiDien(cuaHang.getString("HinhAnhDaiDien"));
            a.setKhoangCachDiaLy(Double.parseDouble(cuaHang.getString("KhoangCachDiaLy")));
            a.setSoSaoDanhGia(Double.parseDouble(cuaHang.getString("SoSaoDanhGia")));
            a.setSoLuongDanhGia(Integer.parseInt(cuaHang.getString("SoLuongDanhGia")));
            a.setDangHoatDong(Integer.parseInt(cuaHang.getString("DangHoatDong")));
            if (a.getDangHoatDong() == 1) {
                listCuaHang.add(a);
            }
        }
        adapterCuaHang = new CustomAdapterCuaHang(getContext(), R.layout.custom_item_lv_cua_hang, listCuaHang);
        lvCuaHang.setAdapter(adapterCuaHang);
    }
    public void getAllDataCuaHangLoc(String url) {
        RequestQueue requestQueue = Volley.newRequestQueue(getContext());
        StringRequest stringRequest = new StringRequest(Request.Method.GET, url,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        try {
                            parseJsonDataLoc(response);
                        } catch (JSONException e) {
                            throw new RuntimeException(e);
                        }
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(getContext(), "error", Toast.LENGTH_SHORT).show();
            }
        });
        requestQueue.add(stringRequest);

    }

    public void parseJsonDataLoc(String response) throws JSONException {

        JSONArray cuaHangArray = new JSONArray(response);
        listCuaHang.clear();
        for (int i = 0; i < cuaHangArray.length(); i++) {
            JSONObject cuaHang = cuaHangArray.getJSONObject(i);
            CuaHang a = new CuaHang();
            a.setMaCuaHang(Integer.parseInt(cuaHang.getString("MaCuaHang")));
            a.setTenCuaHang(cuaHang.getString("TenCuaHang"));
            a.setSoDienThoai(cuaHang.getString("SoDienThoai"));
            a.setDiaChiQuan(cuaHang.getString("DiaChiQuan"));
            a.setEmail(cuaHang.getString("Email"));
            a.setMatKhau(cuaHang.getString("MatKhau"));
            a.setHinhAnhDaiDien(cuaHang.getString("HinhAnhDaiDien"));
            a.setKhoangCachDiaLy(Double.parseDouble(cuaHang.getString("KhoangCachDiaLy")));
            a.setSoSaoDanhGia(Double.parseDouble(cuaHang.getString("SoSaoDanhGia")));
            a.setSoLuongDanhGia(Integer.parseInt(cuaHang.getString("SoLuongDanhGia")));
            a.setDangHoatDong(Integer.parseInt(cuaHang.getString("DangHoatDong")));
            boolean maCuaHangDaTonTai = false;
            // Duyệt qua danh sách đơn hàng để kiểm tra
            for (CuaHang ch : listCuaHang) {
                if (ch.getMaCuaHang() == a.getMaCuaHang()) {
                    // Nếu mã cửa hàng đã tồn tại trong danh sách, đặt biến kiểm tra là true
                    maCuaHangDaTonTai = true;
                    break;
                }
            }

            // Nếu mã cửa hàng không tồn tại trong danh sách, thêm mới
            if (!maCuaHangDaTonTai && a.getDangHoatDong() == 1) {
                // Thêm mới đơn hàng vào danh sách
                listCuaHang.add(a);
            }
        }
        adapterCuaHang = new CustomAdapterCuaHang(getContext(), R.layout.custom_item_lv_cua_hang, listCuaHang);
        lvCuaHang.setAdapter(adapterCuaHang);
    }

    //Lấy dữ liệu loại món

    public void getAllDataLoaiMon(String url) {
        RequestQueue requestQueue = Volley.newRequestQueue(getContext());
        StringRequest stringRequest = new StringRequest(Request.Method.GET, url,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        try {
                            parseJsonDataLoaiMon(response);
                        } catch (JSONException e) {
                            throw new RuntimeException(e);
                        }
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(getContext(), "error", Toast.LENGTH_SHORT).show();
            }
        });
        requestQueue.add(stringRequest);

    }

    public void parseJsonDataLoaiMon(String response) throws JSONException {
        listLoaiMon.clear();
        JSONArray loaiMonArray = new JSONArray(response);
        for (int i = 0; i < loaiMonArray.length(); i++) {
            JSONObject cuaHang = loaiMonArray.getJSONObject(i);
            LoaiMon a = new LoaiMon();
            a.setMaLoaiMon(Integer.parseInt(cuaHang.getString("MaLoaiMon")));
            a.setTenLoaiMon(cuaHang.getString("TenLoaiMon"));
            a.setAnhLoaiMon(cuaHang.getString("AnhLoaiMon"));
            String tenAnhLoaiMon = cuaHang.getString("AnhLoaiMon");
            int resID = getContext().getResources().getIdentifier(tenAnhLoaiMon, "drawable", getContext().getPackageName());
            a.setResourceID(resID);
            listLoaiMon.add(a);
        }
        MyAdapter myAdapter = new MyAdapter(listLoaiMon, getContext());
        recyclerView.setAdapter(myAdapter);
        LinearLayoutManager layoutManager = new LinearLayoutManager(getContext(), LinearLayoutManager.HORIZONTAL, false);
        recyclerView.setLayoutManager(layoutManager);
    }
}