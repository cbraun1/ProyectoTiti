package com.example.proyectotiti;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.RadioButton;

import com.example.proyectotiti.models.OldNewPair;
import com.example.proyectotiti.models.Structure;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class madera5 extends AppCompatActivity {

    private DatabaseReference mDatabase;
    private DatabaseReference visitsDatabase;

    private Integer family_no;
    private Long visit_num;

    private EditText stove_freq;
    private EditText stove_type;

    private Structure structure;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_madera5);

        // Get current Info
        Intent intentExtras = getIntent();
        Bundle extrasBundle = intentExtras.getExtras();
        //firstPass = extrasBundle.getBoolean("firstPass");
        family_no = extrasBundle.getInt("family_no");
        visit_num = extrasBundle.getLong("visit_num");

        // Views
        stove_freq = (EditText) findViewById(R.id.editTextFreq);
        stove_type = (EditText) findViewById(R.id.editTextStoveType);

        String visitID = "visit_" + visit_num;
        mDatabase = FirebaseDatabase.getInstance().getReference().child("families").child(String.valueOf(family_no)).child("curr_visit").child("structures");
        visitsDatabase = FirebaseDatabase.getInstance().getReference().child("families").child(String.valueOf(family_no)).child("visits").child(visitID).child("changes");
        readFromDB();
    }

    public void readFromDB(){
        // Add value event listener to the list of families
        ValueEventListener sListener = new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                Log.e("DEBUG", String.valueOf(dataSnapshot));
                Structure post = dataSnapshot.getValue(Structure.class);
                prepopulate(post);
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                // Getting Family failed, log a message
                Log.w("DEBUG", "loadPost:onCancelled", databaseError.toException());
            }
        };
        mDatabase.addValueEventListener(sListener);
    }

    public void prepopulate(Structure post){
        structure = post;
        stove_freq.setText(structure.stove_freq);
        stove_type.setText(structure.stove_type);

    }

    public void submitStructure(View v){
        if (!stove_freq.getText().toString().equals(structure.stove_freq) && visit_num != 0){
            OldNewPair new_pair = new OldNewPair(structure.stove_freq, stove_freq.getText().toString());
            visitsDatabase.child("curr_visit-structures-stove_freq").setValue(new_pair);
        }
        mDatabase.child("stove_freq").setValue(stove_freq.getText().toString());

        if (!stove_type.getText().toString().equals(structure.stove_type) && visit_num != 0){
            OldNewPair new_pair = new OldNewPair(structure.stove_type, stove_type.getText().toString());
            visitsDatabase.child("curr_visit-structures-stove_type").setValue(new_pair);
        }
        mDatabase.child("stove_type").setValue(stove_type.getText().toString());

        openRecycle1(v);
    }

    public void openMadera4(View v){
        Intent intentDetails = new Intent(madera5.this, madera4.class);
        Bundle bundle = new Bundle();
        bundle.putLong("visit_num", visit_num);
        bundle.putInt("family_no", family_no);
        //bundle.putBoolean("firstPass", true);
        intentDetails.putExtras(bundle);
        startActivity(intentDetails);
    }

    public void openRecycle1(View v){
        Intent intentDetails = new Intent(madera5.this, recycle1.class);
        Bundle bundle = new Bundle();
        bundle.putLong("visit_num", visit_num);
        bundle.putInt("family_no", family_no);
        //bundle.putBoolean("firstPass", true);
        intentDetails.putExtras(bundle);
        startActivity(intentDetails);
    }
}
