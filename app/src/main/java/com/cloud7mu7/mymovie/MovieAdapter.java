package com.cloud7mu7.mymovie;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

public class MovieAdapter extends RecyclerView.Adapter<MovieAdapter.ViewHolder>{

    private List<DailyBoxOfficeList> items;

    public MovieAdapter(List<DailyBoxOfficeList> items){
        this.items = items;
    }

    @NonNull
    @Override
    public MovieAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.boxoffice_item , parent, false);
        return new ViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull MovieAdapter.ViewHolder holder, int position) {
        DailyBoxOfficeList item = items.get(position);
        holder.setItem(item);
    }

    @Override
    public int getItemCount() {
        return items.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        private TextView rank, movieNm, openDt;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            rank = itemView.findViewById(R.id.movie_rank);
            movieNm = itemView.findViewById(R.id.movie_name);
            openDt = itemView.findViewById(R.id.movie_day);
        }

        public void setItem(DailyBoxOfficeList item){
            rank.setText(item.getRank());
            movieNm.setText(item.getMovieNm());
            openDt.setText(item.getOpenDt());
        }

    }
}
