package com.example.proyectotiti;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

public class animals4 extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_animals4);
    }

    public void openMadera0(View v){
        startActivity(new Intent(animals4.this, madera0.class));
    }

    public void openAnimals3(View v){
        startActivity(new Intent(animals4.this, animals3.class));
    }
}
