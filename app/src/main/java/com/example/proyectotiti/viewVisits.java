package com.example.proyectotiti;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class viewVisits extends AppCompatActivity {

    private static final String TAG = "viewVisits";

    private String familyNum;
    private String visitNum;
    private DatabaseReference mDatabase;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_visits);

        // Get current Info
        Intent intentExtras = getIntent();
        Bundle extrasBundle = intentExtras.getExtras();
        familyNum = extrasBundle.getString("familyNum");
    }

    public void getNewVisitNumber(){
        // Add value event listener to find the visit number
        ValueEventListener visitListener = new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                Log.e(TAG, String.valueOf(dataSnapshot));
                long numOfVisits = dataSnapshot.getChildrenCount();
                numOfVisits = numOfVisits + 1;
                visitNum = String.valueOf(numOfVisits);

                // Pass the id of the family selected to the new activity
                Intent intentDetails = new Intent(viewVisits.this, basicData.class);
                Bundle bundle = new Bundle();
                bundle.putString("familyNum", familyNum);
                bundle.putString("visitNum", visitNum);
                intentDetails.putExtras(bundle);
                startActivity(intentDetails);

            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                // Getting Family failed, log a message
                Log.w("DEBUG", "loadPost:onCancelled", databaseError.toException());
            }
        };
        mDatabase.child("visits").addListenerForSingleValueEvent(visitListener);
    }
    public void startNewVisit(View v){
        mDatabase = FirebaseDatabase.getInstance().getReference().child("families").child(familyNum);
        // Get next visit number
        getNewVisitNumber();

    }
}
