package be.idr.nasaproject.UI;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import be.idr.nasaproject.R;

public class MainActivity extends AppCompatActivity{
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    public void startEarth(View view){
        Intent intent = new Intent(this, EarthActivity.class);
        startActivity(intent);
    }

    public void underConstruction(View view){
        Toast.makeText(this, "This part of the app is still under construction!", Toast.LENGTH_SHORT).show();
    }

}