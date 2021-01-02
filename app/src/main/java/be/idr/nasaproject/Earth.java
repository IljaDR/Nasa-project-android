package be.idr.nasaproject;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import org.json.JSONException;
import org.json.JSONObject;

public class Earth extends AppCompatActivity implements APIRepo.OnApiDataDownloadedCallback{

    private ImageView earthImage;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_earth);

        earthImage = findViewById(R.id.earthImage);

        //APIRepo apiRepo = new APIRepo(this);
        //apiRepo.getInfo();
    }


    @Override
    public void OnApiDataDownloaded(String data) {
//        try {
//            JSONObject jsonObject = new JSONObject(data);
//            String url = jsonObject.getString("url");
//            Picasso.get().
//                    load(url).
//                    into(earthImage);
//        } catch (JSONException e) {
//            e.printStackTrace();
//        }


    }
}