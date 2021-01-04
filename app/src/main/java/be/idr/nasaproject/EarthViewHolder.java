package be.idr.nasaproject;

import android.graphics.Bitmap;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.squareup.picasso.Picasso;

class EarthViewHolder extends RecyclerView.ViewHolder{
    private final ImageButton imageButton;

    public EarthViewHolder(@NonNull View itemView) {
        super(itemView);
        imageButton = itemView.findViewById(R.id.imageButton);
    }

    public void bind(EarthData earthData){
        Picasso.get().
                load(earthData.getImgURL()).
                into(imageButton);
    }

    static EarthViewHolder create(ViewGroup parent){
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.recyclerview_item, parent, false);
        return new EarthViewHolder(view);
    }

}
