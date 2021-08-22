package be.idr.nasaproject;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.LifecycleOwner;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.os.Parcelable;
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

public class EarthActivity extends AppCompatActivity implements APIRepo.OnApiDataDownloadedCallback, EarthListAdapter.ItemClickListener {
    private EarthViewModel earthViewModel;
    private APIRepo apiRepo;
    EarthListAdapter adapter;
    RecyclerView recyclerView;
    JSONArray APIData;
    int APIIndex = 0;
    Parcelable recylerViewState;

    static final String EARTH_DATE = " be.idr.nasaproject.EARTH_DATE";
    static final String EARTH_CAPTION = " be.idr.nasaproject.EARTH_CAPTION";
    static final String EARTH_IMAGE_URL = " be.idr.nasaproject.EARTH_IMAGE_URL";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        apiRepo = new APIRepo(this);
        apiRepo.getEarthDates();

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_earth);

        earthViewModel = new ViewModelProvider(this, ViewModelProvider.AndroidViewModelFactory.getInstance(this.getApplication())).get(EarthViewModel.class);

        earthViewModel.getAllDates().observe(this, dates -> {
            recyclerView = findViewById(R.id.recyclerview);
            recyclerView.setLayoutManager(new GridLayoutManager(this,2));
            adapter = new EarthListAdapter(this, dates);
            adapter.setClickListener(this);
            recyclerView.setAdapter(adapter);
            GridLayoutManager layoutManager = ((GridLayoutManager)recyclerView.getLayoutManager());
            recyclerView.addOnScrollListener(new RecyclerView.OnScrollListener() {
                @Override
                public void onScrolled(@NonNull RecyclerView recyclerView, int dx, int dy) {
                    super.onScrolled(recyclerView, dx, dy);
                    if(APIData != null){
                        // I can't find a reliable way to tell how far along the recyclerview I am, this is the best I could figure out
                        // but usually it completely messes up quickly and it will nog longer work...
                        Log.e("Scroll log:", String.valueOf(layoutManager.findLastCompletelyVisibleItemPosition()));
                        try {
                            int index = 4 + layoutManager.findLastVisibleItemPosition()/20;
                            Log.e("Scroll log:", "Index: " + String.valueOf(index));
                            Log.e("Scroll log:", "APIindex: " + String.valueOf(APIIndex));
                            if (index > APIIndex){
                                apiRepo.getEarthData(APIData.getJSONObject(index).getString("date"));
                                APIIndex++;
                                recyclerView.invalidate();
                            }
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                }
            });
        });

        earthViewModel.logDateCount().observe(this, count -> {
            Log.e("Count", String.valueOf(count));
        });

    }

    @Override
    public void onItemClick(View view, int position) {
        Intent intent = new Intent(this, EarthDetailActivity.class);
        intent.putExtra(EARTH_DATE, adapter.getItem(position).getDate());
        intent.putExtra(EARTH_CAPTION, adapter.getItem(position).getCaption());
        intent.putExtra(EARTH_IMAGE_URL, adapter.getItem(position).getImgURL());
        startActivity(intent);
//        Toast.makeText(this, "You clicked " + adapter.getItem(position).getDate() + " on row number " + position, Toast.LENGTH_SHORT).show();
    }


    @Override
    public void OnApiDataDownloaded(String data) {
        if(data.substring(0, 3).equals("all")){
//            getEarthByDate(data.substring(3));
            try {
                APIData = new JSONArray(data.substring(3));
                apiRepo.getEarthData(APIData.getJSONObject(0).getString("date"));
                apiRepo.getEarthData(APIData.getJSONObject(1).getString("date"));
                apiRepo.getEarthData(APIData.getJSONObject(2).getString("date"));
            }
            catch (JSONException e){
                e.printStackTrace();
            }
        }
        else if(data.substring(0, 3).equals("edd")){
//            recyclerView.invalidate();
//            APIIndex++;
//            try {
//                apiRepo.getEarthData(APIData.getJSONObject(APIIndex).getString("date"));
//            }
//            catch (JSONException e){
//                e.printStackTrace();
//            }
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