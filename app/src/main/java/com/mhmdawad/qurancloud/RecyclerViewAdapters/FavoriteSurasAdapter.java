package com.mhmdawad.qurancloud.RecyclerViewAdapters;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.TextView;

import com.mhmdawad.qurancloud.MediaPlayer.Mp3File;
import com.mhmdawad.qurancloud.R;
import com.mhmdawad.qurancloud.Retrofit.Objects.Reciter;
import com.mhmdawad.qurancloud.database.FavoriteEntity;

import java.util.ArrayList;
import java.util.List;

public class FavoriteSurasAdapter extends RecyclerView.Adapter<FavoriteSurasAdapter.MainAdapter>
implements Filterable {

    private List<Mp3File> favoriteList;
    private List<Mp3File> favoriteListFull;
    private ClickListener mClickListener;

    public FavoriteSurasAdapter(List<Mp3File> favoriteEntityList) {
        favoriteList = favoriteEntityList;
        this.favoriteListFull = new ArrayList<>(favoriteList);

    }

    @NonNull
    @Override
    public FavoriteSurasAdapter.MainAdapter onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.main_recycler_view_items
        ,viewGroup,false);
        return new MainAdapter(view);
    }

    @Override
    public void onBindViewHolder(@NonNull FavoriteSurasAdapter.MainAdapter mainAdapter, int i) {
        mainAdapter.readerName.setText(favoriteList.get(i).getReaderName());
        mainAdapter.suraName.setText(favoriteList.get(i).getSuraName());
    }

    @Override
    public int getItemCount() {
        if(favoriteList.size()<=0) {
            return 0;
        }else {
            return favoriteList.size();
        }
    }

     class MainAdapter extends RecyclerView.ViewHolder implements View.OnClickListener {
        TextView readerName;
        TextView suraName;

         MainAdapter(@NonNull View itemView) {
            super(itemView);
             readerName = itemView.findViewById(R.id.readerTextView);
             suraName = itemView.findViewById(R.id.rewayaTextView);

             itemView.setOnClickListener(this);
         }

         @Override
         public void onClick(View v) {
             mClickListener.itemOnClickListener(getAdapterPosition(),itemView);
         }
     }
     public void setOnClickListener(ClickListener clickListener){
        mClickListener = clickListener;
     }

     public interface ClickListener{
        void itemOnClickListener(int pos,View v);
     }
    private Filter filter = new Filter() {
        @Override
        protected FilterResults performFiltering(CharSequence constraint) {
            ArrayList<Mp3File> filteredRciters = new ArrayList<>();

            if(constraint == null || constraint.length() <= 0 ){
                filteredRciters.addAll(favoriteListFull);
            }else{
                String searched = constraint.toString().toLowerCase().trim();

                for (Mp3File item : favoriteListFull){
                    if(item.getReaderName().toLowerCase().contains(searched)){
                        filteredRciters.add(item);
                    }
                }
            }

            FilterResults results = new FilterResults();
            results.values  = filteredRciters;

            return results;
        }

        @Override
        protected void publishResults(CharSequence constraint, FilterResults results) {
            favoriteList.clear();
            favoriteList.addAll(((List)results.values));
            notifyDataSetChanged();
        }
    };
    @Override
    public Filter getFilter() {
        return filter;
    }
}