package com.project.FirebaseDemo.fcm;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;

import com.google.firebase.iid.FirebaseInstanceId;
import com.project.FirebaseDemo.R;

public class FCMActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_fcm);
        Log.i("frost", "onCreate: "+FirebaseInstanceId.getInstance().getToken());
    }
}
