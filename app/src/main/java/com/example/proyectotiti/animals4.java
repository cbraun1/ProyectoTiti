package com.example.proyectotiti;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

public class animals4 extends AppCompatActivity {

    private Integer family_no;
    //private Boolean firstPass;
    private Long visit_num;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_animals4);

        Intent intentExtras = getIntent();
        Bundle extrasBundle = intentExtras.getExtras();
        //firstPass = extrasBundle.getBoolean("firstPass");
        family_no = extrasBundle.getInt("family_no");
        visit_num = extrasBundle.getLong("visit_num");
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
