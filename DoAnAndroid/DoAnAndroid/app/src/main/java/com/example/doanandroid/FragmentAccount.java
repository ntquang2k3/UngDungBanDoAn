package com.example.doanandroid;

import static android.content.Context.MODE_PRIVATE;

import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AlertDialog;
import androidx.fragment.app.Fragment;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.example.doanandroid.POJO.DonHangInnerJoin;
import com.example.doanandroid.POJO.Mon;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class FragmentAccount extends Fragment {
    private EditText edtTenKhachHang, edtSDT, edtEmail, edtDiaChi, edtDiemTichLuy;
    private Button btnCapNhat, btnDangXuat;
    public FragmentAccount() {
        // Required empty public constructor
    }
    public void khoiChay(String url) {
        new MyUrlTask().execute(url);
    }
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_account, container, false);

        edtTenKhachHang = view.findViewById(R.id.edtTenKhachHangAcc);
        edtSDT = view.findViewById(R.id.edtSoDienThoaiAcc);
        edtEmail = view.findViewById(R.id.edtEmailAcc);
        edtDiaChi = view.findViewById(R.id.edtDiaChiAcc);
        edtDiemTichLuy = view.findViewById(R.id.edtDiemTichLuyAcc);
        btnDangXuat = view.findViewById(R.id.btnDangXuatAcc);
        btnCapNhat = view.findViewById(R.id.btnCapNhatAcc);

        // Lấy dữ liệu từ SharedPreferences
        SharedPreferences sharedPreferences = getActivity().getSharedPreferences("MyPrefs", MODE_PRIVATE);
        int maKhachHang = sharedPreferences.getInt("maKhachHang", 0);
        String tenKhachHang = sharedPreferences.getString("tenKhachHang", "");
        String soDienThoai = sharedPreferences.getString("soDienThoai", "");
        String email = sharedPreferences.getString("email", "");
        String diaChi = sharedPreferences.getString("diaChiGiaoHang", "");
        int diemTichLuy = sharedPreferences.getInt("diemTichLuy", 0);

        // Hiển thị thông tin vừa lấy được lên EditText
        edtTenKhachHang.setText(tenKhachHang);
        edtSDT.setText(soDienThoai);
        edtEmail.setText(email);
        edtDiaChi.setText(diaChi);
        edtDiemTichLuy.setText(String.valueOf(diemTichLuy));

        // Button click listener for updating customer information
        btnCapNhat.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String tenKhachHang = edtTenKhachHang.getText().toString();
                String soDienThoai = edtSDT.getText().toString();
                String email = edtEmail.getText().toString();
                String diaChi = edtDiaChi.getText().toString();
                String sql = "UPDATE `khachhang` SET `TenKhachHang`=N'"+tenKhachHang+"',`SoDienThoai`='"+soDienThoai+"',`Email`='"+email+"',`DiaChiGiaoHang`='"+diaChi+"' WHERE MaKhachHang = "+maKhachHang;
                String thucHienSQL = getResources().getString(R.string.url)+ "thuchientruyvan.php?query="+sql;
                khoiChay(thucHienSQL);

                new AlertDialog.Builder(getContext())
                        .setTitle("Thông báo")
                        .setMessage("Cập nhật thành công")
                        .setPositiveButton("Đồng ý", new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int which) {
                                Intent intent = new Intent(getActivity(), LoginActivity.class);
                                startActivity(intent);
                                getActivity().finish();
                                //Bùi Phương Nam
                            }
                        })
                        .setIcon(android.R.drawable.ic_dialog_alert)
                        .show();

            }

        });

        btnDangXuat.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SharedPreferences.Editor editor = sharedPreferences.edit();
                editor.clear();
                editor.apply();

                Intent intent = new Intent(getActivity(), LoginActivity.class);
                startActivity(intent);
                getActivity().finish();
            }
        });

        return view;
    }

}
