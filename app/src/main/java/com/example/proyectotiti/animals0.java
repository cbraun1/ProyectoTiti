package com.example.proyectotiti;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

public class animals0 extends AppCompatActivity {

    private int family_no = 1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_animals0);
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

    public void openAnimals1(View v){

        startActivity(new Intent(animals0.this, animals1.class));
    }

    public void openAnimals2(View v){

        startActivity(new Intent(animals0.this, animals2.class));
    }

    public void openAnimals4(View v){

        startActivity(new Intent(animals0.this, animals4.class));
    }
}
