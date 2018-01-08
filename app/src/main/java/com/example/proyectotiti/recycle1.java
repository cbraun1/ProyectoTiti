package com.example.proyectotiti;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.RadioButton;

import com.example.proyectotiti.models.OldNewPair;
import com.example.proyectotiti.models.Recycle;
import com.example.proyectotiti.models.Structure;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class recycle1 extends AppCompatActivity {

    private DatabaseReference mDatabase;
    private DatabaseReference visitsDatabase;

    private Integer family_no;
    private Long visit_num;

    private RadioButton radioButtonSi;
    private RadioButton radioButtonNo;

    private Recycle recycle;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_recycle1);

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
        mDatabase = FirebaseDatabase.getInstance().getReference().child("families").child(String.valueOf(family_no)).child("curr_visit").child("recycle");
        visitsDatabase = FirebaseDatabase.getInstance().getReference().child("families").child(String.valueOf(family_no)).child("visits").child(visitID).child("changes");
        readFromDB();

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
        if(post.doRecycle){
            radioButtonSi.setChecked(true);
        }
        else{
            radioButtonNo.setChecked(true);
        }

    }

    public void submitRecycle(View v){
        if(radioButtonSi.isChecked()){
            if (!recycle.doRecycle.equals(true) && visit_num != 0){
                OldNewPair new_pair = new OldNewPair("false", "true");
                visitsDatabase.child("curr_visit-recycle-doRecycle").setValue(new_pair);
            }
            mDatabase.child("doRecycle").setValue(true);
            openRecycle2(v);
        }
        else {
            if(radioButtonNo.isChecked()) {
                if (!recycle.doRecycle.equals(false)){
                    OldNewPair new_pair = new OldNewPair("true", "false");
                    visitsDatabase.child("curr_visit-recycle-doRecycle").setValue(new_pair);
                }
                mDatabase.child("doRecycle").setValue(false);
                openRecycle3(v);
            }
        }
    }

    public void openMadera0(View v){
        Intent intentDetails = new Intent(recycle1.this, madera0.class);
        Bundle bundle = new Bundle();
        bundle.putLong("visit_num", visit_num);
        bundle.putInt("family_no", family_no);
        //bundle.putBoolean("firstPass", true);
        intentDetails.putExtras(bundle);
        startActivity(intentDetails);
    }
    public void openRecycle2(View v){

        Intent intentDetails = new Intent(recycle1.this, recycle2.class);
        Bundle bundle = new Bundle();
        bundle.putLong("visit_num", visit_num);
        bundle.putInt("family_no", family_no);
        //bundle.putBoolean("firstPass", true);
        intentDetails.putExtras(bundle);
        startActivity(intentDetails);
    }
    public void openRecycle3(View v){
        Intent intentDetails = new Intent(recycle1.this, recycle3.class);
        Bundle bundle = new Bundle();
        bundle.putLong("visit_num", visit_num);
        bundle.putInt("family_no", family_no);
        //bundle.putBoolean("firstPass", true);
        intentDetails.putExtras(bundle);
        startActivity(intentDetails);
    }}
