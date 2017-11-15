package com.example.proyectotiti;

import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.provider.MediaStore;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.Spinner;

import com.example.proyectotiti.models.BasicData;
import com.example.proyectotiti.models.Family;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;


public class basicData extends BaseActivity {

    // Declare database reference
    private DatabaseReference mDatabase;
    private DatabaseReference mFamily;

    private EditText family_no;
    private EditText family_name;
    private EditText family_phone;

    private boolean isInitVisit;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_basic_data);

        // Views
        family_no = (EditText)findViewById(R.id.editTextNoRegistro);
        family_name = (EditText)findViewById(R.id.editTextNombre);
        family_phone = (EditText)findViewById(R.id.editTextTelefono);


        setUpMonthSpinner();

        // Get current Info
        Intent intentExtras = getIntent();
        Bundle extrasBundle = intentExtras.getExtras();
        isInitVisit = extrasBundle.getBoolean("isInitVisit");
        if (!isInitVisit){
            int id_no = extrasBundle.getInt("family_no");
            mDatabase = FirebaseDatabase.getInstance().getReference().child("families").child(String.valueOf(id_no));
            readFromDB();
        }
        else {
            mDatabase = FirebaseDatabase.getInstance().getReference().child("families");
        }
    }

    public void setUpMonthSpinner(){
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

    public void readFromDB() {

        // Add value event listener to the list of families
        ValueEventListener bdListener = new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                Log.e("DEBUG", String.valueOf(dataSnapshot));
                Family post = dataSnapshot.getValue(Family.class);
                //for (DataSnapshot bdSnapshot: dataSnapshot.getChildren()) {
                    Log.e("DEBUG", String.valueOf(post));
                    //Family post = bdSnapshot.getValue(Family.class);
                    prepopulate(post);
                //}
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                // Getting Family failed, log a message
                Log.w("DEBUG", "loadPost:onCancelled", databaseError.toException());
            }
        };
        mDatabase.addValueEventListener(bdListener);

    }

    public void prepopulate(Family fam) {
        family_no.setText(String.valueOf(fam.id));
        family_name.setText(fam.basic_data.name);
        family_phone.setText(fam.basic_data.phone_number);
    }

    public void openContinue(View v){
        if (isInitVisit){
            startActivity(new Intent(basicData.this, home.class));
        }
        else{
            startActivity(new Intent(basicData.this, continuePage.class));
        }
    }

    public void openAnimals0(View v){

        if (isInitVisit){
            // Create new instance of BasicData
            BasicData bdata = new BasicData(family_name.getText().toString(), "", "", family_phone.getText().toString());
            // Create new instance of family
            Family fam = new Family(Double.parseDouble(family_no.getText().toString()), bdata);
            // Send family info to database
            mDatabase.child(family_no.getText().toString()).setValue(fam);
        }
        else{
            // Send family info to database
            mDatabase.child("basic_data").child("name").setValue(family_name.getText().toString());
            Log.e("DEBUG", family_name.getText().toString());
            mDatabase.child("basic_data").child("phone_number").setValue(family_phone.getText().toString());
        }

        startActivity(new Intent(basicData.this, animals0.class));
    }

    static final int REQUEST_IMAGE_CAPTURE = 1;

    private void dispatchTakePictureIntent() {
        Intent takePictureIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        if (takePictureIntent.resolveActivity(getPackageManager()) != null) {
            startActivityForResult(takePictureIntent, REQUEST_IMAGE_CAPTURE);
        }
    }
    static final int REQUEST_VIDEO_CAPTURE = 1;

    private void dispatchTakeVideoIntent() {
        Intent takeVideoIntent = new Intent(MediaStore.ACTION_VIDEO_CAPTURE);
        if (takeVideoIntent.resolveActivity(getPackageManager()) != null) {
            startActivityForResult(takeVideoIntent, REQUEST_VIDEO_CAPTURE);
        }
    }
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == REQUEST_IMAGE_CAPTURE && resultCode == RESULT_OK) {
            Bundle extras = data.getExtras();
            Bitmap imageBitmap = (Bitmap) extras.get("data");
            mImageView.setImageBitmap(imageBitmap);
        }
    }

}