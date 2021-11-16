package com.cloud7mu7.mymovie;

import android.content.ClipData;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class DiaryAdapter extends RecyclerView.Adapter {

    Context context;
    ArrayList<Item> items;

    public DiaryAdapter(Context context, ArrayList<Item> items){
        this.context = context;
        this.items = items;
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(context);
        View itemview = inflater.inflate(R.layout.diary_item, parent, false);
        VH vh = new VH(itemview);
        return vh;
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
        VH vh = (VH) holder;

        Item item = items.get(position);

//        vh.tvNo.setText(item.no);
        vh.tvTitle.setText(item.day);
        vh.tvday.setText(item.title);
        vh.tvMoviename.setText(item.moviename);
        vh.tvcoment.setText(item.coment);

    }

    @Override
    public int getItemCount() {
        return items.size();
    }

    class VH extends RecyclerView.ViewHolder{

//        TextView tvNo;
        TextView tvTitle;
        TextView tvMoviename;
        TextView tvday;
        TextView tvcoment;

        public VH(@NonNull View itemView) {
            super(itemView);

//            tvNo = itemView.findViewById(R.id.diary_no);
            tvTitle = itemView.findViewById(R.id.diary_title);
            tvMoviename = itemView.findViewById(R.id.diary_moviename);
            tvday = itemView.findViewById(R.id.diary_day);
            tvcoment = itemView.findViewById(R.id.diary_coment);

        }
    }
}
