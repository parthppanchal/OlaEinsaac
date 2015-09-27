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
import java.util.Calendar;
import java.util.HashMap;

/**
 * Created by yash on 27/9/15.
 */
public class ItenararyAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    ArrayList<HashMap<String, String>> pointsOfInterest = new ArrayList<HashMap<String, String>>();
    ArrayList<UserData> userDataArrayList = null;
    Context context;
    ItenararyViewHolder myViewHolder = null;

    public ItenararyAdapter(Context context, ArrayList<HashMap<String, String>> pointsOfInterest, ArrayList<UserData> userDataArrayList) {
        this.context = context;
        this.pointsOfInterest = pointsOfInterest;
        this.userDataArrayList=userDataArrayList;
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
//        printList(userDataArrayList);
        View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.itenarary_element, viewGroup, false);
        myViewHolder = new ItenararyViewHolder(view);
        return myViewHolder;
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder viewHolder, int i) {
        ItenararyViewHolder vH = (ItenararyViewHolder) viewHolder;
//        vH.i_loc.setText(userDataArrayList.get(i).location);
        vH.i_loc.setText(pointsOfInterest.get(i).get("start_address"));
        Double dh = Double.parseDouble(pointsOfInterest.get(i).get("duration"));
        Double dm = dh % 60;
        dh /= 60;
        Calendar c =Calendar.getInstance();
        int hours = c.get(Calendar.HOUR);
        int minutes = c.get(Calendar.MINUTE);
//        d + currTime + userDataArrayList.ge;
        vH.i_time.setText(((((hours) * 60) + dh + minutes)/60) + " : " + (((hours) * 60) + dh + minutes)%60);
        vH.time2.setText(((((hours + userDataArrayList.get(i).time) * 60) + dh + minutes)/60) + " : " + (((hours + userDataArrayList.get(i).time) * 60) + dh + minutes)%60);
        vH.i_sno.setText("DESTINATION #" + i);
    }

    @Override
    public int getItemCount() {
        return pointsOfInterest.size();
    }


    public class ItenararyViewHolder extends RecyclerView.ViewHolder {

        TextView i_loc;
        TextView i_time;
        TextView i_sno;
        TextView time2;


        public ItenararyViewHolder(View itemView) {
            super(itemView);
            i_loc = (TextView) itemView.findViewById(R.id.i_locationvalue);
            i_time = (TextView) itemView.findViewById(R.id.i_timevalue);
            i_sno = (TextView) itemView.findViewById(R.id.i_sno);

            time2 = (TextView) itemView.findViewById(R.id.i_time2);
        }

    }
}

