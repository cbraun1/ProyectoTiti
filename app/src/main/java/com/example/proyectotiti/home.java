package com.example.proyectotiti;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

import com.google.firebase.auth.FirebaseAuth;

public class home extends BaseActivity {

    private static final String TAG = "home";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
    }

    // Opens basic data for initial visits for families
    public void openBasicData(View v){
        // Pass the id of the family selected to the new activity
        Intent intentDetails = new Intent(home.this, basicData.class);
        Bundle bundle = new Bundle();
        bundle.putBoolean("isInitVisit", true);
        bundle.putBoolean("firstPass", true);
        intentDetails.putExtras(bundle);
        startActivity(intentDetails);
    }

    // Opens screen to allow for continued visits for families
    public void openContinue(View v){
        startActivity(new Intent(home.this, continuePage.class));
    }

//    public void openDownload(View v){
//        startActivity(new Intent(home.this, download.class));
//    }

    // Signs out and goes back to login screen
    public void openMain(View v){
        FirebaseAuth.getInstance().signOut();
        startActivity(new Intent(home.this, MainActivity.class));
        finish();
    }
}
