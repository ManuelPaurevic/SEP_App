package com.example.sep_socialful;

import android.content.Context;
import android.graphics.Color;
import android.nfc.cardemulation.CardEmulation;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class communityAdapter extends RecyclerView.Adapter<communityAdapter.ViewHolder> {


    Context context;
    ArrayList<community> list;

    public communityAdapter(Context context, ArrayList<community> list) {
        this.context = context;
        this.list = list;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return null;
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {

    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder{

        CardView card;
        TextView name;
        String color;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            name = itemView.findViewById(R.id.communityName);
            card = itemView.findViewById(R.id.communityCard);
            //card.setCardBackgroundColor();
            //cardView.setBackgroundColor(ContextCompat.getColor(this, R.color.colors));


        }
    }



}
