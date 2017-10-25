package com.example.proyectotiti;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

public class madera3 extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_madera3);
    }
    public void openMadera0(View v){
        startActivity(new Intent(madera3.this, madera0.class));
    }
    public void openMadera4(View v){
        startActivity(new Intent(madera3.this, madera4.class));
    }
}
