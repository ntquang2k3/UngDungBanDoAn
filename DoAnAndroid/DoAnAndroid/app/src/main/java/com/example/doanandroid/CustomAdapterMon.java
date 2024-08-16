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
import com.example.doanandroid.POJO.Mon;

import java.text.NumberFormat;
import java.util.ArrayList;
import java.util.Locale;

public class CustomAdapterMon extends ArrayAdapter {
    Context context;
    int layoutItem;
    ArrayList<Mon> dsMon = new ArrayList<>();

    public CustomAdapterMon(@NonNull Context context, int layoutItem, @NonNull ArrayList<Mon> dsmon) {
        super(context, layoutItem, dsmon);
        this.context = context;
        this.layoutItem = layoutItem;
        this.dsMon = dsmon;
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        Mon mon = dsMon.get(position);
        if (convertView == null)
        {
            convertView = LayoutInflater.from(context).inflate(layoutItem,null);
        }
        // Hiển thị ảnh sản phẩm
        ImageView anhMon = (ImageView) convertView.findViewById(R.id.anhMonCHA);
        int resID = context.getResources().getIdentifier(mon.getAnh(), "drawable", context.getPackageName());
        if (resID != 0) {
            Drawable drawable = context.getResources().getDrawable(resID);
            anhMon.setImageDrawable(drawable);
        } else {
            // Xử lý khi không tìm thấy tài nguyên ảnh
        }

        TextView tenMon = (TextView) convertView.findViewById(R.id.tenMonCHA);
        tenMon.setText(mon.getTenMon());
        TextView gia = (TextView) convertView.findViewById(R.id.giaMonCHA);
        // Sử dụng NumberFormat để định dạng số
        NumberFormat numberFormat = NumberFormat.getInstance(new Locale("vi", "VN"));
        String formattedNumber = numberFormat.format(mon.getGiaBanRa());
        gia.setText(""+formattedNumber+" VNĐ");

        return convertView;
    }
    public ArrayList<Mon> getListMon() {
        return dsMon;
    }
}

