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

import java.util.Map;

public class FirebaseDatabaseActivity extends AppCompatActivity {

    private RecyclerView rvMainList;
    public final static String firebaseURL = "https://fir-demo-65c25.firebaseio.com/";
    private FirebaseApp fApp;
    private FirebaseOptions fopts;
    private DemoData demoData;
    private FirebaseRecyclerAdapter<DemoData, FirebaseDemoViewHolder> adapter;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_firebase_database);
        fopts = new FirebaseOptions.Builder().setDatabaseUrl(firebaseURL).setApplicationId("fir-demo-65c25").build();
        fApp = FirebaseApp.initializeApp(this, fopts, "firebase_demo");
        initUI();
//        setData();
        getData();
        populateUIInRecyclerView();
    }

    private void populateUIInRecyclerView() {
        FirebaseDatabase db = FirebaseDatabase.getInstance();
        DatabaseReference mDatabaseReference = db.getReference();
        Log.i("frost", "populateUIInRecyclerView: "+mDatabaseReference.child("demodata").child("name").getRef());
         adapter = new FirebaseRecyclerAdapter<DemoData, FirebaseDemoViewHolder>(
                DemoData.class,
                R.layout.item_text_row,
                FirebaseDemoViewHolder.class,
                //referencing the node where we want the database to store the data from our Object
                 mDatabaseReference
        ) {
            @Override
            protected void populateViewHolder(FirebaseDemoViewHolder viewHolder, DemoData model, int position) {
                viewHolder.textView.setText(model.getName());
             }
        };

        adapter.registerAdapterDataObserver(new RecyclerView.AdapterDataObserver() {
            @Override
            public void onChanged() {
                super.onChanged();
            }

            @Override
            public void onItemRangeInserted(int positionStart, int itemCount) {
                super.onItemRangeInserted(positionStart, itemCount);
                Log.d("frost", "onItemRangeInserted: "+adapter.getItemCount());
            }
        });

        rvMainList.setAdapter(adapter);
        Log.i("frost", "populateUIInRecyclerView: adapter "+adapter.getItemCount());
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
        myRef.child("demodata2").setValue(demoData);
        myRef.push();
    }

    private void initUI() {
        rvMainList = (RecyclerView) findViewById(R.id.rv_main_list);
        LinearLayoutManager mLinearLayoutManager = new LinearLayoutManager(this);
        mLinearLayoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        rvMainList.setHasFixedSize(true);
        rvMainList.setLayoutManager(mLinearLayoutManager);
    }


}
