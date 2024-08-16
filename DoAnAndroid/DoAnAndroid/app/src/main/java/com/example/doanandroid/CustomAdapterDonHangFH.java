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
import com.example.doanandroid.POJO.DonHangInnerJoin;

import java.util.ArrayList;

public class CustomAdapterDonHangFH extends ArrayAdapter {
    Context context;
    int layoutItem;
    ArrayList<DonHangInnerJoin> dsDonHangIJ = new ArrayList<>();

    public CustomAdapterDonHangFH(@NonNull Context context, int layoutItem, @NonNull ArrayList<DonHangInnerJoin> dsdh) {
        super(context, layoutItem, dsdh);
        this.context = context;
        this.layoutItem = layoutItem;
        this.dsDonHangIJ = dsdh;
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        DonHangInnerJoin donHangInnerJoin = dsDonHangIJ.get(position);
        if (convertView == null)
        {
            convertView = LayoutInflater.from(context).inflate(layoutItem,null);
        }
        // Hiển thị ảnh sản phẩm
        ImageView anhCuaHang = (ImageView) convertView.findViewById(R.id.imgAnhQuanDHLS);
        int resID = context.getResources().getIdentifier(donHangInnerJoin.getHinhAnhDaiDien(), "drawable", context.getPackageName());
        if (resID != 0) {
            Drawable drawable = context.getResources().getDrawable(resID);
            anhCuaHang.setImageDrawable(drawable);
        } else {
            // Xử lý khi không tìm thấy tài nguyên ảnh
        }
        TextView trangThai = (TextView) convertView.findViewById(R.id.tvTrangThaiGiaoDHLS);
        trangThai.setText(donHangInnerJoin.getTrangThai());
        TextView tenQuan = (TextView) convertView.findViewById(R.id.tvTenQuanDHLS);
        tenQuan.setText(donHangInnerJoin.getTenCuaHang());
        TextView khoangCach = (TextView) convertView.findViewById(R.id.tvKhoangCachDHLS);
        khoangCach.setText(""+donHangInnerJoin.getKhoangCachDiaLy()+"km");

        return convertView;
    }
    public ArrayList<DonHangInnerJoin> getListCuaHang() {
        return dsDonHangIJ;
    }
}
