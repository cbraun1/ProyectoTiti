package com.example.proyectotiti;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;

import com.example.proyectotiti.models.OldNewPair;
import com.example.proyectotiti.models.Recycle;
import com.example.proyectotiti.models.Structure;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class recycle2 extends AppCompatActivity {

    private DatabaseReference mDatabase;
    private DatabaseReference visitsDatabase;

    private String familyNum;
    private String visitNum;

    private EditText recycle_items;
    private EditText recycle_deliver;

    private Recycle recycle;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_recycle2);

        // Get current Info
        Intent intentExtras = getIntent();
        Bundle extrasBundle = intentExtras.getExtras();
        familyNum = extrasBundle.getString("familyNum");
        visitNum = extrasBundle.getString("visitNum");

        // Views
        recycle_items = (EditText) findViewById(R.id.editTextRecycled);
        recycle_deliver = (EditText) findViewById(R.id.editTextRecyclingQ3);

        mDatabase = FirebaseDatabase.getInstance().getReference().child("families").child(familyNum).child("visits").child("visit"+visitNum).child("recycle");
        if(!visitNum.equals("1")){
            readFromDB();
        }
    }

    public void readFromDB(){
        // Add value event listener to the list of families
        ValueEventListener rListener = new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                Log.e("DEBUG", String.valueOf(dataSnapshot));
                Recycle post = dataSnapshot.getValue(Recycle.class);
                prepopulate(post);
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                // Getting Family failed, log a message
                Log.w("DEBUG", "loadPost:onCancelled", databaseError.toException());
            }
        };
        mDatabase.addValueEventListener(rListener);
    }

    public void prepopulate(Recycle post){
        recycle = post;
        recycle_items.setText(recycle.recycle_items);
        recycle_deliver.setText(recycle.recycle_deliver);

    }

    public void submitRecycle(View v){
        mDatabase.child("recycle_items").setValue(recycle_items.getText().toString());
        mDatabase.child("recycle_deliver").setValue(recycle_deliver.getText().toString());

        openConservacion0(v);
    }

    public void openRecycle1(View v){

        Intent intentDetails = new Intent(recycle2.this, recycle1.class);
        Bundle bundle = new Bundle();
        bundle.putString("visitNum", visitNum);
        bundle.putString("familyNum", familyNum);
        intentDetails.putExtras(bundle);
        startActivity(intentDetails);
    }
    public void openConservacion0(View v){
        Intent intentDetails = new Intent(recycle2.this, conservaion0.class);
        Bundle bundle = new Bundle();
        bundle.putString("visitNum", visitNum);
        bundle.putString("familyNum", familyNum);
        intentDetails.putExtras(bundle);
        startActivity(intentDetails);
    }
}
