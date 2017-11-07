package com.example.proyectotiti;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

public class recycle2 extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_recycle2);
    }

    public void openRecycle1(View v){
        startActivity(new Intent(recycle2.this, recycle1.class));
    }
    public void openRevise(View v){
        startActivity(new Intent(recycle2.this, revise.class));
    }
}
