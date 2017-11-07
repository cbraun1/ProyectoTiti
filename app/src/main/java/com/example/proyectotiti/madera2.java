package com.example.proyectotiti;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

public class madera2 extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_madera2);
    }

    public void openMadera0(View v){
        startActivity(new Intent(madera2.this, madera0.class));
    }
    public void openMadera3(View v){
        startActivity(new Intent(madera2.this, madera3.class));
    }
}
