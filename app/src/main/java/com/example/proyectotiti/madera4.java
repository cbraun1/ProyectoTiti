package com.example.proyectotiti;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.RadioButton;
import android.widget.RadioGroup;

import com.example.proyectotiti.models.OldNewPair;
import com.example.proyectotiti.models.Structure;
import com.example.proyectotiti.models.StructureDesc;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class madera4 extends AppCompatActivity {

    private DatabaseReference mDatabase;
    private DatabaseReference visitsDatabase;

    private Integer family_no;
    private Long visit_num;

    private RadioButton radioButtonSi;
    private RadioButton radioButtonNo;

    private Structure structure;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_madera4);

        // Get current Info
        Intent intentExtras = getIntent();
        Bundle extrasBundle = intentExtras.getExtras();
        //firstPass = extrasBundle.getBoolean("firstPass");
        family_no = extrasBundle.getInt("family_no");
        visit_num = extrasBundle.getLong("visit_num");

        // Views
        radioButtonSi = (RadioButton) findViewById(R.id.radioButtonSi);
        radioButtonNo = (RadioButton) findViewById(R.id.radioButtonNo);

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
        if(post.cookWithWoodCoal){
            radioButtonSi.setChecked(true);
        }
        else{
            radioButtonNo.setChecked(true);
        }

    }

    public void submitStructure(View v){
        if(radioButtonSi.isChecked()){
            if (!structure.cookWithWoodCoal.equals(true) && visit_num != 0){
                OldNewPair new_pair = new OldNewPair("false", "true");
                visitsDatabase.child("curr_visit-structures-cookWithWoodCoal").setValue(new_pair);
            }
            mDatabase.child("cookWithWoodCoal").setValue(true);
            openMadera5(v);
        }
        else {
            if(radioButtonNo.isChecked()) {
                if (!structure.cookWithWoodCoal.equals(false) && visit_num != 0){
                    OldNewPair new_pair = new OldNewPair("true", "false");
                    visitsDatabase.child("curr_visit-structures-cookWithWoodCoal").setValue(new_pair);
                }
                mDatabase.child("cookWithWoodCoal").setValue(false);
                openRecycle1(v);
            }
        }
    }

    public void openMadera0(View v){
        Intent intentDetails = new Intent(madera4.this, madera0.class);
        Bundle bundle = new Bundle();
        bundle.putLong("visit_num", visit_num);
        bundle.putInt("family_no", family_no);
        //bundle.putBoolean("firstPass", true);
        intentDetails.putExtras(bundle);
        startActivity(intentDetails);
    }
    public void openMadera5(View v){

        Intent intentDetails = new Intent(madera4.this, madera5.class);
        Bundle bundle = new Bundle();
        bundle.putLong("visit_num", visit_num);
        bundle.putInt("family_no", family_no);
        //bundle.putBoolean("firstPass", true);
        intentDetails.putExtras(bundle);
        startActivity(intentDetails);
    }
    public void openRecycle1(View v){
        Intent intentDetails = new Intent(madera4.this, recycle1.class);
        Bundle bundle = new Bundle();
        bundle.putLong("visit_num", visit_num);
        bundle.putInt("family_no", family_no);
        //bundle.putBoolean("firstPass", true);
        intentDetails.putExtras(bundle);
        startActivity(intentDetails);
    }
}
