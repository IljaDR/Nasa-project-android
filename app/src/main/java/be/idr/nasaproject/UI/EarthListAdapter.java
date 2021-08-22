package be.idr.nasaproject.UI;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;

import java.util.List;

import be.idr.nasaproject.DB.EarthData;
import be.idr.nasaproject.R;

public class EarthListAdapter extends RecyclerView.Adapter<EarthListAdapter.ViewHolder> {

    private List<EarthData> mData;
    private LayoutInflater mInflater;
    private ItemClickListener mClickListener;

    // data is passed into the constructor
    public EarthListAdapter(Context context, List<EarthData> data) {
        this.mInflater = LayoutInflater.from(context);
        this.mData = data;
        this.setStateRestorationPolicy(StateRestorationPolicy.ALLOW);
    }

    // inflates the row layout from xml when needed
    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = mInflater.inflate(R.layout.recyclerview_item, parent, false);
        return new ViewHolder(view);
    }

    // binds the data to the TextView in each row
    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        EarthData earthData = mData.get(position);
//        holder.image.setText(earthData.getImgURL());
        // glide seems slightly faster than picasso
        Glide.with(holder.itemView.getContext()).load(earthData.getImgURL()).into(holder.image);
//        Picasso.get().
//                load(earthData.getImgURL()).
//                into(holder.image);
    }


    // total number of rows
    @Override
    public int getItemCount() {
        return mData.size();
    }


    // stores and recycles views as they are scrolled off screen
    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        ImageView image;

        ViewHolder(View itemView) {
            super(itemView);
            image = itemView.findViewById(R.id.imageButton);
            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View view) {
            if (mClickListener != null) mClickListener.onItemClick(view, getAdapterPosition());
        }
    }

    // convenience method for getting data at click position
    public EarthData getItem(int id) {
        return mData.get(id);
    }

    // allows clicks events to be caught
    public void setClickListener(ItemClickListener itemClickListener) {
        this.mClickListener = itemClickListener;
    }

    // parent activity will implement this method to respond to click events
    public interface ItemClickListener {
        void onItemClick(View view, int position);
    }

}
