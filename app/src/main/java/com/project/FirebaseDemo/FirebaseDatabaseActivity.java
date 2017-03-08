package com.project.FirebaseDemo;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;

import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.google.firebase.FirebaseApp;
import com.google.firebase.FirebaseOptions;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.Map;

public class FirebaseDatabaseActivity extends AppCompatActivity {

    private RecyclerView rvMainList;
    public final static String firebaseURL = "https://fir-demo-65c25.firebaseio.com/";
    private FirebaseApp fApp;
    private FirebaseOptions fopts;
    private DemoData demoData;
    private FirebaseRecyclerAdapter<DemoData, FirebaseDemoAdapter.FirebaseDemoViewHolder> adapter;
    private ArrayList<String> listData = new ArrayList<>();


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_firebase_database);
        fopts = new FirebaseOptions.Builder().setDatabaseUrl(firebaseURL).setApplicationId("fir-demo-65c25").build();
        fApp = FirebaseApp.initializeApp(this, fopts, "firebase_demo");
        initUI();
        setData();
        getData();
//        updateData();
    }

    private void updateData() {
        FirebaseDatabase database = FirebaseDatabase.getInstance();
        DatabaseReference myref = database.getReference();
        adapter = new FirebaseDemoAdapter(DemoData.class, R.layout.item_text_row,
                FirebaseDemoAdapter.FirebaseDemoViewHolder.class, myref, this,listData);
        LinearLayoutManager mLinearLayoutManager = new LinearLayoutManager(this);
        mLinearLayoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        rvMainList.setHasFixedSize(true);
        rvMainList.setAdapter(adapter);
        rvMainList.setLayoutManager(mLinearLayoutManager);
        /*DemoData data = new DemoData();
        data.setName("Didi");
        data.setRegistrationNumber(1233);
        Map<String, Object> postValues = data.toMap();

        myref.child("demodata").updateChildren(postValues);*/
//        myref.updateChildren(updateValues);
    }


    /**
     * retrieve data from firebase
     **/
    private void getData() {

        final FirebaseDatabase database = FirebaseDatabase.getInstance();
        final DatabaseReference myref = database.getReference();
        final ValueEventListener listener = new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                DemoData demo = dataSnapshot.getValue(DemoData.class);
                listData = collectNames((Map<String,Object>) dataSnapshot.getValue());
                adapter = new FirebaseDemoAdapter(DemoData.class, R.layout.item_text_row,
                        FirebaseDemoAdapter.FirebaseDemoViewHolder.class, myref, FirebaseDatabaseActivity.this,listData);
                LinearLayoutManager mLinearLayoutManager = new LinearLayoutManager(FirebaseDatabaseActivity.this);
                mLinearLayoutManager.setOrientation(LinearLayoutManager.VERTICAL);
                rvMainList.setHasFixedSize(true);
                rvMainList.setAdapter(adapter);
                rvMainList.setLayoutManager(mLinearLayoutManager);
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        };
        myref.addValueEventListener(listener);
    }

    private ArrayList<String> collectNames(Map<String,Object> users) {

        ArrayList<String> phoneNumbers = new ArrayList<>();

        //iterate through each user, ignoring their UID
        for (Map.Entry<String, Object> entry : users.entrySet()){

            //Get user map
            Map singleUser = (Map) entry.getValue();
            //Get phone field and append to list
            phoneNumbers.add((String) singleUser.get("name"));
        }
        Log.i("frost", "collectNames: "+phoneNumbers.size());
        return phoneNumbers;
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
        myRef.child("demodata").setValue(demoData);
        myRef.push();
    }

    private void initUI() {
        rvMainList = (RecyclerView) findViewById(R.id.rv_main_list);

    }


}
