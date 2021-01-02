package be.idr.nasaproject;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.os.Debug;
import android.util.Log;
import android.widget.ImageView;

import com.squareup.picasso.Picasso;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

public class Earth extends AppCompatActivity implements APIRepo.OnApiDataDownloadedCallback{

    private ImageView earthImage;
    private EarthViewModel earthViewModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_earth);

        earthImage = findViewById(R.id.earthImage);



        RecyclerView recyclerView = findViewById(R.id.recyclerview);
        final EarthListAdapter adapter = new EarthListAdapter(new EarthListAdapter.WordDiff());
        recyclerView.setAdapter(adapter);
        recyclerView.setLayoutManager(new GridLayoutManager(this,2));

        earthViewModel = new ViewModelProvider(this, ViewModelProvider.AndroidViewModelFactory.getInstance(this.getApplication())).get(EarthViewModel.class);

        earthViewModel.getAllDates().observe(this, dates -> {
            adapter.submitList(dates);
        });

        APIRepo apiRepo = new APIRepo(this);
        apiRepo.getInfo();
    }


    @Override
    public void OnApiDataDownloaded(String data) {
        Log.e("Dates","Went into API Downloaded function");
        try {
            JSONArray jsonArray = new JSONArray(data);
            List<String> dates = new ArrayList<>();
            for (int i = 0; i < jsonArray.length(); i++){
                JSONObject obj = jsonArray.getJSONObject(i);
                dates.add(obj.getString("date"));
                Log.e("Dates",obj.getString("date"));
            }

            Log.e("Dates",dates.toString());
//            Picasso.get().
//                    load(url).
//                    into(earthImage);
        } catch (JSONException e) {
            e.printStackTrace();
        }

    }
}