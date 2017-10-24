package com.example.proyectotiti;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

public class animals2 extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_animals2);
    }

    public void openAnimals1(View v){
        startActivity(new Intent(animals2.this, animals1.class));
    }

    public void openAnimals3(View v){
        startActivity(new Intent(animals2.this, animals3.class));
    }
}
