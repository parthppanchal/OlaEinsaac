package com.ola.apithon.olaeinsaac;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import java.util.ArrayList;

/**
 * Created by yash on 27/9/15.
 */
public class ItenararyAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    ArrayList<UserData> userDataArrayList = null;
    Context context;
    ItenararyViewHolder myViewHolder = null;

    public ItenararyAdapter(Context context, ArrayList<UserData> userDataArrayList) {
        this.context = context;
        this.userDataArrayList = userDataArrayList;
    }

    public void printList(ArrayList<UserData> userDataArrayList) {
        if (userDataArrayList != null) {
            for (int i = 0; i < userDataArrayList.size(); i++) {
                Log.d("Array", userDataArrayList.get(i).location);
                Log.d("Array", userDataArrayList.get(i).time + "");
            }
        } else {
            Log.d("Array", "Data is null.");
        }
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup viewGroup, int i) {
        Log.d("iamhere", "sdnf");
        printList(userDataArrayList);
        View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.itenarary_element, viewGroup, false);
        myViewHolder = new ItenararyViewHolder(view);
        return myViewHolder;
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder viewHolder, int i) {
        ItenararyViewHolder vH = (ItenararyViewHolder) viewHolder;
        vH.i_loc.setText(userDataArrayList.get(i).location);
        vH.i_time.setText("" + userDataArrayList.get(i).time);
        vH.i_sno.setText("DESTINATION #" + i);
    }

    @Override
    public int getItemCount() {
        return userDataArrayList.size();
    }


    public class ItenararyViewHolder extends RecyclerView.ViewHolder {

        TextView i_loc;
        TextView i_time;
        TextView i_sno;

        public ItenararyViewHolder(View itemView) {
            super(itemView);
            i_loc= (TextView) itemView.findViewById(R.id.i_locationvalue);
            i_time= (TextView) itemView.findViewById(R.id.i_timevalue);
            i_sno= (TextView) itemView.findViewById(R.id.i_sno);

        }

    }
}

