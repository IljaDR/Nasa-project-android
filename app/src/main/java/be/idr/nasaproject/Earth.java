package be.idr.nasaproject;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.Toast;

import com.squareup.picasso.Picasso;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

public class Earth extends AppCompatActivity implements APIRepo.OnApiDataDownloadedCallback, EarthListAdapter.ItemClickListener {
    private EarthViewModel earthViewModel;
    private APIRepo apiRepo;
    EarthListAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_earth);

        earthViewModel = new ViewModelProvider(this, ViewModelProvider.AndroidViewModelFactory.getInstance(this.getApplication())).get(EarthViewModel.class);

        earthViewModel.getAllDates().observe(this, dates -> {
            RecyclerView recyclerView = findViewById(R.id.recyclerview);
            recyclerView.setLayoutManager(new GridLayoutManager(this,2));
            adapter = new EarthListAdapter(this, dates);
            adapter.setClickListener(this);
            recyclerView.setAdapter(adapter);
        });

        apiRepo = new APIRepo(this);
        apiRepo.getEarthDates();
    }

    @Override
    public void onItemClick(View view, int position) {
        Toast.makeText(this, "You clicked " + adapter.getItem(position).getDate() + " on row number " + position, Toast.LENGTH_SHORT).show();
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
                String dateString = obj.getString("date");
                Date date = new SimpleDateFormat("yyyy-MM-dd").parse(dateString);

                Calendar calendar = Calendar.getInstance();
                calendar.setTime(date);
                int year = calendar.get(Calendar.YEAR);
                int month = calendar.get(Calendar.MONTH) + 1;
                int day = calendar.get(Calendar.DAY_OF_MONTH);
                String formattedDay = day < 10 ? "0" + String.valueOf(day) : String.valueOf(day);
                String formattedMonth = month < 10 ? "0" + String.valueOf(month) : String.valueOf(month);
                String URL = "https://epic.gsfc.nasa.gov/archive/natural/" + year + "/" + formattedMonth + "/" + formattedDay + "/png/" + image + ".png";
                earthData = new EarthData(dateString, identifier, caption, URL);
                earthViewModel.insert(earthData);
            }

        } catch (JSONException | ParseException e) {
            e.printStackTrace();
        }
    }
}