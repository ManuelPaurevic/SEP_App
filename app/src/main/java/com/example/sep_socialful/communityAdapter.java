package com.example.sep_socialful;

import android.content.Context;
import android.graphics.Color;
import android.nfc.cardemulation.CardEmulation;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class communityAdapter extends RecyclerView.Adapter<communityAdapter.ViewHolder> {

    Context context;
    ArrayList<community> list;
    private OnItemClickListener cListener;

    public communityAdapter(Context context, ArrayList<community> list) {
        this.context = context;
        this.list = list;
    }

    public interface OnItemClickListener {
        void onDeleteClick(int position);
    }

    public void setOnItemClickListener(OnItemClickListener listener) {
        cListener = listener;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(context).inflate(R.layout.recycler_view_row, parent, false);
        return new ViewHolder(v, cListener);
    }


    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        community communityItem = list.get(position);
        holder.name.setText(communityItem.getName());
        holder.cCard.setCardBackgroundColor(Color.parseColor(communityItem.getColor()));
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder{

        CardView cCard;
        TextView name;
        Button button;

        public ViewHolder(View itemView, final OnItemClickListener listener) {
            super(itemView);

            name = itemView.findViewById(R.id.communityName);
            cCard = itemView.findViewById(R.id.communityCard);
            button = itemView.findViewById(R.id.comJoinButton);

            button.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    if(listener != null){
                        int position = getAdapterPosition();
                        if(position != RecyclerView.NO_POSITION){
                            listener.onDeleteClick(position);
                        }
                    }
                }
            });

        }

    }
}
