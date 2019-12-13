package com.example.omniver;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

class RecyclerAdapter extends RecyclerView.Adapter<RecyclerAdapter.RecyclerViewHolder> {
    private String[] clothes;
    private String[] temps1, temps2;

    public static class RecyclerViewHolder extends RecyclerView.ViewHolder {
        public TextView clothesText, temp1Text, temp2Text;
        public RecyclerViewHolder(View v) {
            super(v);
            clothesText = v.findViewById(R.id.clothes_text);
            temp1Text = v.findViewById(R.id.temp1_text);
            temp2Text = v.findViewById(R.id.temp2_text);
        }
    }
    public RecyclerAdapter(String[] c, String[] t1, String[] t2) {
        clothes = c;
        temps1 = t1;
        temps2 = t2;
    }

    @Override
    public RecyclerAdapter.RecyclerViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_recycler, parent, false);
        RecyclerViewHolder vh = new RecyclerViewHolder(v);
        return vh;
    }
    @Override
    public void onBindViewHolder(RecyclerViewHolder holder, int position) {
        holder.clothesText.setText(clothes[position]);
        holder.temp1Text.setText(temps1[position] + "℃");
        holder.temp2Text.setText("~ " + temps2[position] + "℃");
        if (position == 0) holder.temp2Text.setText("");
        if (position == getItemCount()) holder.temp1Text.setText("");
    }

    @Override
    public int getItemCount() {
        return clothes.length;
    }

}
