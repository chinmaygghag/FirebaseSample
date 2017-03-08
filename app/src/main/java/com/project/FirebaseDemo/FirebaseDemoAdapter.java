package com.project.FirebaseDemo;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.google.firebase.database.Query;

import java.util.ArrayList;

/**
 * Created by chinmay on 3/3/17.
 */

public class FirebaseDemoAdapter extends FirebaseRecyclerAdapter<DemoData, FirebaseDemoAdapter.FirebaseDemoViewHolder> {

   private ArrayList<String> listData;


    /**
     * @param modelClass      Firebase will marshall the data at a location into an instance of a class that you provide
     * @param modelLayout     This is the layout used to represent a single item in the list. You will be responsible for populating an
     *                        instance of the corresponding view with the data from an instance of modelClass.
     * @param viewHolderClass The class that hold references to all sub-views in an instance modelLayout.
     * @param ref             The Firebase location to watch for data changes. Can also be a slice of a location, using some
     *                        combination of {@code limit()}, {@code startAt()}, and {@code endAt()}.
     */
    public FirebaseDemoAdapter(Class<DemoData> modelClass, int modelLayout, Class<FirebaseDemoViewHolder> viewHolderClass, Query ref, Context context, ArrayList<String> listData) {
        super(modelClass, modelLayout, viewHolderClass, ref);
        this.listData = listData;
        Log.i("frost", "FirebaseDemoAdapter: "+listData.size());
    }

    @Override
    public FirebaseDemoViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_text_row,parent,false);
        return new FirebaseDemoViewHolder(view);
    }

    @Override
    protected void populateViewHolder(FirebaseDemoViewHolder viewHolder, DemoData model, int position) {
        Log.i("frost", "populateViewHolder: ");
        viewHolder.tvTest.setText(model.getName());
    }


    @Override
    public int getItemCount() {
        Log.i("frost", "getItemCount: "+listData.size());
        return listData.size();
     }

    public class FirebaseDemoViewHolder extends RecyclerView.ViewHolder {

        public TextView tvTest;

        public FirebaseDemoViewHolder(View itemView) {
            super(itemView);
            tvTest = (TextView)itemView.findViewById(R.id.tv_firebase_demo_text);
        }
    }
}
