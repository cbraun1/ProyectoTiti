package com.example.proyectotiti;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;

import com.example.proyectotiti.models.OldNewPair;
import com.example.proyectotiti.models.StructureDesc;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class madera3 extends AppCompatActivity {

    private DatabaseReference mDatabase;
    private DatabaseReference visitsDatabase;

    private EditText structure_name;
    private EditText structure_type;
    private EditText structure_size;
    private EditText structure_function;

    private Integer family_no;
    private Integer structure_no;
    private Long visit_num;

    private long structures_count;

    private boolean new_structure = false;

    private StructureDesc structure;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_madera3);

        //Views
        structure_name = (EditText)findViewById(R.id.editTextCercado);
        structure_type = (EditText)findViewById(R.id.editTextFenceType);
        structure_size = (EditText)findViewById(R.id.editTextPerimeter);
        structure_function = (EditText)findViewById(R.id.editTextFunction);

        Intent intentExtras = getIntent();
        Bundle extrasBundle = intentExtras.getExtras();
        structure_no = extrasBundle.getInt("structure_no");
        family_no = extrasBundle.getInt("family_no");
        visit_num = extrasBundle.getLong("visit_num");

        if (structure_no == -1){
            new_structure = true;
            mDatabase = FirebaseDatabase.getInstance().getReference().child("families").child(String.valueOf(family_no)).child("curr_visit").child("structures").child("fence");
            getStructureNumber();
        }
        else {
            String visitID = "visit_" + visit_num;
            mDatabase = FirebaseDatabase.getInstance().getReference().child("families").child(String.valueOf(family_no)).child("curr_visit").child("structures").child("fence");
            visitsDatabase = FirebaseDatabase.getInstance().getReference().child("families").child(String.valueOf(family_no)).child("visits").child(visitID).child("changes");
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
                structures_count = dataSnapshot.getChildrenCount();
                Log.e("DEBUG", String.valueOf(structures_count));
                structure_no = (int)structures_count + 1;
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                // Getting Family failed, log a message
                Log.w("DEBUG", "loadPost:onCancelled", databaseError.toException());
            }
        };
        mDatabase.addValueEventListener(structureListener);
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
        String id = "s_" + structure_no.toString();
        mDatabase.child(id).addValueEventListener(sListener);
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
        String id = "s_" + structure_no.toString();
        if(!new_structure){
            if (!structure_function.getText().toString().equals(structure.function)){
                OldNewPair new_pair = new OldNewPair(structure.function, structure_type.getText().toString());
                visitsDatabase.child("curr_visit-structures-fence-"+id+"-function").setValue(new_pair);
            }
            if (!structure_type.getText().toString().equals(structure.type)){
                OldNewPair new_pair = new OldNewPair(structure.type, structure_type.getText().toString());
                visitsDatabase.child("curr_visit-structures-fence-"+id+"-type").setValue(new_pair);
            }

            if (!structure_name.getText().toString().equals(structure.name)){
                OldNewPair new_pair = new OldNewPair(structure.name, structure_name.getText().toString());
                visitsDatabase.child("curr_visit-structures-fence-"+id+"-name").setValue(new_pair);
            }
            if (!structure_size.getText().toString().equals(structure.size)){
                OldNewPair new_pair = new OldNewPair(structure.size, structure_size.getText().toString());
                visitsDatabase.child("curr_visit-structures-fence-"+id+"-size").setValue(new_pair);
            }
        }
        StructureDesc new_structure = new StructureDesc(structure_type.getText().toString(), structure_function.getText().toString(), structure_name.getText().toString(), true, structure_size.getText().toString());
        mDatabase.child(id).setValue(new_structure);
        openMadera0(v);
    }

    public void openMadera0(View v){

        Intent intentDetails = new Intent(madera3.this, madera0.class);
        Bundle bundle = new Bundle();
        bundle.putLong("visit_num", visit_num);
        bundle.putInt("family_no", family_no);
        intentDetails.putExtras(bundle);
        startActivity(intentDetails);
    }
    public void openMadera4(View v){
        startActivity(new Intent(madera3.this, madera4.class));
    }
}
