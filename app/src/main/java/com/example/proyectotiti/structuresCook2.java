package com.example.proyectotiti;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;

import com.example.proyectotiti.models.Structure;
import com.example.proyectotiti.models.Visit;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class structuresCook2 extends AppCompatActivity {

    private DatabaseReference mDatabase;

    // Passed from last screen
    private String familyNum;
    private String visitNum;

    // Views
    private EditText stove_freq;
    private EditText stove_type;

    private Structure structure;
    private Class nextField;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_structures_cook2);

        // Get current Info
        Intent intentExtras = getIntent();
        Bundle extrasBundle = intentExtras.getExtras();
        familyNum = extrasBundle.getString("familyNum");
        visitNum = extrasBundle.getString("visitNum");

        // Views
        stove_freq = (EditText) findViewById(R.id.editTextFreq);
        stove_type = (EditText) findViewById(R.id.editTextStoveType);

        mDatabase = FirebaseDatabase.getInstance().getReference().child("families").child(familyNum).child("visits").child("visit"+visitNum);
        readFromDB();
    }

    public void readFromDB() {

        // Add value event listener to the list of families
        ValueEventListener bdListener = new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                Log.e("DEBUG", String.valueOf(dataSnapshot));
                Visit post = dataSnapshot.getValue(Visit.class);
                if(post != null){
                    prepopulate(post.structures);
                    if(post.recycle.committed){
                        nextField = recycle1.class;
                    }
                    else if(post.conservation.committed){
                        nextField = conservacion1.class;
                    }
                    else{
                        nextField = visitOverview.class;
                    }
                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                // Getting Family failed, log a message
                Log.w("DEBUG", "loadPost:onCancelled", databaseError.toException());
            }
        };
        mDatabase.addValueEventListener(bdListener);

    }

    public void prepopulate(Structure post){
        structure = post;
        stove_freq.setText(structure.stove_freq);
        stove_type.setText(structure.stove_type);

    }

    public void submitStructure(View v){
        mDatabase.child("structures").child("stove_freq").setValue(stove_freq.getText().toString());
        mDatabase.child("structures").child("stove_type").setValue(stove_type.getText().toString());

        openNextField(v);
    }

    public void openMadera4(View v){
        Intent intentDetails = new Intent(structuresCook2.this, structuresCook.class);
        Bundle bundle = new Bundle();
        bundle.putString("visitNum", visitNum);
        bundle.putString("familyNum", familyNum);
        intentDetails.putExtras(bundle);
        startActivity(intentDetails);
    }

    public void openNextField(View v){
        Intent intentDetails = new Intent(structuresCook2.this, nextField);
        Bundle bundle = new Bundle();
        bundle.putString("familyNum", familyNum);
        bundle.putString("visitNum", visitNum);
        intentDetails.putExtras(bundle);
        startActivity(intentDetails);

    }
}
