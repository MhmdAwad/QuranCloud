package com.mhmdawad.qurancloud.RecyclerViewAdapters;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.TextView;
import com.mhmdawad.qurancloud.R;
import com.mhmdawad.qurancloud.Retrofit.Objects.Reciter;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

public class MainActivityAdapter extends RecyclerView.Adapter<MainActivityAdapter.MainAdapter>
implements Filterable {

    private ArrayList<Reciter> reciters;
    private ArrayList<Reciter> recitersFull;
    private static ClickListener mClickListener;


    public MainActivityAdapter(ArrayList<Reciter> reciters) {
        this.reciters = reciters;
        this.recitersFull = new ArrayList<>(reciters);
    }

    @NonNull
    @Override
    public MainAdapter onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.main_recycler_view_items,
                viewGroup, false);
        return new MainAdapter(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MainAdapter mainAdapter, int i) {
        mainAdapter.reciterName.setText(reciters.get(i).getName());
        mainAdapter.rewayaName.setText(reciters.get(i).getRewaya());
    }

    @Override
    public int getItemCount() {
        if (reciters != null) {
            return reciters.size();
        } else {
            return 0;
        }
    }

    class MainAdapter extends RecyclerView.ViewHolder implements View.OnClickListener {
        TextView reciterName;
        TextView rewayaName;

        MainAdapter(@NonNull View itemView) {
            super(itemView);
            reciterName = itemView.findViewById(R.id.readerTextView);
            rewayaName = itemView.findViewById(R.id.rewayaTextView);

            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View v) {
            mClickListener.onItemClick(getAdapterPosition(),itemView);
        }
    }

    public void setOnClickListener(ClickListener clickListener){
        mClickListener = clickListener;
    }

    public interface ClickListener{
        void onItemClick(int pos, View view);
    }

    private Filter filter = new Filter() {
        @Override
        protected FilterResults performFiltering(CharSequence constraint) {
             ArrayList<Reciter> filteredRciters = new ArrayList<>();

             if(constraint == null || constraint.length() <= 0 ){
                 filteredRciters.addAll(recitersFull);
             }else{
                 String searched = constraint.toString().toLowerCase().trim();

                 for (Reciter item : recitersFull){
                     if(item.getName().toLowerCase().contains(searched)){
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
            reciters.clear();
            reciters.addAll(((List)results.values));
            notifyDataSetChanged();
        }
    };
    @Override
    public Filter getFilter() {
        return filter;
    }
}
