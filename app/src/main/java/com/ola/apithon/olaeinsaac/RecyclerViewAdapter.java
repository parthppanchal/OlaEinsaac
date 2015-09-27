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
public class RecyclerViewAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    ArrayList<UserData> userDataArrayList = null;
    Context context;
    MyViewHolder myViewHolder = null;

    public RecyclerViewAdapter(Context context, ArrayList<UserData> userDataArrayList) {
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
        View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.recycler_view_element, viewGroup, false);
        myViewHolder = new MyViewHolder(view);
        return myViewHolder;
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder viewHolder, int i) {
        MyViewHolder vH = (MyViewHolder) viewHolder;
        vH.loc.setText(userDataArrayList.get(i).location);
        vH.time.setText("" + userDataArrayList.get(i).time);
        if (i == 0)
            vH.dest.setText("SOURCE");
        else if (i == getItemCount() - 1)
            vH.dest.setText("DESTINATION");
        else
            vH.dest.setText("STOP #" + i);
    }

    @Override
    public int getItemCount() {
        return userDataArrayList.size();
    }

//    @Override
//    public void onClick(View v) {
//        //if (v==myViewHolder.cross){
//        removeAt(myViewHolder.getAdapterPosition());
//        // }
//    }

//    public void removeAt(int position) {
//
//        userDataArrayList.remove(position);
////        notifyDataSetChanged();
//        notifyItemRemoved(position);
////        notifyAll();
//        Log.d("Array", getItemCount() + "");
//        //      notifyItemRangeChanged(position, userDataArrayList.size());
//    }

    public class MyViewHolder extends RecyclerView.ViewHolder {

        TextView loc;
        TextView time;
        TextView dest;

        TextView time2;
        Button cross;

        public MyViewHolder(View itemView) {
            super(itemView);
            loc = (TextView) itemView.findViewById(R.id.rv_loc);
            time = (TextView) itemView.findViewById(R.id.rv_time);
            cross = (Button) itemView.findViewById(R.id.rv_cross);
            dest = (TextView) itemView.findViewById(R.id.rv_dest);
            time2 = (TextView) itemView.findViewById(R.id.i_time2);

        }

    }
}
