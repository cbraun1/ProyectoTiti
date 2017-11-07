package com.example.proyectotiti;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.Spinner;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;


public class basicData extends AppCompatActivity {

    // Declare database reference
    private DatabaseReference mDatabase;

    private EditText family_no;
    private EditText family_name;
    private EditText family_phone;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_basic_data);

        // Views
        family_no = (EditText)findViewById(R.id.editTextNoRegistro);
        family_name = (EditText)findViewById(R.id.editTextNombre);
        family_phone = (EditText)findViewById(R.id.editTextTelefono);

        // Set up instance of database reference
        mDatabase = FirebaseDatabase.getInstance().getReference();

        Spinner spinnerDay = (Spinner) findViewById(R.id.spinnerDay);
        // Create an ArrayAdapter using the string array and a default spinner layout
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this,
                R.array.spinnerDayArray, android.R.layout.simple_spinner_item);
        // Specify the layout to use when the list of choices appears
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        // Apply the adapter to the spinner
        spinnerDay.setAdapter(adapter);

        Spinner spinnerMonth = (Spinner) findViewById(R.id.spinnerMonth);
        // Create an ArrayAdapter using the string array and a default spinner layout
        ArrayAdapter<CharSequence> adapter2 = ArrayAdapter.createFromResource(this,
                R.array.spinnerMonthArray, android.R.layout.simple_spinner_item);
        // Specify the layout to use when the list of choices appears
        adapter2.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        // Apply the adapter to the spinner
        spinnerMonth.setAdapter(adapter2);

        Spinner spinnerYear = (Spinner) findViewById(R.id.spinnerYear);
        // Create an ArrayAdapter using the string array and a default spinner layout
        ArrayAdapter<CharSequence> adapter3 = ArrayAdapter.createFromResource(this,
                R.array.spinnerYearArray, android.R.layout.simple_spinner_item);
        // Specify the layout to use when the list of choices appears
        adapter3.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        // Apply the adapter to the spinner
        spinnerYear.setAdapter(adapter3);
    }



    public void openContinue(View v){
        // Create new instance of family
        Family fam = new Family("family1", "4567890");

        // Send family info to database
        mDatabase.child("families").child("1").setValue(fam);

        startActivity(new Intent(basicData.this, continuePage.class));
    }

    public void openAnimals0(View v){
        // Create new instance of family
        Family fam = new Family(family_name.getText().toString(), family_phone.getText().toString());

        // Send family info to database
        mDatabase.child("families").child(family_no.getText().toString()).setValue(fam);
        startActivity(new Intent(basicData.this, animals0.class));
    }
}