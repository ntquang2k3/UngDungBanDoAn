package com.example.doanandroid.POJO;

import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.example.doanandroid.CustomAdapterCuaHang;
import com.example.doanandroid.R;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class CuaHang {
    private int MaCuaHang;
    private String TenCuaHang;
    private String SoDienThoai;
    private String DiaChiQuan;
    private String Email;
    private String MatKhau;
    private String HinhAnhDaiDien;
    private double KhoangCachDiaLy;
    private double SoSaoDanhGia;
    private int SoLuongDanhGia;
    private int DangHoatDong;

    public CuaHang() {

    }

    public CuaHang(int maCuaHang, String tenCuaHang, String soDienThoai, String diaChiQuan, String email, String matKhau, String hinhAnhDaiDien, double khoangCachDiaLy, double soSaoDanhGia, int soLuongDanhGia, int dangHoatDong) {
        MaCuaHang = maCuaHang;
        TenCuaHang = tenCuaHang;
        SoDienThoai = soDienThoai;
        DiaChiQuan = diaChiQuan;
        Email = email;
        MatKhau = matKhau;
        HinhAnhDaiDien = hinhAnhDaiDien;
        KhoangCachDiaLy = khoangCachDiaLy;
        SoSaoDanhGia = soSaoDanhGia;
        SoLuongDanhGia = soLuongDanhGia;
        DangHoatDong = dangHoatDong;
    }
    // Getters and setters

    public int getMaCuaHang() {
        return MaCuaHang;
    }

    public void setMaCuaHang(int maCuaHang) {
        MaCuaHang = maCuaHang;
    }

    public String getTenCuaHang() {
        return TenCuaHang;
    }

    public void setTenCuaHang(String tenCuaHang) {
        TenCuaHang = tenCuaHang;
    }

    public String getSoDienThoai() {
        return SoDienThoai;
    }

    public void setSoDienThoai(String soDienThoai) {
        SoDienThoai = soDienThoai;
    }

    public String getDiaChiQuan() {
        return DiaChiQuan;
    }

    public void setDiaChiQuan(String diaChiQuan) {
        DiaChiQuan = diaChiQuan;
    }

    public String getEmail() {
        return Email;
    }

    public void setEmail(String email) {
        Email = email;
    }

    public String getMatKhau() {
        return MatKhau;
    }

    public void setMatKhau(String matKhau) {
        MatKhau = matKhau;
    }

    public String getHinhAnhDaiDien() {
        return HinhAnhDaiDien;
    }

    public void setHinhAnhDaiDien(String hinhAnhDaiDien) {
        HinhAnhDaiDien = hinhAnhDaiDien;
    }

    public double getKhoangCachDiaLy() {
        return KhoangCachDiaLy;
    }

    public void setKhoangCachDiaLy(double khoangCachDiaLy) {
        KhoangCachDiaLy = khoangCachDiaLy;
    }

    public double getSoSaoDanhGia() {
        return SoSaoDanhGia;
    }

    public void setSoSaoDanhGia(double soSaoDanhGia) {
        SoSaoDanhGia = soSaoDanhGia;
    }

    public int getSoLuongDanhGia() {
        return SoLuongDanhGia;
    }

    public void setSoLuongDanhGia(int soLuongDanhGia) {
        SoLuongDanhGia = soLuongDanhGia;
    }

    public int getDangHoatDong() {
        return DangHoatDong;
    }

    public void setDangHoatDong(int dangHoatDong) {
        DangHoatDong = dangHoatDong;
    }
}
