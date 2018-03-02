package com.example.proyectotiti;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

public class conservacion1 extends AppCompatActivity {

    private static final String TAG = "conservaion0";

    private String familyNum;
    private String visitNum;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_conservacion1);

        // Get current Info
        Intent intentExtras = getIntent();
        Bundle extrasBundle = intentExtras.getExtras();
        familyNum = extrasBundle.getString("familyNum");
        visitNum = extrasBundle.getString("visitNum");
    }

    public void openConservacion0(View v){
        // Pass the id of the family selected to the new activity
        // Pass false to initial visit flag
        Intent intentDetails = new Intent(conservacion1.this, conservaion0.class);
        Bundle bundle = new Bundle();
        bundle.putString("familyNum", familyNum);
        bundle.putString("visitNum", visitNum);
        intentDetails.putExtras(bundle);
        startActivity(intentDetails);
    }

    public void openVisitOverview(View v){
        // Pass the id of the family selected to the new activity
        // Pass false to initial visit flag
        Intent intentDetails = new Intent(conservacion1.this, visitOverview.class);
        Bundle bundle = new Bundle();
        bundle.putString("familyNum", familyNum);
        bundle.putString("visitNum", visitNum);
        intentDetails.putExtras(bundle);
        startActivity(intentDetails);
    }
}
