package com.example.doanandroid.POJO;

public class KhachHang {
    private int maKhachHang;
    private String tenKhachHang;
    private String soDienThoai;
    private String email;
    private String matKhau;
    private String diaChiGiaoHang;
    private int diemTichLuy;

    // Constructors
    public KhachHang() {
    }

    public KhachHang(int maKhachHang, String tenKhachHang, String soDienThoai, String email, String matKhau, String diaChiGiaoHang, int diemTichLuy) {
        this.maKhachHang = maKhachHang;
        this.tenKhachHang = tenKhachHang;
        this.soDienThoai = soDienThoai;
        this.email = email;
        this.matKhau = matKhau;
        this.diaChiGiaoHang = diaChiGiaoHang;
        this.diemTichLuy = diemTichLuy;
    }

    // Getters and setters
    public int getMaKhachHang() {
        return maKhachHang;
    }

    public void setMaKhachHang(int maKhachHang) {
        this.maKhachHang = maKhachHang;
    }

    public String getTenKhachHang() {
        return tenKhachHang;
    }

    public void setTenKhachHang(String tenKhachHang) {
        this.tenKhachHang = tenKhachHang;
    }

    public String getSoDienThoai() {
        return soDienThoai;
    }

    public void setSoDienThoai(String soDienThoai) {
        this.soDienThoai = soDienThoai;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getMatKhau() {
        return matKhau;
    }

    public void setMatKhau(String matKhau) {
        this.matKhau = matKhau;
    }

    public String getDiaChiGiaoHang() {
        return diaChiGiaoHang;
    }

    public void setDiaChiGiaoHang(String diaChiGiaoHang) {
        this.diaChiGiaoHang = diaChiGiaoHang;
    }

    public int getDiemTichLuy() {
        return diemTichLuy;
    }

    public void setDiemTichLuy(int diemTichLuy) {
        this.diemTichLuy = diemTichLuy;
    }
}
