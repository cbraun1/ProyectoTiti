package com.example.proyectotiti;

import android.content.Intent;
import android.provider.MediaStore;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.RadioGroup;

import com.example.proyectotiti.models.Animal;
import com.example.proyectotiti.models.AnimalDesc;
import com.example.proyectotiti.models.Family;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.HashMap;
import java.util.Map;

public class animals0 extends AppCompatActivity {

    private DatabaseReference mDatabase;

    private Integer family_no;
    //private Boolean firstPass;
    private Long visit_num;

    private RadioGroup wildRdGp;
    private RadioGroup domesticRdGp;

    /* This function runs upon the creation of the animals0 screen.
    * If it is not an initial visit, it will prompt the app to read from the database
    * and prepopulate the text boxes.  Otherwise, it will prepopulate the family number
    * with the next consecutive number*/
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_animals0);

        // Set up Radio Button Gorups with Linear Layout
        LinearLayout wildLinearLayout = (LinearLayout) findViewById(R.id.linear_wild);
        LinearLayout domesticLinearLayout = (LinearLayout) findViewById(R.id.linear_domestic);
        wildRdGp = new RadioGroup(this);
        domesticRdGp = new RadioGroup(this);
        wildRdGp.setOrientation(RadioGroup.VERTICAL);
        domesticRdGp.setOrientation(RadioGroup.VERTICAL);

        // Get current Info
        Intent intentExtras = getIntent();
        Bundle extrasBundle = intentExtras.getExtras();
        //firstPass = extrasBundle.getBoolean("firstPass");
        family_no = extrasBundle.getInt("family_no");
        visit_num = extrasBundle.getLong("visit_num");
        mDatabase = FirebaseDatabase.getInstance().getReference().child("families").child(String.valueOf(family_no)).child("curr_visit").child("animals");

        readFromDB();

        wildLinearLayout.addView(wildRdGp);
        domesticLinearLayout.addView(domesticRdGp);

    }

    public void readFromDB() {

        // Add value event listener to the list of families
        ValueEventListener bdListener = new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                Log.e("DEBUG", String.valueOf(dataSnapshot));
                Animal post = dataSnapshot.getValue(Animal.class);
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

    public void prepopulate(Animal animal){
        // Iterate through wild animals and prepopulate
        if(animal.wild != null){
            Log.e("DEBUG", String.valueOf(animal.wild));
            for (Map.Entry<String, AnimalDesc> entry : animal.wild.entrySet()) {
                Log.e("DEBUG", String.valueOf(entry));
                String key = entry.getKey();
                AnimalDesc value = entry.getValue();
                if (value != null) {
                    addWildRadioButton(key,value);
                }
            }
        }
        // Iterate through domestic animals and prepopulate
        if(animal.domestic != null) {
            for (Map.Entry<String, AnimalDesc> entry : animal.domestic.entrySet()) {
                String key = entry.getKey();
                AnimalDesc value = entry.getValue();
                if (value != null) {
                    addDomesticRadioButton(key,value);
                }
            }
        }
    }

    // Add new animal as a radio button with the text as the animal name and the id as the id
    public void addWildRadioButton(String key, AnimalDesc value) {
        RadioButton rdbtn = new RadioButton(this);
        String[] s = key.split("_");
        int id = Integer.valueOf(s[1]);
        rdbtn.setId(id);
        rdbtn.setText(value.name);
        wildRdGp.addView(rdbtn);
    }

    // Add new animal as a radio button with the text as the animal name and the id as the id
    public void addDomesticRadioButton(String key, AnimalDesc value) {
        RadioButton rdbtn = new RadioButton(this);
        String[] s = key.split("_");
        int id = Integer.valueOf(s[1]);
        rdbtn.setId(id);
        rdbtn.setText(value.name);
        domesticRdGp.addView(rdbtn);
    }

    public void openBasicData(View v){
        // Pass the id of the family selected to the new activity
        // Pass false to initial visit flag
        // Pass false to firstPass
        Intent intentDetails = new Intent(animals0.this, basicData.class);
        Bundle bundle = new Bundle();
        bundle.putBoolean("isInitVisit", false);
        bundle.putInt("family_no", family_no);
        bundle.putBoolean("firstPass", false);
        intentDetails.putExtras(bundle);
        startActivity(intentDetails);
    }

    public void openMadera0(View v){

        startActivity(new Intent(animals0.this, madera0.class));
    }

    public void openAnimals2(View v){
        int selectedId = domesticRdGp.getCheckedRadioButtonId();

        Intent intentDetails = new Intent(animals0.this, animals2.class);
        Bundle bundle = new Bundle();
        bundle.putLong("visit_num", visit_num);
        bundle.putInt("family_no", family_no);
//        bundle.putInt("animal_no", selectedId);
        intentDetails.putExtras(bundle);
        startActivity(intentDetails);

    }

    public void openAnimals4(View v){

        Intent intentDetails = new Intent(animals0.this, animals4.class);
        Bundle bundle = new Bundle();
        bundle.putLong("visit_num", visit_num);
        bundle.putInt("family_no", family_no);
        intentDetails.putExtras(bundle);
        startActivity(intentDetails);
    }
}
