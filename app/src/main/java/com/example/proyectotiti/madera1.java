package com.example.proyectotiti;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.RadioButton;

public class madera1 extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_madera1);

        final RadioButton radioButtonConstructionSi = (RadioButton) findViewById(R.id.radioButtonConstructionSi);
        final RadioButton radioButtonConstructionNo = (RadioButton) findViewById(R.id.radioButtonConstructionNo);
        final RadioButton radioButtonFencesSi = (RadioButton) findViewById(R.id.radioButtonFencesSi);
        final RadioButton radioButtonFencesNo = (RadioButton) findViewById(R.id.radioButtonFencesNo);

        final Button continueButton = (Button) findViewById(R.id.continueButton);
        continueButton.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v){
                if(radioButtonConstructionSi.isChecked()){
                    startActivity(new Intent(madera1.this, madera2.class));
                }
                else
                {
                    if(radioButtonConstructionNo.isChecked())
                    {
                        if(radioButtonFencesSi.isChecked()) {
                            startActivity(new Intent(madera1.this, madera3.class));
                        }
                        else
                        {
                            if(radioButtonFencesNo.isChecked())
                            {
                                startActivity(new Intent(madera1.this, madera4.class));
                            }
                        }
                    }
                }
            }
        });
    }
}
