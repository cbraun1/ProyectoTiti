package com.example.proyectotiti;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

public class home extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
    }

    public void openBasicData(View v){
        startActivity(new Intent(home.this, basicData.class));
    }

    public void openContinue(View v){
        startActivity(new Intent(home.this, continuePage.class));
    }

    public void openDownload(View v){
        startActivity(new Intent(home.this, download.class));
    }
}
