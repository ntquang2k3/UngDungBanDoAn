package com.example.doanandroid.POJO;

public class Mon {
    int maMon;
    int maLoaiMon;
    int maCuaHang;
    String tenMon;
    String moTaMon;
    int giaMon;
    int giaBanRa;
    String anh;
    int hienThi;

    public Mon(String tenMon, int giaBanRa, String anh, int hienThi) {
        this.tenMon = tenMon;
        this.giaBanRa = giaBanRa;
        this.anh = anh;
        this.hienThi = hienThi;
    }

    public int getMaMon() {
        return maMon;
    }

    public void setMaMon(int maMon) {
        this.maMon = maMon;
    }

    public int getMaLoaiMon() {
        return maLoaiMon;
    }

    public void setMaLoaiMon(int maLoaiMon) {
        this.maLoaiMon = maLoaiMon;
    }

    public int getMaCuaHang() {
        return maCuaHang;
    }

    public void setMaCuaHang(int maCuaHang) {
        this.maCuaHang = maCuaHang;
    }

    public String getTenMon() {
        return tenMon;
    }

    public void setTenMon(String tenMon) {
        this.tenMon = tenMon;
    }

    public String getMoTaMon() {
        return moTaMon;
    }

    public void setMoTaMon(String moTaMon) {
        this.moTaMon = moTaMon;
    }

    public int getGiaMon() {
        return giaMon;
    }

    public void setGiaMon(int giaMon) {
        this.giaMon = giaMon;
    }

    public int getGiaBanRa() {
        return giaBanRa;
    }

    public void setGiaBanRa(int giaBanRa) {
        this.giaBanRa = giaBanRa;
    }

    public String getAnh() {
        return anh;
    }

    public void setAnh(String anh) {
        this.anh = anh;
    }

    public int getHienThi() {
        return hienThi;
    }

    public void setHienThi(int hienThi) {
        this.hienThi = hienThi;
    }

    public Mon() {
    }
}
