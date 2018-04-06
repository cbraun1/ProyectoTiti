package com.example.proyectotiti;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;

import com.example.proyectotiti.models.StructureDesc;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class madera2 extends AppCompatActivity {

    private DatabaseReference mDatabase;
    private DatabaseReference visitsDatabase;

    private EditText structure_name;
    private EditText structure_type;
    private EditText structure_size;
    private EditText structure_function;

    private String familyNum;
    private String structureNum;
    private String visitNum;

    private long structuresCount;


    private StructureDesc structure;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_madera2);

        //Views
        structure_name = (EditText)findViewById(R.id.editTextConstruccion);
        structure_type = (EditText)findViewById(R.id.editTextConType);
        structure_size = (EditText)findViewById(R.id.editTextArea);
        structure_function = (EditText)findViewById(R.id.editTextFunction);

        Intent intentExtras = getIntent();
        Bundle extrasBundle = intentExtras.getExtras();
        structureNum = extrasBundle.getString("structureNum");
        familyNum = extrasBundle.getString("familyNum");
        visitNum = extrasBundle.getString("visitNum");

        mDatabase = FirebaseDatabase.getInstance().getReference().child("families").child(familyNum).child("visits");


        if (structureNum.equals("-1")){
            getStructureNumber();
        }
        else {
            readFromDB();
        }
    }

    /* This function runs once the family count has been read from the database.*/
    public void getStructureNumber(){
        // Add value event listener to find the family number
        final ValueEventListener structureListener = new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                Log.e("DEBUG", String.valueOf(dataSnapshot));
                structuresCount = dataSnapshot.getChildrenCount();
                Log.e("DEBUG", String.valueOf(structuresCount));
                structureNum = String.valueOf((int)structuresCount + 1);
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                // Getting Family failed, log a message
                Log.w("DEBUG", "loadPost:onCancelled", databaseError.toException());
            }
        };
        mDatabase.child("visit"+visitNum).child("structures").child("construction").addValueEventListener(structureListener);
    }

    public void readFromDB(){
        // Add value event listener to the list of families
        ValueEventListener sListener = new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                Log.e("DEBUG", String.valueOf(dataSnapshot));
                StructureDesc post = dataSnapshot.getValue(StructureDesc.class);
                prepopulate(post);
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                // Getting Family failed, log a message
                Log.w("DEBUG", "loadPost:onCancelled", databaseError.toException());
            }
        };
        mDatabase.child("visit"+visitNum).child("structures").child("construction").child("s_"+structureNum).addListenerForSingleValueEvent(sListener);
    }

    public void prepopulate(StructureDesc post){
        structure = post;
        // Set all the editTexts to original data
        structure_name.setText(structure.name);
        structure_type.setText(structure.type);
        structure_function.setText(structure.function);
        structure_size.setText(structure.size);

    }

    public void submitStructure(View v){
        StructureDesc new_structure = new StructureDesc(structure_type.getText().toString(), structure_function.getText().toString(), structure_name.getText().toString(), true, structure_size.getText().toString(), true, "");
        mDatabase.child("visit"+visitNum).child("structures").child("construction").child("s_"+structureNum).setValue(new_structure);
        openMadera0(v);
    }

    public void openMadera0(View v){
        Intent intentDetails = new Intent(madera2.this, madera0.class);
        Bundle bundle = new Bundle();
        bundle.putString("visitNum", visitNum);
        bundle.putString("familyNum", familyNum);
        intentDetails.putExtras(bundle);
        startActivity(intentDetails);
    }
    public void openMadera3(View v){
        startActivity(new Intent(madera2.this, madera3.class));
    }
}
