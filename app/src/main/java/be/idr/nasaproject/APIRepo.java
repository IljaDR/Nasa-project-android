package be.idr.nasaproject;

import android.app.Application;
import android.content.Context;
import android.graphics.Bitmap;
import android.util.Log;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import java.util.ArrayList;
import java.util.List;

public class APIRepo {

    public interface OnApiDataDownloadedCallback{
        void OnApiDataDownloaded(String data);
    }

    private RequestQueue queue;
    private String url;
    private OnApiDataDownloadedCallback activityCallback;

    private List<String> APIKeys = new ArrayList<>();

    public APIRepo(Context context){
        // There's a very low limit of 1000 API calls per hour, while you need at least 1600 to even know where to get the images. You can request higher limits, but I doubt they'd respond quickly in the weekend.
        APIKeys.add("Lb7LHAfJEqP9slE5fulgsdezh2kNcojXUhJsZgiF");
        APIKeys.add("YyhqUTSJ3HzzD1zetF2RdQfsmtzj4pynSbbmOHl2");
        APIKeys.add("dk4Pnjtb10CoH5ZhsLb9UF01m2MqYASLP2Er7XSd");
        APIKeys.add("8N8bNLSn0jJcI21zLxfFAUyUcG7bTRUkXLgYMRtz");
        APIKeys.add("yP6NGwrPcgDqtVWnIwFbblRRgfhzDW1Uoc6Z1DZ7");
        APIKeys.add("gBIlciruAd3JC2QL3Tw6MMAV1x1wVtBa7jNgM1Nb");
        APIKeys.add("bX7XOtfpNlSaLcGN1xCNr4742uCwVQEQeiNbJMtH");
        queue = Volley.newRequestQueue(context);
        url = "https://api.nasa.gov/EPIC/api/natural/all?api_key=Lb7LHAfJEqP9slE5fulgsdezh2kNcojXUhJsZgiF";

        if(context instanceof OnApiDataDownloadedCallback){
            activityCallback = (OnApiDataDownloadedCallback) context;
        } else {
            throw new ClassCastException("Activity does not implement " + OnApiDataDownloadedCallback.class.getCanonicalName());
        }
    }

    public void getInfo(){
        StringRequest stringRequest = new StringRequest(Request.Method.GET, url,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        activityCallback.OnApiDataDownloaded(response);
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Log.e("API Log:","That didn't work!");
            }
        });

        queue.add(stringRequest);
    }

    public void getEarthDates(){
        StringRequest stringRequest = new StringRequest(Request.Method.GET, "https://epic.gsfc.nasa.gov/api/natural/all",
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        // all is identifier of data that's returned to the callback
                        // From "all dates"
                        activityCallback.OnApiDataDownloaded("all" + response);
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Log.e("API Log:","That didn't work!");
            }
        });

        queue.add(stringRequest);
    }

    public void getEarthData(String date){

        StringRequest stringRequest = new StringRequest(Request.Method.GET, "https://epic.gsfc.nasa.gov/api/natural/date/"+date+"?api_key="+getRandomKey(),
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {

                        // earth date data
                        activityCallback.OnApiDataDownloaded("edd" + response);
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Log.e("API Log:","That didn't work!");
            }
        });

        queue.add(stringRequest);
    }

    private String getRandomKey(){
        int index = (int) (Math.random() * (this.APIKeys.size()));
        return this.APIKeys.get(index);
    }
}
