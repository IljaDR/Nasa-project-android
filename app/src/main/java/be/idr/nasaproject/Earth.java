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
            Log.e("Get Caption: ","1");
            saveEarthData(data.substring(3));
        }
    }

    private void getEarthByDate(String data){
        try {
            JSONArray jsonArray = new JSONArray(data);
            EarthData earthData;
            Log.e("DB Length",String.valueOf(jsonArray.length()));
            for (int i = 0; i < jsonArray.length(); i++){
                JSONObject obj = jsonArray.getJSONObject(i);
                apiRepo.getEarthData(obj.getString("date"));
                earthData = new EarthData(obj.getString("date"));
                earthViewModel.insert(earthData);
            }

//            Picasso.get().
//                    load(url).
//                    into(earthImage);
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    private void saveEarthData(String data){
        Log.e("Get Caption: ","2");
        //@NonNull String date, @NonNull String identifier, @NonNull String caption, @NonNull String image
        EarthData earthData;
        try {
            Log.e("Get Caption: ","3");
            JSONObject obj = new JSONObject(data);
            Log.e("Get Caption: ",obj.getString("caption"));
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

}