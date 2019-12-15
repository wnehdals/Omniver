package com.example.omniver;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;

import androidx.recyclerview.widget.RecyclerView;

class RecommendedListRecyclerAdapter extends RecyclerView.Adapter<RecommendedListRecyclerAdapter.RecyclerViewHolder> {
    private int[][] icons;
    private String[] texts;
    private String[] temps1, temps2;

    public static class RecyclerViewHolder extends RecyclerView.ViewHolder {
        public ImageView icon1, icon2;
        public TextView clothesText, temp1Text, temp2Text;

        public RecyclerViewHolder(View v) {
            super(v);
            icon1 = v.findViewById(R.id.icon1);
            icon2 = v.findViewById(R.id.icon2);
            clothesText = v.findViewById(R.id.clothes_text);
            temp1Text = v.findViewById(R.id.temp1_text);
            temp2Text = v.findViewById(R.id.temp2_text);
        }
    }

    public RecommendedListRecyclerAdapter(int[][] i, String[] t, String[] t1, String[] t2) {
        icons = i;
        texts = t;
        temps1 = t1;
        temps2 = t2;
    }

    @Override
    public RecommendedListRecyclerAdapter.RecyclerViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_recommended_list, parent, false);
        RecyclerViewHolder vh = new RecyclerViewHolder(v);
        return vh;
    }
    @Override
    public void onBindViewHolder(RecyclerViewHolder holder, int position) {
        Glide.with(holder.icon1).load(icons[position][0]).into(holder.icon1);
        Glide.with(holder.icon2).load(icons[position][1]).into(holder.icon2);

        holder.clothesText.setText(texts[position]);
        holder.temp1Text.setText(temps1[position] + "℃");
        holder.temp2Text.setText("~ " + temps2[position] + "℃");
        if (position == 0) {
            holder.temp2Text.setText("");
            holder.temp1Text.setText(temps1[position] + "℃ ~");
        }
        if (position == getItemCount()) {
            holder.temp1Text.setText("");
        }
    }

    @Override
    public int getItemCount() {
        return texts.length;
    }

}
