package com.example.omniver;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.example.omniver.model.Picture;

import java.util.ArrayList;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

public class ClothesCardRecyclerAdapter extends RecyclerView.Adapter<ClothesCardRecyclerAdapter.ViewHolder> {
    private ArrayList<Picture> clothes;

    public static class ViewHolder extends RecyclerView.ViewHolder {
        public ImageView image;
        public TextView dateText;
        public TextView temperatureText;
        public TextView ratingText;

        public ViewHolder(View view) {
            super(view);

            image = view.findViewById(R.id.card_image);
            dateText = view.findViewById(R.id.card_date);
            temperatureText = view.findViewById(R.id.card_temperature);
            ratingText = view.findViewById(R.id.card_rating);
        }
    }

    public ClothesCardRecyclerAdapter(ArrayList<Picture> clothes) {
        this.clothes = clothes;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_clothes_card, parent, false);

        ViewHolder vh = new ViewHolder(v);
        return vh;
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        Glide.with(holder.image).load(clothes.get(position).getImageUrl()).into(holder.image);
        holder.dateText.setText(String.format("# %d.%d.%d", clothes.get(position).getYear(), clothes.get(position).getMonth(), clothes.get(position).getDay()));
        holder.temperatureText.setText(String.format("# %.1f", clothes.get(position).getTemperature()) + " ℃");
        holder.ratingText.setText(String.format("# %.1f", clothes.get(position).getGrade()) + "점");
    }

    @Override
    public int getItemCount() {
        return clothes.size();
    }
}
