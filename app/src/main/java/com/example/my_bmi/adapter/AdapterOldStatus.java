package com.example.my_bmi.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.my_bmi.R;
import com.example.my_bmi.model.OldStatues;

import java.util.ArrayList;

public class AdapterOldStatus extends RecyclerView.Adapter<AdapterOldStatus.Holder> {
    ArrayList<OldStatues> adapterIteamChaletLists;
    OnItemClickListener onItemClickListener;
    Context context;

    ViewGroup gg;


    public interface OnItemClickListener {
        void onItemClick(int position);
    }

    public void setOnItemClickListener(OnItemClickListener onItemClickListener){
        this.onItemClickListener = onItemClickListener;
    }

    public AdapterOldStatus(ArrayList<OldStatues> adapterIteamChaletLists, Context context) {
        this.adapterIteamChaletLists = adapterIteamChaletLists;
        this.context = context;

    }

    @NonNull
    @Override
    public Holder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.list_item, null, false);
        Holder holder = new Holder(v);
         gg = parent;

        return holder;
    }

    @Override
    public void onBindViewHolder(@NonNull Holder holder, int position) {
        OldStatues A_I_C=adapterIteamChaletLists.get(position);


    }

    @Override
    public int getItemCount() {
        return adapterIteamChaletLists.size();
    }

    public class Holder extends RecyclerView.ViewHolder {
        TextView txt_date,txt_weight, txt_length;



        public Holder(@NonNull View itemView) {


            super(itemView);
            txt_date = itemView.findViewById(R.id.txt_date);
            txt_weight = itemView.findViewById(R.id.txt_weight);
            txt_length = itemView.findViewById(R.id.txt_length);



            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (onItemClickListener != null){
                        int position = getAdapterPosition();
                        if (position != RecyclerView.NO_POSITION){
                            onItemClickListener.onItemClick(position);
                        }
                    }
                }
            });


        }
    }

}
