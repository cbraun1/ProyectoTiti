package com.example.proyectotiti;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

public class madera5 extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_madera5);
    }

    public void openMadera4(View v){
        startActivity(new Intent(madera5.this, madera4.class));
    }

    public void openRecycle1(View v){
        startActivity(new Intent(madera5.this, recycle1.class));
    }
}
