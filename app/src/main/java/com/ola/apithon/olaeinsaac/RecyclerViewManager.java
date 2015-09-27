package com.ola.apithon.olaeinsaac;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.PersistableBundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.Button;

import java.util.ArrayList;

/**
 * Created by yash on 27/9/15.
 */
public class RecyclerViewManager extends Activity implements View.OnClickListener{

    private RecyclerView mRecyclerView;
    private RecyclerViewAdapter mAdapter;
    private RecyclerView.LayoutManager mLayoutManager;
    private ArrayList<UserData> userDataArrayList = null;
    private Button button_book_cab;

    public RecyclerViewManager() {
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        userDataArrayList = (ArrayList<UserData>) getIntent().getSerializableExtra("ArrayList");

        setContentView(R.layout.activity_main);

        mRecyclerView = (RecyclerView) findViewById(R.id.my_recycler_view);
        button_book_cab =(Button) findViewById(R.id.book_cab);

        button_book_cab.setOnClickListener(this);

        mRecyclerView.setHasFixedSize(true);
        mLayoutManager = new LinearLayoutManager(this);
        mRecyclerView.setLayoutManager(mLayoutManager);

        mAdapter = new RecyclerViewAdapter(RecyclerViewManager.this, userDataArrayList);
        mRecyclerView.setAdapter(mAdapter);
    }

    @Override
    public void onClick(View v) {
        Intent intent = new Intent(this, Itenarary.class);
        //Bundle bundle =new Bundle();
        //bundle.putSerializable("ArrayList",(Serializable) userDataArrayList);
        intent.putExtra("ArrayList",userDataArrayList);
        //intent.putExtras(bundle);
        startActivity(intent);
    }
}
