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

    ArrayList<HashMap<String, String>> pointsOfInterest = new ArrayList<HashMap<String, String>>();
    ArrayList<HashMap<String, String>> trips = new ArrayList<HashMap<String, String>>();

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

        // Request a string response from the provided URL.
        JsonObjectRequest googleDirectionReq = new JsonObjectRequest(Request.Method.GET, url, null,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        try {
                            JSONArray legs = response.getJSONArray("routes").getJSONObject(0).getJSONArray("legs");
                            for (int i = 0; i < legs.length(); i++) {
                                JSONObject point = legs.getJSONObject(i);
                                HashMap<String, String> pointOfInterest = new HashMap<String, String>();
                                pointOfInterest.put("distance", point.getJSONObject("distance").getString("text"));
                                pointOfInterest.put("duration", point.getJSONObject("duration").getString("text"));
                                pointOfInterest.put("end_address", point.getString("end_address"));
                                pointOfInterest.put("end_lat", point.getJSONObject("end_location").getString("lat"));
                                pointOfInterest.put("end_lng", point.getJSONObject("end_location").getString("lng"));
                                pointOfInterest.put("start_address", point.getString("start_address"));
                                pointOfInterest.put("start_lat", point.getJSONObject("start_location").getString("lat"));
                                pointOfInterest.put("start_lng", point.getJSONObject("start_location").getString("lng"));
                                Main2Activity.this.pointsOfInterest.add(pointOfInterest);
                            }
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                        Main2Activity.this.olaEstReq(pointsOfInterest);

                    }
                }, new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        VolleyLog.d(TAG, "Error: " + error.getMessage());
                        // hide the progress dialog
                    }
                });

                // Add a request (in this example, called stringRequest) to your RequestQueue.
                MySingleton.getInstance(this).addToRequestQueue(googleDirectionReq);

    }

    public void olaEstReq(ArrayList<HashMap<String, String>> pointsOfInterest) {
        for (int i = 0; i < Main2Activity.this.pointsOfInterest.size(); i++) {
            //String url3 = "http://sandbox-t.olacabs.com/v1/products?pickup_lat=12.950072&pickup_lng=77.642684&drop_lat=13.039308&drop_lng=77.599994&category=sedan";
            String url2 = String.format("http://sandbox­t.olacabs.com/v1/products?pickup_lat=%.03f&pickup_lng=%.03f&drop_lat=%.03f&drop_lng=%.03f&category=mini",
                    Float.parseFloat(pointsOfInterest.get(i).get("start_lat")),
                    Float.parseFloat(pointsOfInterest.get(i).get("start_lng")),
                    Float.parseFloat(pointsOfInterest.get(i).get("end_lat")),
                    Float.parseFloat(pointsOfInterest.get(i).get("end_lng")));
            MySingleton.getInstance(Main2Activity.this).addToRequestQueue(
                    new JsonObjectRequest(
                            Request.Method.GET,
                            url2,
                            null,
                            new Response.Listener<JSONObject>() {
                                @Override
                                public void onResponse(JSONObject response) {
                                    HashMap<String, String> trip = new HashMap<String, String>();
                                    try {
                                        trip.put("eta", response.getJSONArray("ride_estimate").getJSONObject(0).getJSONObject("distance").getString("text"));
                                        JSONObject rideEstimate = response.getJSONArray("ride_estimate").getJSONObject(0);
                                        trip.put("distance", rideEstimate.getString("distance"));
                                        trip.put("travel_time_in_minutes", rideEstimate.getString("travel_time_in_minutes"));
                                        trip.put("amount_min", rideEstimate.getString("amount_min"));
                                        trip.put("amount_max", rideEstimate.getString("amount_max"));
                                        Main2Activity.this.trips.add(trip);
                                        Log.d(TAG, response.toString());
                                        Log.d(TAG, trips.toString());
                                    } catch (JSONException e) {
                                        e.printStackTrace();
                                    }
                                }
                            }, new Response.ErrorListener() {
                                @Override
                                public void onErrorResponse(VolleyError error) {
                                    Log.e(TAG, "abc");
                                    VolleyLog.d(TAG, "Error: " + error.getMessage());
                                }
                        }) {
                            @Override
                            public Map<String, String> getHeaders() throws AuthFailureError {
                                Map<String, String> headers = new HashMap<String, String>();
                                headers.put("X-APP-TOKEN", "93c6a2732e2e4988bdff3f4f45a38584");
                                headers.put("Content-Type", "application/json");
                                return headers;
                            }
                        }
                    );
        }
    }
}
