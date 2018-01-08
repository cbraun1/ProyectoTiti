package com.example.proyectotiti;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;

import com.example.proyectotiti.models.AnimalDesc;
import com.example.proyectotiti.models.OldNewPair;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class animals4 extends AppCompatActivity {

    private DatabaseReference mDatabase;
    private DatabaseReference visitsDatabase;

    private EditText animal_name;
    private EditText animal_marking;
    private EditText animal_type;

    private Integer family_no;
    private Integer animal_no;
    private Long visit_num;

    private long animals_count;

    private boolean new_animal = false;

    private AnimalDesc animal;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_animals4);

        //Views
        animal_name = (EditText)findViewById(R.id.editTextAnimal);
        animal_marking = (EditText)findViewById(R.id.editTextMarking);
        animal_type = (EditText)findViewById(R.id.editTextType);

        Intent intentExtras = getIntent();
        Bundle extrasBundle = intentExtras.getExtras();
        animal_no = extrasBundle.getInt("animal_no");
        family_no = extrasBundle.getInt("family_no");
        visit_num = extrasBundle.getLong("visit_num");

        if (animal_no == -1){
            new_animal = true;
            mDatabase = FirebaseDatabase.getInstance().getReference().child("families").child(String.valueOf(family_no)).child("curr_visit").child("animals").child("domestic");
            getAnimalNumber();
        }
        else {
            String visitID = "visit_" + visit_num;
            mDatabase = FirebaseDatabase.getInstance().getReference().child("families").child(String.valueOf(family_no)).child("curr_visit").child("animals").child("domestic");
            visitsDatabase = FirebaseDatabase.getInstance().getReference().child("families").child(String.valueOf(family_no)).child("visits").child(visitID).child("changes");
            readFromDB();
        }
    }

    /* This function runs once the family count has been read from the database.*/
    public void getAnimalNumber(){
        // Add value event listener to find the family number
        final ValueEventListener animalListener = new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                Log.e("DEBUG", String.valueOf(dataSnapshot));
                animals_count = dataSnapshot.getChildrenCount();
                Log.e("DEBUG", String.valueOf(animals_count));
                animal_no = (int)animals_count + 1;
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                // Getting Family failed, log a message
                Log.w("DEBUG", "loadPost:onCancelled", databaseError.toException());
            }
        };
        mDatabase.addValueEventListener(animalListener);
    }

    public void readFromDB(){
        // Add value event listener to the list of families
        ValueEventListener aListener = new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                Log.e("DEBUG", String.valueOf(dataSnapshot));
                AnimalDesc post = dataSnapshot.getValue(AnimalDesc.class);
                prepopulate(post);
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                // Getting Family failed, log a message
                Log.w("DEBUG", "loadPost:onCancelled", databaseError.toException());
            }
        };
        String id = "a_" + animal_no.toString();
        mDatabase.child(id).addValueEventListener(aListener);
    }

    public void prepopulate(AnimalDesc post){
        animal = post;
        // Set all the editTexts to original data
        animal_marking.setText(String.valueOf(animal.marking));
        animal_name.setText(animal.name);
        animal_type.setText(animal.type);

    }

    public void submitAnimal(View v){
        String id = "a_" + animal_no.toString();
        if(!new_animal){
            if (!animal_type.getText().toString().equals(animal.type)){
                OldNewPair new_pair = new OldNewPair(animal.type, animal_type.getText().toString());
                visitsDatabase.child("curr_visit-animals-domestic-"+id+"-type").setValue(new_pair);
            }
            if (!animal_marking.getText().toString().equals(animal.marking)){
                OldNewPair new_pair = new OldNewPair(animal.marking, animal_marking.getText().toString());
                visitsDatabase.child("curr_visit-animals-domestic-"+id+"-marking").setValue(new_pair);
            }

            if (!animal_name.getText().toString().equals(animal.name)){
                OldNewPair new_pair = new OldNewPair(animal.name, animal_name.getText().toString());
                visitsDatabase.child("curr_visit-animals-domestic-"+id+"-name").setValue(new_pair);
            }
        }
        AnimalDesc new_animal = new AnimalDesc(animal_type.getText().toString(), animal_marking.getText().toString(), animal_name.getText().toString(), true);
        mDatabase.child(id).setValue(new_animal);
        openAnimals0(v);
    }

    public void openAnimals0(View v){
        Intent intentDetails = new Intent(animals4.this, animals0.class);
        Bundle bundle = new Bundle();
        bundle.putLong("visit_num", visit_num);
        bundle.putInt("family_no", family_no);
        intentDetails.putExtras(bundle);
        startActivity(intentDetails);
    }

    public void openAnimals3(View v){
        startActivity(new Intent(animals4.this, animals3.class));
    }
}
