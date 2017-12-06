package com.example.proyectotiti;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.RadioButton;

public class madera4 extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_madera4);

        final RadioButton radioButtonSi = (RadioButton) findViewById(R.id.radioButtonSi);
        final RadioButton radioButtonNo = (RadioButton) findViewById(R.id.radioButtonNo);

        final Button continueButton = (Button) findViewById(R.id.continueButton);
        continueButton.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v){
                if(radioButtonSi.isChecked()){
                    startActivity(new Intent(madera4.this, madera5.class));
                }
                else
                {
                    if(radioButtonNo.isChecked())
                    {
                        startActivity(new Intent(madera4.this, recycle1.class));
                    }
                }
            }
        });
    }

    public void openMadera0(View v){
        startActivity(new Intent(madera4.this, madera0.class));
    }
    public void openMadera5(View v){
        startActivity(new Intent(madera4.this, madera5.class));
    }
}
