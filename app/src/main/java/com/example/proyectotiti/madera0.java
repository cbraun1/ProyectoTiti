package com.example.proyectotiti;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

public class madera0 extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_madera0);
    }

    public void openMadera1(View v){
        startActivity(new Intent(madera0.this, madera1.class));
    }

    public void openMadera2(View v){
        startActivity(new Intent(madera0.this, madera2.class));
    }
    public void openMadera3(View v){
        startActivity(new Intent(madera0.this, madera3.class));
    }
    public void openAnimals0(View v){
        startActivity(new Intent(madera0.this, animals0.class));
    }
}
