package com.ola.apithon.olaeinsaac;

import android.app.ProgressDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.TextView;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.VolleyLog;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.StringRequest;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

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
                    URLEncoder.encode("Bangalore Palace", "UTF-8") + "&destination=" +
                    URLEncoder.encode("Embassy GolfLink", "UTF-8") +
                    "&waypoints=optimize:true|" +
                    URLEncoder.encode("Cubbon Park", "UTF-8") + "|" +
                    URLEncoder.encode("Bull Temple", "UTF-8") + "|" +
                    URLEncoder.encode("Lumbini Gardens", "UTF-8") + "|" +
                    URLEncoder.encode("UB City, Bangalore", "UTF-8") +
                    "&key=AIzaSyCiiu8WHdCas7oWF4p2dg_S0orga33Zt0M";
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }

        final ProgressDialog pDialog = new ProgressDialog(this);
        pDialog.setMessage("Loading...");
        pDialog.show();

        final ArrayList<HashMap<String, String>> pointsOfInterest = new ArrayList<HashMap<String, String>>();
        final HashMap<String, String> pointOfInterest = new HashMap<String, String>();

        // Request a string response from the provided URL.
        JsonObjectRequest googleDirectionReq = new JsonObjectRequest(Request.Method.GET, url, null,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        try {
                            JSONArray legs = response.getJSONArray("routes").getJSONObject(0).getJSONArray("legs");
                            for (int i = 0; i < legs.length(); i++) {
                                JSONObject point = legs.getJSONObject(i);
                                pointOfInterest.put("distance", point.getJSONObject("distance").getString("text"));
                                pointOfInterest.put("duration", point.getJSONObject("duration").getString("text"));
                                pointOfInterest.put("end_address", point.getString("end_address"));
                                pointOfInterest.put("end_lat", point.getJSONObject("end_location").getString("lat"));
                                pointOfInterest.put("end_lon", point.getJSONObject("end_location").getString("lng"));
                                pointOfInterest.put("start_address", point.getString("start_address"));
                                pointOfInterest.put("start_lat", point.getJSONObject("start_location").getString("lat"));
                                pointOfInterest.put("start_lon", point.getJSONObject("start_location").getString("lng"));
                                pointsOfInterest.add(pointOfInterest);
                            }
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                        pDialog.hide();
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                VolleyLog.d(TAG, "Error: " + error.getMessage());
                // hide the progress dialog
                pDialog.hide();
            }
        });

        final ArrayList<HashMap<String, String>> trips = new ArrayList<HashMap<String, String>>();
        final HashMap<String, String> trip = new HashMap<String, String>();

        ArrayList<JsonObjectRequest> olaEstReq = new ArrayList<JsonObjectRequest>();
        for (int i = 0; i < pointsOfInterest.size(); i++) {
            try {
                url = "http://sandbox­t.olacabs.com/v1/products?pickup_lat=" +
                        URLEncoder.encode(pointsOfInterest.get(i).get("start_lat"), "UTF-8") + "&pickup_lng=" +
                        URLEncoder.encode(pointsOfInterest.get(i).get("start_lng"), "UTF-8") + "&drop_lat=" +
                        URLEncoder.encode(pointsOfInterest.get(i).get("end_lat"), "UTF-8") + "&drop_lng=" +
                        URLEncoder.encode(pointsOfInterest.get(i).get("end_lng"), "UTF-8") + "&category=mini";
            } catch (UnsupportedEncodingException e) {
                e.printStackTrace();
            }
            olaEstReq.add(new JsonObjectRequest(Request.Method.GET, url, null,
                    new Response.Listener<JSONObject>() {
                        @Override
                        public void onResponse(JSONObject response) {
                            Log.d(TAG, response.toString());
                            ArrayList<HashMap<String, String>> trips = new ArrayList<HashMap<String, String>>();
                            HashMap<String, String> trip = new HashMap<String, String>();
                            try {
                                trip.put("eta", response.getJSONArray("ride_estimate").getJSONObject(0).getJSONObject("distance").getString("text"));
                                JSONObject rideEstimate = response.getJSONArray("ride_estimate").getJSONObject(0);
                                trip.put("distance", rideEstimate.getString("distance"));
                                trip.put("travel_time_in_minutes", rideEstimate.getString("travel_time_in_minutes"));
                                trip.put("amount_min", rideEstimate.getString("amount_min"));
                                trip.put("amount_max", rideEstimate.getString("amount_max"));
                                trips.add(trip);
                            } catch (JSONException e) {
                                e.printStackTrace();
                            }
                            pDialog.hide();
                        }
                    }, new Response.ErrorListener() {
                @Override
                public void onErrorResponse(VolleyError error) {
                    VolleyLog.d(TAG, "Error: " + error.getMessage());
                    // hide the progress dialog
                    pDialog.hide();
                }
            }) {

                @Override
                public Map<String, String> getHeaders() throws AuthFailureError {
                    HashMap<String, String> headers = new HashMap<String, String>();
                    headers.put("X-APP-TOKEN", "93c6a2732e2e4988bdff3f4f45a38584");
                    return headers;
                }
            });
            Log.d(TAG, trips.toString());
            // Add a request (in this example, called stringRequest) to your RequestQueue.

            MySingleton.getInstance(this).addToRequestQueue(googleDirectionReq);
            for (i = 0; i < olaEstReq.size(); i++) {
                MySingleton.getInstance(this).addToRequestQueue(olaEstReq.get(i));
            }
        }
    }
}
