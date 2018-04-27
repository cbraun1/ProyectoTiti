package com.example.proyectotiti;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Switch;
import android.widget.TextView;

import com.example.proyectotiti.models.StructureDesc;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

public class structuresCon extends AppCompatActivity {

    private DatabaseReference mDatabase;

    // Passed from last screen
    private String familyNum;
    private String structureNum;
    private String visitNum;

    // Views
    private EditText structure_name;
    private EditText structureOther;
    private Spinner typeSpinner;
    private EditText structure_size;
    private EditText structure_function;
    private Switch structureCompliant;
    private EditText structureCompliantText;

    private long structuresCount;
    private boolean addNewOption;
    private StructureDesc structure;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_structures_con);

        // Get current info
        Intent intentExtras = getIntent();
        Bundle extrasBundle = intentExtras.getExtras();
        structureNum = extrasBundle.getString("structureNum");
        familyNum = extrasBundle.getString("familyNum");
        visitNum = extrasBundle.getString("visitNum");
        mDatabase = FirebaseDatabase.getInstance().getReference().child("families").child(familyNum).child("visits");

        //Views
        structure_name = (EditText)findViewById(R.id.editTextConstruccion);
        structure_size = (EditText)findViewById(R.id.editTextArea);
        structure_function = (EditText)findViewById(R.id.editTextFunction);
        typeSpinner = (Spinner) findViewById(R.id.spinnerType);
        final TextView structureOtherTextView = (TextView) findViewById(R.id.textViewConStructureOther);
        structureOther = (EditText)findViewById(R.id.editTextOther);
        structureCompliant = (Switch)findViewById(R.id.switch1);
        structureCompliantText = (EditText)findViewById(R.id.editTextCompliance);

        setUpTypeSpinner();

        // Set up onitemlistener to check if the user choses "other"
        typeSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                String chosenOption = (String) parent.getItemAtPosition(position);

                if (chosenOption.equals("otro")){
                    structureOther.setVisibility(View.VISIBLE);
                    structureOtherTextView.setVisibility(View.VISIBLE);
                    addNewOption = true;
                }

            }
            @Override
            public void onNothingSelected(AdapterView<?> parent) {}
        });


        if (structureNum.equals("-1")){
            getStructureNumber();
        }
        else {
            readFromDB();
        }
    }

    // Pulls the types of constructures from the Firebase database
    private void setUpTypeSpinner() {
        DatabaseReference tDatabase = FirebaseDatabase.getInstance().getReference().child("structure_types");
        tDatabase.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                // Is better to use a List, because you don't know the size
                // of the iterator returned by dataSnapshot.getChildren() to
                // initialize the array
                final List<String> types = new ArrayList<String>();

                for (DataSnapshot typeSnapshot: dataSnapshot.getChildren()) {
                    String typeName = typeSnapshot.getValue(String.class);
                    types.add(typeName);
                }

                ArrayAdapter<String> typesAdapter = new ArrayAdapter<String>(structuresCon.this, android.R.layout.simple_spinner_item, types);
                typesAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                typeSpinner.setAdapter(typesAdapter);
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });

    }

    /* This function runs once the family count has been read from the database.*/
    public void getStructureNumber(){
        // Add value event listener to find the family number
        final ValueEventListener structureListener = new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                structuresCount = dataSnapshot.getChildrenCount();
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
        structure_function.setText(structure.function);
        structureCompliantText.setText(structure.compliant_desc);
        structureCompliant.setChecked(structure.compliant);
        structure_size.setText(structure.size);
        for (int i=0;i<typeSpinner.getCount();i++){
            if (typeSpinner.getItemAtPosition(i).equals(post.type)){
                typeSpinner.setSelection(i);
            }
        }

    }

    public void submitStructure(View v){
        StructureDesc new_structure;
        if(addNewOption){
            new_structure = new StructureDesc(structureOther.getText().toString(), structure_function.getText().toString(), structure_name.getText().toString(), true, "",structure_size.getText().toString(), structureCompliant.isChecked(), structureCompliantText.getText().toString());

            // Add new option to the database
            DatabaseReference nDatabase = FirebaseDatabase.getInstance().getReference().child("structure_types").child(structureOther.getText().toString());
            nDatabase.setValue(structureOther.getText().toString());
        }
        else{
            new_structure = new StructureDesc(typeSpinner.getSelectedItem().toString(), structure_function.getText().toString(), structure_name.getText().toString(), true, "",structure_size.getText().toString(), structureCompliant.isChecked(), structureCompliantText.getText().toString());
        }
        mDatabase.child("visit"+visitNum).child("structures").child("construction").child("s_"+structureNum).setValue(new_structure);
        openStructuresHome(v);
    }

    public void openStructuresHome(View v){
        Intent intentDetails = new Intent(structuresCon.this, structuresHome.class);
        Bundle bundle = new Bundle();
        bundle.putString("visitNum", visitNum);
        bundle.putString("familyNum", familyNum);
        intentDetails.putExtras(bundle);
        startActivity(intentDetails);
    }

}
