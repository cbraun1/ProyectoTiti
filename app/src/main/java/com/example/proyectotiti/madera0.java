package com.example.proyectotiti;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.RadioGroup;

import com.example.proyectotiti.models.Structure;
import com.example.proyectotiti.models.StructureDesc;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.Map;

public class madera0 extends AppCompatActivity {

    private DatabaseReference mDatabase;

    private Integer family_no;
    //private Boolean firstPass;
    private Long visit_num;

    private RadioGroup conRdGp;
    private RadioGroup fenceRdGp;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_madera0);

        // Set up Radio Button Gorups with Linear Layout
        LinearLayout conLinearLayout = (LinearLayout) findViewById(R.id.linear_con);
        LinearLayout fenceLinearLayout = (LinearLayout) findViewById(R.id.linear_fence);
        conRdGp = new RadioGroup(this);
        fenceRdGp = new RadioGroup(this);
        conRdGp.setOrientation(RadioGroup.VERTICAL);
        fenceRdGp.setOrientation(RadioGroup.VERTICAL);

        // Get current Info
        Intent intentExtras = getIntent();
        Bundle extrasBundle = intentExtras.getExtras();
        //firstPass = extrasBundle.getBoolean("firstPass");
        family_no = extrasBundle.getInt("family_no");
        visit_num = extrasBundle.getLong("visit_num");
        mDatabase = FirebaseDatabase.getInstance().getReference().child("families").child(String.valueOf(family_no)).child("curr_visit").child("structures");

        readFromDB();

        conLinearLayout.addView(conRdGp);
        fenceLinearLayout.addView(fenceRdGp);
    }

    public void readFromDB() {

        // Add value event listener to the list of families
        ValueEventListener bdListener = new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                Log.e("DEBUG", String.valueOf(dataSnapshot));
                Structure post = dataSnapshot.getValue(Structure.class);
                if(post != null){
                    prepopulate(post);
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

    public void prepopulate(Structure structure){
        // Iterate through wild animals and prepopulate
        if(structure.construction != null){
            Log.e("DEBUG", String.valueOf(structure.construction));
            for (Map.Entry<String, StructureDesc> entry : structure.construction.entrySet()) {
                Log.e("DEBUG", String.valueOf(entry));
                String key = entry.getKey();
                StructureDesc value = entry.getValue();
                if (value != null) {
                    addConRadioButton(key,value);
                }
            }
        }
        // Iterate through domestic animals and prepopulate
        if(structure.fence != null) {
            for (Map.Entry<String, StructureDesc> entry : structure.fence.entrySet()) {
                String key = entry.getKey();
                StructureDesc value = entry.getValue();
                if (value != null) {
                    addFenceRadioButton(key,value);
                }
            }
        }
    }

    // Add new animal as a radio button with the text as the animal name and the id as the id
    public void addConRadioButton(String key, StructureDesc value) {
        if(value.active == true){
            RadioButton rdbtn = new RadioButton(this);
            String[] s = key.split("_");
            int id = Integer.valueOf(s[1]);
            rdbtn.setId(id);
            rdbtn.setText(value.name);
            conRdGp.addView(rdbtn);
        }
    }

    // Add new animal as a radio button with the text as the animal name and the id as the id
    public void addFenceRadioButton(String key, StructureDesc value) {
        if(value.active == true){
            RadioButton rdbtn = new RadioButton(this);
            String[] s = key.split("_");
            int id = Integer.valueOf(s[1]);
            rdbtn.setId(id);
            rdbtn.setText(value.name);
            fenceRdGp.addView(rdbtn);
        }
    }

    public void deleteConStructure(View v){
        int selectedId = conRdGp.getCheckedRadioButtonId();
        if(selectedId != -1){
            String id = "s_" + selectedId;
            DatabaseReference dDatabase = FirebaseDatabase.getInstance().getReference().child("families").child(String.valueOf(family_no)).child("curr_visit").child("structures").child("construction").child(id);
            dDatabase.child("active").setValue(false);
            conRdGp.removeAllViews();
            fenceRdGp.removeAllViews();
            conRdGp.clearCheck();
        }
    }

    public void deleteFenceStructure(View v){
        int selectedId = fenceRdGp.getCheckedRadioButtonId();
        if(selectedId != -1){
            String id = "s_" + selectedId;
            DatabaseReference dDatabase = FirebaseDatabase.getInstance().getReference().child("families").child(String.valueOf(family_no)).child("curr_visit").child("structures").child("fence").child(id);
            dDatabase.child("active").setValue(false);
            conRdGp.removeAllViews();
            fenceRdGp.removeAllViews();
            fenceRdGp.clearCheck();
        }
    }

    public void openMadera4(View v){

        Intent intentDetails = new Intent(madera0.this, madera4.class);
        Bundle bundle = new Bundle();
        bundle.putLong("visit_num", visit_num);
        bundle.putInt("family_no", family_no);
        //bundle.putBoolean("firstPass", true);
        intentDetails.putExtras(bundle);
        startActivity(intentDetails);
    }

    public void openMadera2(View v){

        int selectedId = conRdGp.getCheckedRadioButtonId();
        Log.e("DEBUG", String.valueOf(selectedId));
        Log.e("DEBUG", String.valueOf(visit_num));
        Log.e("DEBUG", String.valueOf(family_no));

        Intent intentDetails = new Intent(madera0.this, madera2.class);
        Bundle bundle = new Bundle();
        bundle.putLong("visit_num", visit_num);
        bundle.putInt("family_no", family_no);
        bundle.putInt("structure_no", selectedId);
        intentDetails.putExtras(bundle);
        startActivity(intentDetails);
    }
    public void openMadera3(View v){
        int selectedId = fenceRdGp.getCheckedRadioButtonId();
        Log.e("DEBUG", String.valueOf(selectedId));
        Log.e("DEBUG", String.valueOf(visit_num));
        Log.e("DEBUG", String.valueOf(family_no));

        Intent intentDetails = new Intent(madera0.this, madera3.class);
        Bundle bundle = new Bundle();
        bundle.putLong("visit_num", visit_num);
        bundle.putInt("family_no", family_no);
        bundle.putInt("structure_no", selectedId);
        intentDetails.putExtras(bundle);
        startActivity(intentDetails);
    }
    public void openAnimals0(View v){

        Intent intentDetails = new Intent(madera0.this, animalsHome.class);
        Bundle bundle = new Bundle();
        bundle.putLong("visit_num", visit_num);
        bundle.putInt("family_no", family_no);
        //bundle.putBoolean("firstPass", true);
        intentDetails.putExtras(bundle);
        startActivity(intentDetails);
    }
}
