package com.example.proyectotiti;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;

import com.example.proyectotiti.models.OldNewPair;
import com.example.proyectotiti.models.Recycle;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class recycle3 extends AppCompatActivity {

    private DatabaseReference mDatabase;
    private DatabaseReference visitsDatabase;

    private Integer family_no;
    private Long visit_num;

    private EditText waste_man;

    private Recycle recycle;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_recycle3);

        // Get current Info
        Intent intentExtras = getIntent();
        Bundle extrasBundle = intentExtras.getExtras();
        //firstPass = extrasBundle.getBoolean("firstPass");
        family_no = extrasBundle.getInt("family_no");
        visit_num = extrasBundle.getLong("visit_num");

        // Views
        waste_man = (EditText) findViewById(R.id.editText2);

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
        waste_man.setText(recycle.waste_man);

    }

    public void submitRecycle(View v){
        if (!waste_man.getText().toString().equals(recycle.waste_man) && visit_num != 0){
            OldNewPair new_pair = new OldNewPair(recycle.waste_man, waste_man.getText().toString());
            visitsDatabase.child("curr_visit-recycle-waste_man").setValue(new_pair);
        }
        mDatabase.child("waste_man").setValue(waste_man.getText().toString());

        openRevise(v);
    }

    public void openRecycle1(View v){

        Intent intentDetails = new Intent(recycle3.this, recycle1.class);
        Bundle bundle = new Bundle();
        bundle.putLong("visit_num", visit_num);
        bundle.putInt("family_no", family_no);
        //bundle.putBoolean("firstPass", true);
        intentDetails.putExtras(bundle);
        startActivity(intentDetails);
    }
    public void openRevise(View v){
        Intent intentDetails = new Intent(recycle3.this, revise.class);
        Bundle bundle = new Bundle();
        bundle.putLong("visit_num", visit_num);
        bundle.putInt("family_no", family_no);
        //bundle.putBoolean("firstPass", true);
        intentDetails.putExtras(bundle);
        startActivity(intentDetails);
    }
}
