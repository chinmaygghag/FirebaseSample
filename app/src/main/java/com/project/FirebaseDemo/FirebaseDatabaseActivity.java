package com.project.FirebaseDemo;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.RecyclerView;
import android.util.Log;

import com.google.firebase.FirebaseApp;
import com.google.firebase.FirebaseOptions;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.HashMap;
import java.util.Map;

public class FirebaseDatabaseActivity extends AppCompatActivity {

    private RecyclerView rvMainList;
    public final static String firebaseURL = "https://fir-demo-65c25.firebaseio.com/";
    private FirebaseApp fApp;
    private FirebaseOptions fopts;
    private DemoData demoData;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_firebase_database);
        fopts = new FirebaseOptions.Builder().setDatabaseUrl(firebaseURL).setApplicationId("fir-demo-65c25").build();
        fApp = FirebaseApp.initializeApp(this, fopts, "firebase_demo");
        initUI();
        setData();
        getData();
        updateData();
    }

    private void updateData() {



        FirebaseDatabase database = FirebaseDatabase.getInstance();
        DatabaseReference myref = database.getReference();

        DemoData data = new DemoData();
        data.setName("Didi");
        data.setRegistrationNumber(1233);
        Map<String, Object> postValues = data.toMap();

        myref.child("demodata").updateChildren(postValues);
//        myref.updateChildren(updateValues);
    }


    /**
     * retrieve data from firebase
     **/
    private void getData() {
        FirebaseDatabase database = FirebaseDatabase.getInstance();
        DatabaseReference myref = database.getReference("demodata");
        ValueEventListener listener = new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                DemoData demo = dataSnapshot.getValue(DemoData.class);
                Log.i("frost", "onDataChange: " + dataSnapshot.getKey());

            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        };
        myref.addValueEventListener(listener);
    }

    /**
     * set data to firebase
     ***/
    private void setData() {
        FirebaseDatabase database = FirebaseDatabase.getInstance();
        demoData = new DemoData();
        demoData.setName("abc");
        demoData.setRegistrationNumber(123);
        DatabaseReference myRef = database.getReference();
        DatabaseReference mRef1 = database.getReference();
        mRef1.child("test").setValue("Hello World!");
        mRef1.push();
        myRef.child("demodata").setValue(demoData);
        myRef.push();
    }

    private void initUI() {
        rvMainList = (RecyclerView) findViewById(R.id.rv_main_list);
    }


}
