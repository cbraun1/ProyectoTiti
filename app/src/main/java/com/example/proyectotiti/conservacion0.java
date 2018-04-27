package com.example.proyectotiti;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;

import com.example.proyectotiti.models.Visit;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class conservacion0 extends AppCompatActivity {

    private static final String TAG = "conservacion0";

    private DatabaseReference mDatabase;


    private String familyNum;
    private String visitNum;

    private Class nextField;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_conservacion0);

        // Get current Info
        Intent intentExtras = getIntent();
        Bundle extrasBundle = intentExtras.getExtras();
        familyNum = extrasBundle.getString("familyNum");
        visitNum = extrasBundle.getString("visitNum");

        mDatabase = FirebaseDatabase.getInstance().getReference().child("families").child(familyNum).child("visits");

        readFromDB();
    }

    public void readFromDB() {


        // Add value event listener to the list of families
        ValueEventListener visitListener = new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                Log.e(TAG, String.valueOf(dataSnapshot));
                Visit post = dataSnapshot.getValue(Visit.class);
                if(post != null){
                    if(post.recycle.committed){
                        nextField = recycle1.class;
                    }
                    else if(post.structures.committed){
                        nextField = structuresHome.class;
                    }
                    else if(post.animals.committed){
                        nextField = animalsHome.class;
                    }
                    else{
                        nextField = basicData.class;
                    }

                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                // Getting Family failed, log a message
                Log.w(TAG, "loadPost:onCancelled", databaseError.toException());
            }
        };
        mDatabase.child("visit"+visitNum).addValueEventListener(visitListener);

    }

    public void openLastField(View v){

            Intent intentDetails = new Intent(conservacion0.this, nextField);
            Bundle bundle = new Bundle();
            bundle.putString("familyNum", familyNum);
            bundle.putString("visitNum", visitNum);
            intentDetails.putExtras(bundle);
            startActivity(intentDetails);


    }

    public void openConservacion1(View v){
        // Pass the id of the family selected to the new activity
        // Pass false to initial visit flag
        Intent intentDetails = new Intent(conservacion0.this, conservacion1.class);
        Bundle bundle = new Bundle();
        bundle.putString("familyNum", familyNum);
        bundle.putString("visitNum", visitNum);
        intentDetails.putExtras(bundle);
        startActivity(intentDetails);
    }
}
