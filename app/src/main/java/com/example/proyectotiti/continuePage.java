package com.example.proyectotiti;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.RadioGroup;

import com.example.proyectotiti.models.Family;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

public class continuePage extends AppCompatActivity {

    private RadioGroup familyRdbtn;

    private DatabaseReference mDatabase;

    /* This function runs upon the creation of the continue screen.
    * Sets up the radio button group for the families and a reference to the database
    * where the families are stored. */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_continue_page);

        // Set up Radio Button Group with Linear Layout
        LinearLayout mLinearLayout = (LinearLayout) findViewById(R.id.linear1);
        familyRdbtn = new RadioGroup(this);
        familyRdbtn.setOrientation(RadioGroup.VERTICAL);

        mDatabase = FirebaseDatabase.getInstance().getReference().child("families");

        mLinearLayout.addView(familyRdbtn);
    }

    /* This function runs upon the creation of the home screen.
    * It adds a value event listener to the database reference in order to find the families. */
    @Override
    public void onStart() {
        super.onStart();

        // Add value event listener to the list of families
        ValueEventListener familyListener = new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                for (DataSnapshot familySnapshot: dataSnapshot.getChildren()) {
                    Log.e("DEBUG", String.valueOf(familySnapshot));
                    Family post = familySnapshot.getValue(Family.class);
                    addFamilyRadioButton(post);
                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                // Getting Family failed, log a message
                Log.w("DEBUG", "loadPost:onCancelled", databaseError.toException());
            }
        };
        mDatabase.addValueEventListener(familyListener);
    }

    /* This function runs upon the finding of an existing family.
     * It will add the family as a radio button with the text as the family name and the id
      * as the id.*/
    public void addFamilyRadioButton(Family fam) {
        RadioButton rdbtn = new RadioButton(this);
        int id = fam.id.intValue();
        rdbtn.setId(id);
        rdbtn.setText(fam.basic_data.name);
        familyRdbtn.addView(rdbtn);
    }


    /* This function runs upon the selection of the submit button.
     * It will pass the family number to the next screen. */
    public void openBasicData(View v){
        int selectedId = familyRdbtn.getCheckedRadioButtonId();
        if (selectedId == -1) {
            return;
        }

        // Pass the id of the family selected to the new activity
        // Pass false to initial visit flag
        // Pass true to firstPass
        Intent intentDetails = new Intent(continuePage.this, basicData.class);
        Bundle bundle = new Bundle();
        bundle.putBoolean("isInitVisit", false);
        bundle.putInt("family_no", selectedId);
        bundle.putBoolean("firstPass", true);
        intentDetails.putExtras(bundle);
        startActivity(intentDetails);
    }

    /* This function runs upon clicking the back button. */
    public void openHome(View v){
        startActivity(new Intent(continuePage.this, home.class));
    }
}
