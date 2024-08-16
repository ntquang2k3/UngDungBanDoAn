package com.example.doanandroid.POJO;

public class LoaiMon {
    int MaLoaiMon;
    String TenLoaiMon;
    String AnhLoaiMon;

    int resourceID;

    public int getResourceID() {
        return resourceID;
    }

    public void setResourceID(int resourceID) {
        this.resourceID = resourceID;
    }

    public LoaiMon(int maLoaiMon, String tenLoaiMon, String anhLoaiMon) {
        MaLoaiMon = maLoaiMon;
        TenLoaiMon = tenLoaiMon;
        AnhLoaiMon = anhLoaiMon;
    }
    public LoaiMon(int maLoaiMon, String tenLoaiMon, int resourceID) {
        MaLoaiMon = maLoaiMon;
        TenLoaiMon = tenLoaiMon;
        this.resourceID = resourceID;
    }

    public LoaiMon() {
    }

    public int getMaLoaiMon() {
        return MaLoaiMon;
    }

    public void setMaLoaiMon(int maLoaiMon) {
        MaLoaiMon = maLoaiMon;
    }

    public String getTenLoaiMon() {
        return TenLoaiMon;
    }

    public void setTenLoaiMon(String tenLoaiMon) {
        TenLoaiMon = tenLoaiMon;
    }

    public String getAnhLoaiMon() {
        return AnhLoaiMon;
    }

    public void setAnhLoaiMon(String anhLoaiMon) {
        AnhLoaiMon = anhLoaiMon;
    }
}
