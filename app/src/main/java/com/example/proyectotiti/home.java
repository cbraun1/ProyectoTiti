package com.example.proyectotiti;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import com.google.firebase.auth.FirebaseAuth;

public class home extends BaseActivity {

    private static final String TAG = "home";

    /* This function runs upon the creation of the home screen. */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
    }

    /* This function runs upon the clicking of the new family button.
     * Opens basic data for initial visits for families. */
    public void openBasicData(View v){
        // Pass the id of the family selected to the new activity
        Intent intentDetails = new Intent(home.this, basicData.class);
        Bundle bundle = new Bundle();
        bundle.putBoolean("isInitVisit", true);
        bundle.putBoolean("firstPass", true);
        intentDetails.putExtras(bundle);
        startActivity(intentDetails);
    }

    /* This function runs upon the clicking of the continue family button.
     * Opens continue page to decide which family to follow up. */
    public void openContinue(View v){
        startActivity(new Intent(home.this, continuePage.class));
    }


    /* This function runs upon the clicking of the sign out  button. */
    public void openMain(View v){
        FirebaseAuth.getInstance().signOut();
        startActivity(new Intent(home.this, login.class));
        finish();
    }
}
