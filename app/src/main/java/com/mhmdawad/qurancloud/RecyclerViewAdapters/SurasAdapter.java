package com.mhmdawad.qurancloud.RecyclerViewAdapters;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.mhmdawad.qurancloud.R;
import java.util.ArrayList;

public class SurasAdapter extends RecyclerView.Adapter<SurasAdapter.MainAdapter> {

    private ArrayList<String> suraArrayList;
    private ItemClickListener clickListener;

    public SurasAdapter(ArrayList<String> arrayList) {
        suraArrayList = arrayList;
    }

    @NonNull
    @Override
    public MainAdapter onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.quran_reader_recycler_items
                , parent, false);
        return new MainAdapter(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MainAdapter mainAdapter, int i) {
        mainAdapter.suraName.setText(suraArrayList.get(i));
    }

    @Override
    public int getItemCount() {
        if (suraArrayList != null) {
            return suraArrayList.size();
        } else
            return 0;
    }

    class MainAdapter extends RecyclerView.ViewHolder implements View.OnClickListener {
        TextView suraName;

        MainAdapter(@NonNull View itemView) {
            super(itemView);

            suraName = itemView.findViewById(R.id.suraName);

            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View v) {
            clickListener.onItemClick(getAdapterPosition(),itemView);
        }
    }

    public void setOnClickListener(ItemClickListener clickListener){this.clickListener = clickListener;}

    public interface ItemClickListener{
        void onItemClick(int pos, View v);
    }
}
