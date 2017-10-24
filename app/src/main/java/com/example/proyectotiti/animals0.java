package com.example.proyectotiti;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

public class animals0 extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_animals0);
    }

    public void openBasicData(View v){

        startActivity(new Intent(animals0.this, basicData.class));
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
