package be.idr.nasaproject;

import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.DiffUtil;
import androidx.recyclerview.widget.ListAdapter;

public class EarthListAdapter extends ListAdapter<EarthDate, EarthViewHolder> {

    public EarthListAdapter(@NonNull DiffUtil.ItemCallback<EarthDate> diffCallback) {
        super(diffCallback);
    }

    @Override
    public EarthViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return EarthViewHolder.create(parent);
    }

    @Override
    public void onBindViewHolder(EarthViewHolder holder, int position) {
        EarthDate current = getItem(position);
        holder.bind(current.getDate());
    }

    static class WordDiff extends DiffUtil.ItemCallback<EarthDate> {

        @Override
        public boolean areItemsTheSame(@NonNull EarthDate oldItem, @NonNull EarthDate newItem) {
            return oldItem == newItem;
        }

        @Override
        public boolean areContentsTheSame(@NonNull EarthDate oldItem, @NonNull EarthDate newItem) {
            return oldItem.getDate().equals(newItem.getDate());
        }
    }
}
