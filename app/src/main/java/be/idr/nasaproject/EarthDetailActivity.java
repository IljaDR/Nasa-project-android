package be.idr.nasaproject;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.StringDef;
import androidx.appcompat.app.AppCompatActivity;

import com.bumptech.glide.Glide;

public class EarthDetailActivity extends AppCompatActivity implements DetailFragment.onRatingListener {

    private TextView txtCaption;
    private TextView txtDate;
    private String rating;
    private Button btnLike;
    private Button btnDislike;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.fragment_detail);

        Intent intent = getIntent();
        String caption = intent.getStringExtra(EarthActivity.EARTH_CAPTION);
        String date = intent.getStringExtra(EarthActivity.EARTH_DATE);
        String earthImageURL = intent.getStringExtra(EarthActivity.EARTH_IMAGE_URL);

        txtCaption = findViewById(R.id.txtCaption);
        txtDate = findViewById(R.id.txtDate);
        txtCaption.setText(caption);
        txtDate.setText(date);

        Glide.with(this).load(earthImageURL).into((ImageView) findViewById(R.id.detailImage));

        btnLike = findViewById(R.id.btnLike);
        btnLike.setOnClickListener(v -> onRating("like"));

        btnDislike = findViewById(R.id.btnDislike);
        btnDislike.setOnClickListener(v -> onRating("dislike"));

//        Toast.makeText(this, "You clicked " + caption, Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onRating(String rating) {
        this.rating = rating;
        Toast.makeText(this, "You clicked " + rating, Toast.LENGTH_SHORT).show();
        if(rating.equals("like")){
            btnLike.setBackgroundColor(Color.parseColor("#09de1e"));
            btnDislike.setBackgroundColor(getResources().getColor(R.color.buttonColor));
        }
        else if(rating.equals("dislike")){
            btnDislike.setBackgroundColor(Color.parseColor("#db072a"));
            btnLike.setBackgroundColor(getResources().getColor(R.color.buttonColor));
        }
    }
}
