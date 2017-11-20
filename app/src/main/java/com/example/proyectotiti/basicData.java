package com.example.proyectotiti;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.Spinner;

import com.example.proyectotiti.models.BasicData;
import com.example.proyectotiti.models.Changes;
import com.example.proyectotiti.models.Date_Class;
import com.example.proyectotiti.models.Family;
import com.example.proyectotiti.models.OldNewPair;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;


public class basicData extends BaseActivity {

    // Declare database reference
    private DatabaseReference mDatabase;
    private DatabaseReference mFamily;

    private EditText family_no;
    private EditText family_name;
    private EditText family_phone;
    private EditText family_address;
    private EditText family_comm;

    private Spinner spinnerDay;
    private Spinner spinnerMonth;
    private Spinner spinnerYear;

    private Family family;

    private boolean isInitVisit;
    private long visit_num;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_basic_data);

        // Views
        family_no = (EditText)findViewById(R.id.editTextNoRegistro);
        family_name = (EditText)findViewById(R.id.editTextNombre);
        family_phone = (EditText)findViewById(R.id.editTextTelefono);
        family_address = (EditText)findViewById(R.id.editTextDirrecion);
        family_comm = (EditText)findViewById(R.id.editTextComunidad);

        setUpDateSpinner();

        // Get current Info
        Intent intentExtras = getIntent();
        Bundle extrasBundle = intentExtras.getExtras();
        isInitVisit = extrasBundle.getBoolean("isInitVisit");
        if (!isInitVisit){
            // Set so that the number cannot be editable
            family_no.setEnabled(false);
            int id_no = extrasBundle.getInt("family_no");
            mDatabase = FirebaseDatabase.getInstance().getReference().child("families").child(String.valueOf(id_no));
            readFromDB();
        }
        else {
            mDatabase = FirebaseDatabase.getInstance().getReference().child("families");
        }
    }

    public void setUpDateSpinner(){
        spinnerDay = (Spinner) findViewById(R.id.spinnerDay);
        // Create an ArrayAdapter using the string array and a default spinner layout
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this,
                R.array.spinnerDayArray, android.R.layout.simple_spinner_item);
        // Specify the layout to use when the list of choices appears
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        // Apply the adapter to the spinner
        spinnerDay.setAdapter(adapter);

        spinnerMonth = (Spinner) findViewById(R.id.spinnerMonth);
        // Create an ArrayAdapter using the string array and a default spinner layout
        ArrayAdapter<CharSequence> adapter2 = ArrayAdapter.createFromResource(this,
                R.array.spinnerMonthArray, android.R.layout.simple_spinner_item);
        // Specify the layout to use when the list of choices appears
        adapter2.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        // Apply the adapter to the spinner
        spinnerMonth.setAdapter(adapter2);

        spinnerYear = (Spinner) findViewById(R.id.spinnerYear);
        // Create an ArrayAdapter using the string array and a default spinner layout
        ArrayAdapter<CharSequence> adapter3 = ArrayAdapter.createFromResource(this,
                R.array.spinnerYearArray, android.R.layout.simple_spinner_item);
        // Specify the layout to use when the list of choices appears
        adapter3.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        // Apply the adapter to the spinner
        spinnerYear.setAdapter(adapter3);
    }

    public void readFromDB() {

        // Add value event listener to find the visit number
        ValueEventListener visitListener = new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                Log.e("DEBUG", String.valueOf(dataSnapshot));
                visit_num = dataSnapshot.getChildrenCount();
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                // Getting Family failed, log a message
                Log.w("DEBUG", "loadPost:onCancelled", databaseError.toException());
            }
        };
        mDatabase.child("visits").addValueEventListener(visitListener);

        // Add value event listener to the list of families
        ValueEventListener bdListener = new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                Log.e("DEBUG", String.valueOf(dataSnapshot));
                Family post = dataSnapshot.getValue(Family.class);
                prepopulate(post);
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                // Getting Family failed, log a message
                Log.w("DEBUG", "loadPost:onCancelled", databaseError.toException());
            }
        };
        mDatabase.addValueEventListener(bdListener);

    }

    // Prepopulate basic data from last visit
    public void prepopulate(Family fam) {
        family = fam;
        // Set the date to last visit
        String visitID = "visit_" + visit_num;
        Map<String, Date_Class> visit_object = family.visits;
        Date_Class date = visit_object.get(visitID);

        for (int i=0;i<spinnerDay.getCount();i++){
            Log.e("DEBUG", String.valueOf(spinnerDay.getItemAtPosition(i)));
            if (spinnerDay.getItemAtPosition(i).equals(date.day)){
                spinnerDay.setSelection(i);
            }
        }
        for (int i=0;i<spinnerMonth.getCount();i++){
            if (spinnerMonth.getItemAtPosition(i).equals(date.month)){
                spinnerMonth.setSelection(i);
            }
        }
        for (int i=0;i<spinnerYear.getCount();i++){
            if (spinnerYear.getItemAtPosition(i).equals(date.year)){
                spinnerYear.setSelection(i);
            }
        }

        // Set all the editTexts to original data
        family_no.setText(String.valueOf(family.id));
        family_name.setText(family.basic_data.name);
        family_phone.setText(family.basic_data.phone_number);
        family_address.setText(family.basic_data.address);
        family_comm.setText(family.basic_data.community);

    }

    // Back button without saving new input
    public void openContinue(View v){
        if (isInitVisit){
            startActivity(new Intent(basicData.this, home.class));
        }
        else{
            startActivity(new Intent(basicData.this, continuePage.class));
        }
    }

    // Submitting new data to database
    public void openAnimals0(View v){
        // If it is a new visit- Set up new family
        if (isInitVisit){
            // Get id of family
            Double id = Double.parseDouble(family_no.getText().toString());
            // Create new instance of BasicData
            BasicData bdata = new BasicData(family_name.getText().toString(), family_comm.getText().toString(), family_address.getText().toString(), family_phone.getText().toString());
            // Get values of spinner
            String day = spinnerDay.getSelectedItem().toString();
            String month = spinnerMonth.getSelectedItem().toString();
            String year = spinnerYear.getSelectedItem().toString();
            // Make changes map
            Map<String, OldNewPair> changes = new HashMap<String, OldNewPair>();
            // Create new instance of Date_Class
            Date_Class date = new Date_Class(month, day, year, changes);
            // Make visits map
            Map<String, Date_Class> visits = new HashMap<String, Date_Class>();
            visits.put("visit_1",date);

            // Create new instance of family
            Family fam = new Family(id, bdata, visits);
            // Send family info to database
            mDatabase.child(family_no.getText().toString()).setValue(fam);
        }
        // Make changes to old information
        else{
            // Send family info to database
            // Record new date/visit
            visit_num = visit_num + 1;
            String visitID = "visit_" + visit_num;
            // Make map of changes
            Map<String, OldNewPair> changes = new HashMap<String, OldNewPair>();
            // Get values of spinner
            String day = spinnerDay.getSelectedItem().toString();
            String month = spinnerMonth.getSelectedItem().toString();
            String year = spinnerYear.getSelectedItem().toString();
            // If family name changes
            if(!family_name.getText().toString().equals(family.basic_data.name)) {
                OldNewPair new_pair = new OldNewPair(family.basic_data.name, family_name.getText().toString());
                changes.put("basic_data-name", new_pair);
                family.basic_data.name = family_name.getText().toString();
            }
            // If family address changes
            if(!family_address.getText().toString().equals(family.basic_data.address)) {
                OldNewPair new_pair = new OldNewPair(family.basic_data.address, family_address.getText().toString());
                changes.put("basic_data-address", new_pair);
                family.basic_data.address = family_address.getText().toString();
            }
            // If family community changes
            if(!family_comm.getText().toString().equals(family.basic_data.community)) {
                OldNewPair new_pair = new OldNewPair(family.basic_data.community, family_comm.getText().toString());
                changes.put("basic_data-community", new_pair);
                family.basic_data.community = family_comm.getText().toString();
            }
            // If family phone changes
            if(!family_phone.getText().toString().equals(family.basic_data.phone_number)) {
                OldNewPair new_pair = new OldNewPair(family.basic_data.phone_number, family_phone.getText().toString());
                changes.put("basic_data-phone", new_pair);
                family.basic_data.phone_number = family_phone.getText().toString();
            }
            // Create new instance of Date_Class
            Date_Class date = new Date_Class(month, day, year, changes);
            family.visits.put(visitID, date);
            mDatabase.setValue(family);
        }

        startActivity(new Intent(basicData.this, animals0.class));
    }
}