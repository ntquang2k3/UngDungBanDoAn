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
import com.example.doanandroid.POJO.DonHangInnerJoin;
import com.example.doanandroid.POJO.Mon;

import java.text.NumberFormat;
import java.util.ArrayList;
import java.util.Locale;

public class CustomAdapterMonHoaDon extends ArrayAdapter {
    Context context;
    int layoutItem;
    ArrayList<DonHangInnerJoin> dsMon = new ArrayList<>();

    public CustomAdapterMonHoaDon(@NonNull Context context, int layoutItem, @NonNull ArrayList<DonHangInnerJoin> dsmon) {
        super(context, layoutItem, dsmon);
        this.context = context;
        this.layoutItem = layoutItem;
        this.dsMon = dsmon;
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        DonHangInnerJoin mon = dsMon.get(position);
        if (convertView == null)
        {
            convertView = LayoutInflater.from(context).inflate(layoutItem,null);
        }
        // Hiển thị ảnh sản phẩm
        ImageView anhMon = (ImageView) convertView.findViewById(R.id.anhMonHDAC);
        int resID = context.getResources().getIdentifier(mon.getAnhMon(), "drawable", context.getPackageName());
        if (resID != 0) {
            Drawable drawable = context.getResources().getDrawable(resID);
            anhMon.setImageDrawable(drawable);
        } else {
            // Xử lý khi không tìm thấy tài nguyên ảnh
        }

        TextView tenMon = (TextView) convertView.findViewById(R.id.tvTenMonHDAC);
        tenMon.setText(mon.getTenMon());
        TextView gia = (TextView) convertView.findViewById(R.id.tvGiaHDAC);
        // Sử dụng NumberFormat để định dạng số
        NumberFormat numberFormat = NumberFormat.getInstance(new Locale("vi", "VN"));
        String formattedNumber = numberFormat.format(mon.getGiaBan());
        gia.setText(""+formattedNumber+" VNĐ");
        TextView soLuong = (TextView) convertView.findViewById(R.id.tvSoLuongHDAC);
        soLuong.setText(String.valueOf(mon.getSoLuong()));

        return convertView;
    }
    public ArrayList<DonHangInnerJoin> getListMon() {
        return dsMon;
    }
}

