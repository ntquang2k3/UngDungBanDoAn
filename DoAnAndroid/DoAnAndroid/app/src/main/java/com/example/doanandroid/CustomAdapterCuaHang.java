package com.example.doanandroid;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.example.doanandroid.POJO.CuaHang;

import java.util.ArrayList;

public class CustomAdapterCuaHang extends ArrayAdapter {
    Context context;
    int layoutItem;
    ArrayList<CuaHang> dsCH = new ArrayList<>();

    public CustomAdapterCuaHang(@NonNull Context context, int layoutItem, @NonNull ArrayList<CuaHang> dsCH) {
        super(context, layoutItem, dsCH);
        this.context = context;
        this.layoutItem = layoutItem;
        this.dsCH = dsCH;
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        CuaHang cuaHang = dsCH.get(position);
        if (convertView == null)
        {
            convertView = LayoutInflater.from(context).inflate(layoutItem,null);
        }
        // Hiển thị ảnh sản phẩm
        ImageView anhCuaHang = (ImageView) convertView.findViewById(R.id.anhDaiDienCuaHang);
        int resID = context.getResources().getIdentifier(cuaHang.getHinhAnhDaiDien(), "drawable", context.getPackageName());
        if (resID != 0) {
            Drawable drawable = context.getResources().getDrawable(resID);
            anhCuaHang.setImageDrawable(drawable);
        } else {
            // Xử lý khi không tìm thấy tài nguyên ảnh
        }

        TextView tenCuaHang = (TextView) convertView.findViewById(R.id.tenCuaHang);
        tenCuaHang.setText(cuaHang.getTenCuaHang());
        TextView soSaoDanhGia = (TextView) convertView.findViewById(R.id.soSaoDanhGia);
        soSaoDanhGia.setText(""+cuaHang.getSoSaoDanhGia());
        TextView soLuongDanhGia = (TextView) convertView.findViewById(R.id.soLuongDanhGia);
        soLuongDanhGia.setText("("+cuaHang.getSoLuongDanhGia()+") ... $$$$");
        TextView khoangCach = (TextView) convertView.findViewById(R.id.khoangCach);
        khoangCach.setText(""+cuaHang.getKhoangCachDiaLy()+"km");

        return convertView;
    }
    public ArrayList<CuaHang> getListCuaHang() {
        return dsCH;
    }
}

