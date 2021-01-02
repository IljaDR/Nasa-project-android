package be.idr.nasaproject;

import android.graphics.Bitmap;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

class EarthViewHolder extends RecyclerView.ViewHolder {
    private final TextView EarthDate;

    public EarthViewHolder(@NonNull View itemView) {
        super(itemView);
        EarthDate = itemView.findViewById(R.id.textView);
    }

    public void bind(String date){
        EarthDate.setText(date);
    }

    static EarthViewHolder create(ViewGroup parent){
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.recyclerview_item, parent, false);
        return new EarthViewHolder(view);
    }
}
