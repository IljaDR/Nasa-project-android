package be.idr.nasaproject;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link DetailFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class DetailFragment extends Fragment {

    interface onRatingListener{
        void onRating(EarthData.Rating rating, String identifier);
    }

    private onRatingListener onRatingListener;

    private static final String ARG_RATING = "com.example.dialogsfragments.RATING";

    public DetailFragment() {
        // Required empty public constructor
    }

    public static DetailFragment newInstance(String rating) {
        DetailFragment fragment = new DetailFragment();
        Bundle args = new Bundle();
        args.putString(ARG_RATING, rating);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_detail, container, false);
    }
}