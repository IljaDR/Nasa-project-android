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

public class APIRepo {

    public interface OnApiDataDownloadedCallback{
        void OnApiDataDownloaded(String data);
    }

    private RequestQueue queue;
    private String url;
    private OnApiDataDownloadedCallback activityCallback;

    public APIRepo(Context context){
        queue = Volley.newRequestQueue(context);
        url = "https://api.nasa.gov/EPIC/api/natural/all?api_key=Lb7LHAfJEqP9slE5fulgsdezh2kNcojXUhJsZgiF";

        if(context instanceof OnApiDataDownloadedCallback){
            activityCallback = (OnApiDataDownloadedCallback) context;
        } else {
            throw new ClassCastException("Activity does not implement " + OnApiDataDownloadedCallback.class.getCanonicalName());
        }
    }

    public void getAllDates(Context context){
        queue = Volley.newRequestQueue(context);
        url = "https://api.nasa.gov/planetary/apod?date=2020-12-28&api_key=Lb7LHAfJEqP9slE5fulgsdezh2kNcojXUhJsZgiF";

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

    public void getIMGURL(){

    }
}
