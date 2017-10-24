package com.example.proyectotiti;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.RadioButton;

public class animals1 extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_animals1);

        final RadioButton radioButtonSi = (RadioButton) findViewById(R.id.radioButtonSi);
        final RadioButton radioButtonNo = (RadioButton) findViewById(R.id.radioButtonNo);

        final Button continueButton = (Button) findViewById(R.id.continueButton);
        continueButton.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v){
                if(radioButtonSi.isChecked()){
                    startActivity(new Intent(animals1.this, animals2.class));
                }
                else
                {
                    if(radioButtonNo.isChecked())
                    {
                        startActivity(new Intent(animals1.this, animals3.class));
                    }
                }
            }
        });
    }

    public void openAnimals0(View v){
        startActivity(new Intent(animals1.this, animals0.class));
    }

}
