package be.idr.nasaproject;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.util.Log;
import android.widget.ImageView;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.Date;

public class Earth extends AppCompatActivity implements APIRepo.OnApiDataDownloadedCallback{

    private ImageView earthImage;
    private EarthViewModel earthViewModel;
    private APIRepo apiRepo;

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

        apiRepo = new APIRepo(this);
        apiRepo.getEarthDates();
    }


    @Override
    public void OnApiDataDownloaded(String data) {
        if(data.substring(0, 3).equals("all")){
            getEarthByDate(data.substring(3));
        }
        else if(data.substring(0, 3).equals("edd")){
            saveEarthData(data.substring(3));
        }
    }

    private void getEarthByDate(String data){
        try {
            JSONArray jsonArray = new JSONArray(data);
            for (int i = 0; i < jsonArray.length(); i++){
                JSONObject obj = jsonArray.getJSONObject(i);
                apiRepo.getEarthData(obj.getString("date"));
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    private void saveEarthData(String data){
        try {
            JSONArray jsonArray = new JSONArray(data);
            EarthData earthData;
            for (int i = 0; i < jsonArray.length(); i++){
                JSONObject obj = jsonArray.getJSONObject(i);

                String identifier = obj.getString("identifier");
                String caption = obj.getString("caption");
                String image = obj.getString("image");
                String date = obj.getString("date");

                // I know this is ugly, but the deadline's getting awfully close
                String year = date.substring(0,4);
                String month = date.substring(5,7);
                String day = date.substring(8,10);
                String URL = "https://epic.gsfc.nasa.gov/archive/natural/" + year + "/" + month + "/" + day + "/png/" + image + ".png";
                earthData = new EarthData(date, identifier, caption, URL);
                earthViewModel.insert(earthData);
            }

        } catch (JSONException e) {
            e.printStackTrace();
        }
    }
}