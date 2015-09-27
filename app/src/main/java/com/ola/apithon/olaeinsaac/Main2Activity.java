package com.ola.apithon.olaeinsaac;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.TextView;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.VolleyLog;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.StringRequest;

import org.json.JSONObject;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;

public class Main2Activity extends AppCompatActivity {

    public static final String TAG = Main2Activity.class.getSimpleName();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main2);

        final TextView mTextView = (TextView) findViewById(R.id.abc);


        // Instantiate the RequestQueue.
        RequestQueue queue = MySingleton.getInstance(this.getApplicationContext()).
                getRequestQueue();
        String url = null;
        try {
            url = "https://maps.googleapis.com/maps/api/directions/json?origin=" +
                    URLEncoder.encode("Adelaide,SA", "UTF-8") + "&destination=" +
                    URLEncoder.encode("Adelaide,SA", "UTF-8") +
                    "&waypoints=optimize:true|" +
                    URLEncoder.encode("Barossa+Valley,SA", "UTF-8") + "|" +
                    URLEncoder.encode("Clare,SA", "UTF-8") + "|" +
                    URLEncoder.encode("Connawarra,SA", "UTF-8") + "|" +
                    URLEncoder.encode("McLaren+Vale,SA", "UTF-8") +
                    "&key=AIzaSyCiiu8WHdCas7oWF4p2dg_S0orga33Zt0M";
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }

        // Request a string response from the provided URL.
        JsonObjectRequest jsonObjReq = new JsonObjectRequest(Request.Method.GET, url, null,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        Log.d(TAG, response.toString());
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                VolleyLog.d(TAG, "Error: " + error.getMessage());
            }
        });
        // Add a request (in this example, called stringRequest) to your RequestQueue.

        MySingleton.getInstance(this).addToRequestQueue(jsonObjReq);
    }
}
