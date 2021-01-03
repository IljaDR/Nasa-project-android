package be.idr.nasaproject;

import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.DiffUtil;
import androidx.recyclerview.widget.ListAdapter;

public class EarthListAdapter extends ListAdapter<EarthData, EarthViewHolder>  {

    public EarthListAdapter(@NonNull DiffUtil.ItemCallback<EarthData> diffCallback) {
        super(diffCallback);
    }

    @Override
    public EarthViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return EarthViewHolder.create(parent);
    }

    @Override
    public void onBindViewHolder(EarthViewHolder holder, int position) {
        EarthData current = getItem(position);
        holder.bind(current.getDate());
    }

    static class WordDiff extends DiffUtil.ItemCallback<EarthData> {

        @Override
        public boolean areItemsTheSame(@NonNull EarthData oldItem, @NonNull EarthData newItem) {
            return oldItem == newItem;
        }

        @Override
        public boolean areContentsTheSame(@NonNull EarthData oldItem, @NonNull EarthData newItem) {
            return oldItem.getDate().equals(newItem.getDate());
        }
    }
}
