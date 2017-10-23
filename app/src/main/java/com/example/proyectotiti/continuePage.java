package com.example.proyectotiti;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

public class continuePage extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_continue_page);
    }

    public void openBasicData(View v){
        startActivity(new Intent(continuePage.this, basicData.class));
    }

    public void openHome(View v){
        startActivity(new Intent(continuePage.this, home.class));
    }
}
