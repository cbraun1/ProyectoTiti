package com.example.proyectotiti;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.RadioGroup;

import com.example.proyectotiti.models.Animal;
import com.example.proyectotiti.models.AnimalDesc;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.Map;

public class animalsHome extends BaseActivity {

    private static final String TAG = "animalsHome";

    private DatabaseReference mDatabase;

    private String familyNum;
    private String visitNum;

    private RadioGroup wildRdGp;
    private RadioGroup domesticRdGp;

    /* This function runs upon the creation of the animalsHome screen.
    * If it is not an initial visit, it will prompt the app to read from the database
    * and prepopulate the text boxes.  Otherwise, it will prepopulate the family number
    * with the next consecutive number*/
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_animals_home);

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
        familyNum = extrasBundle.getString("familyNum");
        visitNum = extrasBundle.getString("visitNum");
        mDatabase = FirebaseDatabase.getInstance().getReference().child("families").child(familyNum).child("visits");

        readFromDB();

        wildLinearLayout.addView(wildRdGp);
        domesticLinearLayout.addView(domesticRdGp);

    }


    public void readFromDB() {


        // Add value event listener to the list of families
        ValueEventListener bdListener = new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                Log.e(TAG, String.valueOf(dataSnapshot));
                Animal post = dataSnapshot.getValue(Animal.class);
                if(post != null){
                    prepopulate(post);
                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                // Getting Family failed, log a message
                Log.w(TAG, "loadPost:onCancelled", databaseError.toException());
            }
        };
        mDatabase.child("visit"+visitNum).child("animals").addValueEventListener(bdListener);

    }

    public void prepopulate(Animal animal){
        // Iterate through wild animals and prepopulate
        if(animal.wild != null){
            Log.e(TAG, String.valueOf(animal.wild));
            for (Map.Entry<String, AnimalDesc> entry : animal.wild.entrySet()) {
                Log.e(TAG, String.valueOf(entry));
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
        if(value.active){
            RadioButton rdbtn = new RadioButton(this);
            String[] s = key.split("_");
            int id = Integer.valueOf(s[1]);
            rdbtn.setId(id);
            rdbtn.setText(value.name);
            wildRdGp.addView(rdbtn);
        }
    }

    // Add new animal as a radio button with the text as the animal name and the id as the id
    public void addDomesticRadioButton(String key, AnimalDesc value) {
        if(value.active){
            RadioButton rdbtn = new RadioButton(this);
            String[] s = key.split("_");
            int id = Integer.valueOf(s[1]);
            rdbtn.setId(id);
            rdbtn.setText(value.name);
            domesticRdGp.addView(rdbtn);
        }
    }

    public void deleteWildAnimal(View v){
        int selectedId = wildRdGp.getCheckedRadioButtonId();
        if(selectedId != -1){
            String id = "a_" + selectedId;
            DatabaseReference dDatabase = FirebaseDatabase.getInstance().getReference().child("families").child(familyNum).child("visit"+visitNum).child("animals").child("wild").child(id);
            dDatabase.child("active").setValue(false);
            wildRdGp.removeAllViews();
            domesticRdGp.removeAllViews();
            wildRdGp.clearCheck();
        }
    }

    public void deleteDomAnimal(View v){
        int selectedId = domesticRdGp.getCheckedRadioButtonId();
        if(selectedId != -1){
            String id = "a_" + selectedId;
            DatabaseReference dDatabase = FirebaseDatabase.getInstance().getReference().child("families").child(familyNum).child("visit"+visitNum).child("animals").child("domestic").child(id);
            dDatabase.child("active").setValue(false);
            wildRdGp.removeAllViews();
            domesticRdGp.removeAllViews();
            domesticRdGp.clearCheck();
        }
    }

    public void openBasicData(View v){
        // Pass the id of the family selected to the new activity
        // Pass false to initial visit flag
        // Pass false to firstPass
        Log.e(TAG, "opening basic data");
        Intent intentDetails = new Intent(animalsHome.this, basicData.class);
        Bundle bundle = new Bundle();
        bundle.putString("familyNum", familyNum);
        bundle.putString("visitNum", visitNum);
        intentDetails.putExtras(bundle);
        startActivity(intentDetails);
    }

    public void openMadera0(View v){

        Intent intentDetails = new Intent(animalsHome.this, madera0.class);
        Bundle bundle = new Bundle();
        bundle.putString("familyNum", familyNum);
        bundle.putString("visitNum", visitNum);
        intentDetails.putExtras(bundle);
        startActivity(intentDetails);
    }

    public void openAnimalsWild(View v){
        int selectedId = wildRdGp.getCheckedRadioButtonId();
        Log.e(TAG, String.valueOf(selectedId));
        Log.e(TAG, String.valueOf(visitNum));
        Log.e(TAG, String.valueOf(familyNum));

        Intent intentDetails = new Intent(animalsHome.this, animalsWild.class);
        Bundle bundle = new Bundle();
        bundle.putString("familyNum", familyNum);
        bundle.putString("visitNum", visitNum);
        bundle.putString("animalNum", String.valueOf(selectedId));
        intentDetails.putExtras(bundle);
        startActivity(intentDetails);

    }

    public void openAnimalsDomestic(View v){
        int selectedId = domesticRdGp.getCheckedRadioButtonId();

        Intent intentDetails = new Intent(animalsHome.this, animalsDomestic.class);
        Bundle bundle = new Bundle();
        bundle.putString("familyNum", familyNum);
        bundle.putString("visitNum", visitNum);
        bundle.putString("animalNum", String.valueOf(selectedId));
        intentDetails.putExtras(bundle);
        startActivity(intentDetails);
    }
}
