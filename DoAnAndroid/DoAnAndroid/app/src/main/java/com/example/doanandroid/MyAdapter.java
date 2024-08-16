package com.example.doanandroid;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.example.doanandroid.POJO.LoaiMon;

import java.util.List;

public class MyAdapter extends RecyclerView.Adapter<MyAdapter.MyViewHolder> {
    private List<LoaiMon> listLoaiMon;
    private Context context;

    public MyAdapter(List<LoaiMon> listLoaiMon, Context context) {
        this.listLoaiMon = listLoaiMon;
        this.context = context;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_loai_mon,parent,false);
        return new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {
        LoaiMon loaiMon = listLoaiMon.get(position);
        holder.imageView.setImageResource(loaiMon.getResourceID());
        holder.textView.setText(loaiMon.getTenLoaiMon());
    }

    @Override
    public int getItemCount() {
        return listLoaiMon.size();
    }

    class MyViewHolder extends RecyclerView.ViewHolder{

        private CardView cardView;
        private ImageView imageView;
        private TextView textView;
        //Ánh xạ id
        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            cardView = itemView.findViewById(R.id.cardview);
            imageView = itemView.findViewById(R.id.anhLoaiMon);
            textView = itemView.findViewById(R.id.tvLoaiMon);
        }
    }
}