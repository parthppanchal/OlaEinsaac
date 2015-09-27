package com.ola.apithon.olaeinsaac;

import android.app.Activity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.widget.TextView;

import java.util.ArrayList;

/**
 * Created by yash on 27/9/15.
 */
public class Itenarary extends Activity {


    private RecyclerView mRecyclerView;
    private ItenararyAdapter mAdapter;
    private RecyclerView.LayoutManager mLayoutManager;
    private ArrayList<UserData> userDataArrayList = null;

    private TextView totalfare;
    private TextView totaldistance;
    private TextView totaltime;


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        userDataArrayList = (ArrayList<UserData>) getIntent().getSerializableExtra("ArrayList");

        setContentView(R.layout.itenarary_display);

        mRecyclerView = (RecyclerView) findViewById(R.id.itenarary_list);

        mRecyclerView.setHasFixedSize(true);
        mLayoutManager = new LinearLayoutManager(this);
        mRecyclerView.setLayoutManager(mLayoutManager);


        //mAdapter = new ItenararyAdapter(Itenarary.this, userDataArrayList);
        mRecyclerView.setAdapter(mAdapter);

        totalfare = (TextView) findViewById(R.id.textview1);
        totaldistance = (TextView) findViewById(R.id.textview2);
        totaltime = (TextView) findViewById(R.id.textview3);


        totalfare.setText("Rs. 10,000");
        totaldistance.setText("sjdhask");
        totaltime.setText("kjfdhskjf");
    }
}
