package com.example.proyectotiti;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Switch;

import com.example.proyectotiti.models.Structure;
import com.example.proyectotiti.models.StructureDesc;
import com.example.proyectotiti.models.Visit;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.Map;

public class madera0 extends AppCompatActivity {

    private DatabaseReference mDatabase;

    private String familyNum;
    private String visitNum;

    private Switch compliant_switch;

    private RadioGroup conRdGp;
    private RadioGroup fenceRdGp;

    private Class nextField;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_madera0);

        compliant_switch = (Switch) findViewById(R.id.switch1);

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
        familyNum = extrasBundle.getString("familyNum");
        visitNum = extrasBundle.getString("visitNum");
        mDatabase = FirebaseDatabase.getInstance().getReference().child("families").child(familyNum).child("visits").child("visit"+visitNum);

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
                Visit post = dataSnapshot.getValue(Visit.class);
                if(post != null){
                    prepopulate(post.structures);
                    if(post.animals.committed){
                        nextField = animalsHome.class;
                    }
                    else{
                        nextField= basicData.class;
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

    public void prepopulate(Structure structure){
        // Set compliance switch
        compliant_switch.setChecked(structure.compliant);
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
        if(value.active){
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
        if(value.active){
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
            DatabaseReference dDatabase = FirebaseDatabase.getInstance().getReference().child("families").child(familyNum).child("curr_visit").child("structures").child("construction").child(id);
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
            DatabaseReference dDatabase = FirebaseDatabase.getInstance().getReference().child("families").child(familyNum).child("curr_visit").child("structures").child("fence").child(id);
            dDatabase.child("active").setValue(false);
            conRdGp.removeAllViews();
            fenceRdGp.removeAllViews();
            fenceRdGp.clearCheck();
        }
    }

    public void openMadera4(View v){
        mDatabase.child("structures").child("compliant").setValue(compliant_switch.isChecked());
        Intent intentDetails = new Intent(madera0.this, madera4.class);
        Bundle bundle = new Bundle();
        bundle.putString("visitNum", visitNum);
        bundle.putString("familyNum", familyNum);
        intentDetails.putExtras(bundle);
        startActivity(intentDetails);
    }

    public void openMadera2(View v){

        String selectedId = String.valueOf(conRdGp.getCheckedRadioButtonId());
        Log.e("DEBUG", String.valueOf(selectedId));

        Intent intentDetails = new Intent(madera0.this, madera2.class);
        Bundle bundle = new Bundle();
        bundle.putString("visitNum", visitNum);
        bundle.putString("familyNum", familyNum);
        bundle.putString("structureNum", selectedId);
        intentDetails.putExtras(bundle);
        startActivity(intentDetails);
    }
    public void openMadera3(View v){
        String selectedId = String.valueOf(fenceRdGp.getCheckedRadioButtonId());
        Log.e("DEBUG", String.valueOf(selectedId));

        Intent intentDetails = new Intent(madera0.this, madera3.class);
        Bundle bundle = new Bundle();
        bundle.putString("visitNum", visitNum);
        bundle.putString("familyNum", familyNum);
        bundle.putString("structureNum", selectedId);
        intentDetails.putExtras(bundle);
        startActivity(intentDetails);
    }
    public void openLastField(View v){


            Intent intentDetails = new Intent(madera0.this, nextField);
            Bundle bundle = new Bundle();
            bundle.putString("familyNum", familyNum);
            bundle.putString("visitNum", visitNum);
            intentDetails.putExtras(bundle);
            startActivity(intentDetails);

    }
}
