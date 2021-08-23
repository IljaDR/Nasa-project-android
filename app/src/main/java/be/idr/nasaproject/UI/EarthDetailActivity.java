package be.idr.nasaproject.UI;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;

import com.bumptech.glide.Glide;

import be.idr.nasaproject.DB.EarthData;
import be.idr.nasaproject.DB.EarthViewModel;
import be.idr.nasaproject.R;

public class EarthDetailActivity extends AppCompatActivity implements DetailFragment.onRatingListener {
    private EarthViewModel earthViewModel;
    private TextView txtCaption;
    private TextView txtDate;
    private Button btnLike;
    private Button btnDislike;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        earthViewModel = new ViewModelProvider(this, ViewModelProvider.AndroidViewModelFactory.getInstance(this.getApplication())).get(EarthViewModel.class);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.fragment_detail);

        Intent intent = getIntent();
        String identifier = intent.getStringExtra(EarthActivity.EARTH_IDENTIFIER);
        String date = intent.getStringExtra(EarthActivity.EARTH_DATE);
        String caption = intent.getStringExtra(EarthActivity.EARTH_CAPTION);
        String earthImageURL = intent.getStringExtra(EarthActivity.EARTH_IMAGE_URL);
        EarthData.Rating rating = (EarthData.Rating)intent.getSerializableExtra(EarthActivity.EARTH_RATING);

//        Log.e("Rating", String.valueOf(rating));

        txtCaption = findViewById(R.id.txtCaption);
        txtDate = findViewById(R.id.txtDate);
        txtCaption.setText(caption);
        txtDate.setText(date);

        Glide.with(this).load(earthImageURL).into((ImageView) findViewById(R.id.detailImage));

        btnLike = findViewById(R.id.btnLike);
        btnLike.setOnClickListener(v -> onRating(EarthData.Rating.LIKE, identifier));

        btnDislike = findViewById(R.id.btnDislike);
        btnDislike.setOnClickListener(v -> onRating(EarthData.Rating.DISLIKE, identifier));

        if(rating.equals(EarthData.Rating.LIKE)){
            btnLike.setBackgroundColor(Color.parseColor("#09de1e"));
            btnDislike.setBackgroundColor(getResources().getColor(R.color.buttonColor));
        }
        else if(rating.equals(EarthData.Rating.DISLIKE)){
            btnDislike.setBackgroundColor(Color.parseColor("#db072a"));
            btnLike.setBackgroundColor(getResources().getColor(R.color.buttonColor));
        }

//        Toast.makeText(this, "You clicked " + caption, Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onRating(EarthData.Rating rating, String identifier) {
//        Toast.makeText(this, "You clicked " + rating, Toast.LENGTH_SHORT).show();
        if(rating.equals(EarthData.Rating.LIKE)){
            btnLike.setBackgroundColor(Color.parseColor("#09de1e"));
            btnDislike.setBackgroundColor(getResources().getColor(R.color.buttonColor));
        }
        else if(rating.equals(EarthData.Rating.DISLIKE)){
            btnDislike.setBackgroundColor(Color.parseColor("#db072a"));
            btnLike.setBackgroundColor(getResources().getColor(R.color.buttonColor));
        }
        earthViewModel.addRating(rating, identifier);
    }
}
