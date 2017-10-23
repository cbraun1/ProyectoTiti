package com.example.proyectotiti;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

public class basicData extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_basic_data);
    }

    public void openContinue(View v){
        startActivity(new Intent(basicData.this, continuePage.class));
    }

    public void openAnimals0(View v){
        startActivity(new Intent(basicData.this, animals0.class));
    }
}