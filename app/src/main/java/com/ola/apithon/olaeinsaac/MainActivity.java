package com.ola.apithon.olaeinsaac;

import android.app.Activity;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import java.io.Serializable;
import java.util.ArrayList;

public class MainActivity extends Activity implements View.OnClickListener {

    Button form_add;
    Button form_next;
    EditText form_loc;
    EditText form_time;
    ArrayList<UserData> userDataArrayList = null;
    UserData temp = null;
    int x;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.form);

        userDataArrayList = new ArrayList<>();
        x = 1;

        form_add = (Button) findViewById(R.id.form_add);
        form_next = (Button) findViewById(R.id.form_next);
        form_loc = (EditText) findViewById(R.id.form_loc);
        form_time = (EditText) findViewById(R.id.form_time);

        form_add.setOnClickListener(this);
        form_next.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        if (v.getId() == R.id.form_add) {
            temp= new UserData();
            if (form_loc !=null)
                temp.location = form_loc.getText().toString();
            else
                Toast.makeText(this, "Enter a valid location", Toast.LENGTH_SHORT).show();

            if (form_time!=null && form_time.getText().toString().matches("\\d+"))
                temp.time = Integer.parseInt(form_time.getText().toString());
            else
                Toast.makeText(this, "Enter a valid time duration (in hours)", Toast.LENGTH_SHORT).show();

            userDataArrayList.add(temp);
            Toast.makeText(this, "Number of Locations added: " + x++, Toast.LENGTH_SHORT).show();

            form_loc.setText("");
            form_time.setText("");

        } else if (v.getId() == R.id.form_next) {
            Intent intent = new Intent(this, RecyclerViewManager.class);
            //Bundle bundle =new Bundle();
            //bundle.putSerializable("ArrayList",(Serializable) userDataArrayList);
            intent.putExtra("ArrayList",userDataArrayList);
            //intent.putExtras(bundle);
            startActivity(intent);
        }
    }
}
