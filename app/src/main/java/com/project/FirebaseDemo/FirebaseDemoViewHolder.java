package com.project.FirebaseDemo;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;

/**
 * Created by chinmay on 6/3/17.
 */

public class FirebaseDemoViewHolder extends RecyclerView.ViewHolder {

    public TextView textView;

    public FirebaseDemoViewHolder(View itemView) {
        super(itemView);

        textView = (TextView)itemView.findViewById(R.id.tv_firebase_demo_text);
    }

    public void bindToPost(String demoData){
        textView.setText(demoData);
    }
}
