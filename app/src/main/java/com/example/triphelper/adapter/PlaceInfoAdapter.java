package com.example.triphelper.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Filter;
import android.widget.Filterable;

import androidx.recyclerview.widget.RecyclerView;

import com.example.triphelper.R;
import com.example.triphelper.struct.PlaceInfo;

import java.util.ArrayList;
import java.util.List;

public class PlaceInfoAdapter  extends RecyclerView.Adapter<PlaceInfoViewHolder>
        implements Filterable {
    private List<PlaceInfo> descriptionList = new ArrayList<>();
    private List<PlaceInfo> descriptionListFull;
    public void setItems(List<PlaceInfo> descriptionListCurr) {
        descriptionList.addAll(descriptionListCurr);
        descriptionListFull = new ArrayList<>(descriptionListCurr); // !!!!!!!!!!!!!!!!!!!!!!
        notifyDataSetChanged();
    }

    public void clearItems() {
        descriptionList.clear();
        notifyDataSetChanged();
    }
    @Override
    public PlaceInfoViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.short_decription_layout, parent, false);
        return new PlaceInfoViewHolder(view);
    }

    @Override
    public void onBindViewHolder(PlaceInfoViewHolder holder, int position) {
        holder.bind(descriptionList.get(position));
    }

    @Override
    public int getItemCount() {
        return descriptionList.size();
    }

    @Override
    public Filter getFilter() {
        return exampleFilter;
    }
    private Filter exampleFilter = new Filter() {
        @Override
        protected FilterResults performFiltering(CharSequence charSequence) {
            List<PlaceInfo> filteredList = new ArrayList<>();

            if(charSequence == null || charSequence.length() == 0){
                filteredList.addAll(descriptionListFull);
            } else{
                String filterPattern = charSequence.toString().toLowerCase().trim();
                for(int i = 0; i < descriptionListFull.size(); i++){
                    PlaceInfo currItem = descriptionListFull.get(i);
                    if(currItem.getName().toLowerCase().contains(filterPattern)
                            || currItem.getDescription().toLowerCase().trim().contains(filterPattern)
                            || (currItem.getName() + currItem.getDescription()).toLowerCase().trim().contains(filterPattern)
                            || ( currItem.getDescription() + currItem.getName()).toLowerCase().trim().contains(filterPattern)){
                        filteredList.add(currItem);
                    }
                }
            }
            FilterResults results = new FilterResults();
            results.values = filteredList;
            return results;
        }

        @Override
        protected void publishResults(CharSequence charSequence, FilterResults filterResults) {
            descriptionList.clear();
            descriptionList.addAll((List)filterResults.values);
            notifyDataSetChanged();
        }
    };
}
