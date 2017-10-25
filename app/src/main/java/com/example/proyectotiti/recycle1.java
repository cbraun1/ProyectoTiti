package com.example.proyectotiti;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.RadioButton;

public class recycle1 extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_recycle1);

        final RadioButton radioButtonSi = (RadioButton) findViewById(R.id.radioButtonSi);
        final RadioButton radioButtonNo = (RadioButton) findViewById(R.id.radioButtonNo);

        final Button continueButton = (Button) findViewById(R.id.continueButton);
        continueButton.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v){
                if(radioButtonSi.isChecked()){
                    startActivity(new Intent(recycle1.this, recycle2.class));
                }
                else
                {
                    if(radioButtonNo.isChecked())
                    {
                        startActivity(new Intent(recycle1.this, recycle3.class));
                    }
                }
            }
        });
    }
}
