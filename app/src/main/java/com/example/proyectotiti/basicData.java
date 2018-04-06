package com.example.proyectotiti;

import android.*;
import android.Manifest;
import android.annotation.SuppressLint;
import android.app.ProgressDialog;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Criteria;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.provider.MediaStore;
import android.provider.Settings;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.FileProvider;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.example.proyectotiti.models.BasicData;
import com.example.proyectotiti.models.Date_Class;
import com.example.proyectotiti.models.Family;
import com.example.proyectotiti.models.Visit;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;
import com.squareup.picasso.Picasso;

import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;


public class basicData extends BaseActivity {

    private static final String TAG = "basicData";

    //Views
    private EditText family_no;
    private EditText family_name;
    private EditText family_phone;
    private EditText family_address;
    private EditText family_comm;
    private LinearLayout mainLinearLayout;
    private ImageButton mImageButton;
    //private Button gpsButton;
    private TextView gpsCoords;

    // Declare database and storage reference
    private DatabaseReference mDatabase;
    private StorageReference storageReference;

    private LocationManager locationMan;
    private LocationListener locationListener;
    private String mprovider;

    // Passed variables
    private String familyNum;
    private String visitNum;

    private Uri photoURI;
    private ArrayList<String> uris = new ArrayList<String>() {
    };

    private Spinner spinnerDay;
    private Spinner spinnerMonth;
    private Spinner spinnerYear;

    private boolean animals;
    private boolean structures;
    private boolean recycle;
    private boolean conservation;


    private static final int REQUEST_IMAGE_CAPTURE = 1;

    /* This function runs upon the creation of the basic data screen.
    * If it is not an initial visit, it will prompt the app to read from the database
    * and prepopulate the text boxes.  Otherwise, it will prepopulate the family number
    * with the next consecutive number*/
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_basic_data);

        // Views
        family_no = (EditText) findViewById(R.id.editTextNoRegistro);
        family_name = (EditText) findViewById(R.id.editTextNombre);
        family_phone = (EditText) findViewById(R.id.editTextTelefono);
        family_address = (EditText) findViewById(R.id.editTextDirrecion);
        family_comm = (EditText) findViewById(R.id.editTextComunidad);

        //gpsButton = (Button) findViewById(R.id.gpsButton);
        gpsCoords = (TextView) findViewById(R.id.gpsCoordsTextView);

        locationMan = (LocationManager) getSystemService(LOCATION_SERVICE);
        locationListener = new LocationListener() {
            @Override
            public void onLocationChanged(Location location) {
                Log.e(TAG, "in");
                gpsCoords.append(location.getLatitude() + "," + location.getLongitude());
                Log.e(TAG, String.valueOf(location.getLatitude()));
            }

            @Override
            public void onStatusChanged(String s, int i, Bundle bundle) {
            }

            @Override
            public void onProviderEnabled(String s) {
            }

            @Override
            public void onProviderDisabled(String s) {
                Intent intent = new Intent(Settings.ACTION_LOCATION_SOURCE_SETTINGS);
                startActivity(intent);
            }
        };
        Criteria crit = new Criteria();


        mprovider = locationMan.getBestProvider(crit, false);
        Log.e(TAG, mprovider);

        if (mprovider != null && !mprovider.equals("")) {
            if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
                return;
            }
            Location location = locationMan.getLastKnownLocation(mprovider);
            locationMan.requestLocationUpdates(mprovider, 15, 1, locationListener);

            if (location == null)
                Toast.makeText(getBaseContext(), "No Location Provider Found Check Your Code", Toast.LENGTH_SHORT).show();
        }

        //startLocationServices();

        mainLinearLayout = (LinearLayout) findViewById(R.id.linearLayoutMain);
        mImageButton = (ImageButton) findViewById(R.id.imageButton);
        storageReference = FirebaseStorage.getInstance().getReference();

        // When the photo button is pressed, app will switch to android camera
        mImageButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dispatchTakePictureIntent();
            }
        });

        setUpDateSpinner();

        // Get current Info
        Intent intentExtras = getIntent();
        Bundle extrasBundle = intentExtras.getExtras();
        familyNum = extrasBundle.getString("familyNum");
        visitNum = extrasBundle.getString("visitNum");
        mDatabase = FirebaseDatabase.getInstance().getReference().child("families").child(familyNum);

        if (!visitNum.equals("1")) {
            readFromDB();
        } else {
            family_no.setText(familyNum);
        }
    }


    private void startLocationServices() {


        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            if (checkSelfPermission(Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && checkSelfPermission(Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
                Log.e(TAG, "in check perms");
                requestPermissions(new String[]{
                        Manifest.permission.ACCESS_FINE_LOCATION, Manifest.permission.ACCESS_COARSE_LOCATION, Manifest.permission.INTERNET
                }, 10);
                return;
            } else {
                Log.e(TAG, "or here");
                //configureGPSButton();
            }
        }


    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        switch (requestCode) {
            case 10:
                if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED)
                    Log.e(TAG, "on result");
                //configureGPSButton();

                return;

        }
    }

//    private void configureGPSButton() {
//        //gpsButton.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                locationMan = (LocationManager) getSystemService(LOCATION_SERVICE);
//                locationListener = new LocationListener() {
//                    @Override
//                    public void onLocationChanged(Location location) {
//                        Log.e(TAG, "in");
//                        gpsCoords.append(location.getLatitude() + "," + location.getLongitude());
//                        Log.e(TAG, String.valueOf(location.getLatitude()));
//                    }
//
//                    @Override
//                    public void onStatusChanged(String s, int i, Bundle bundle) {
//                    }
//
//                    @Override
//                    public void onProviderEnabled(String s) {
//                    }
//
//                    @Override
//                    public void onProviderDisabled(String s) {
//                        Intent intent = new Intent(Settings.ACTION_LOCATION_SOURCE_SETTINGS);
//                        startActivity(intent);
//                    }
//                };
//                Log.e(TAG, "clicked");
//                try{
//                    locationMan.requestLocationUpdates(LocationManager.GPS_PROVIDER, 10,0,locationListener);
//                } catch (SecurityException e){
//                    Log.e(TAG, String.valueOf(e));
//                }
//            }
//        });
//    }

    // IMAGE CAPABILITY
    /* This function runs upon the pressing of the camera button.
    * It will set up a new photo file and put the new photo into there.*/
    private void dispatchTakePictureIntent() {
        Intent takePictureIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);

        if (takePictureIntent.resolveActivity(getPackageManager()) != null) {
            // Create the File where the photo should go
            File photoFile = null;
            try {
                photoFile = createImageFile();
            } catch (IOException ex) {
                // Error occurred while creating the File
            }
            // Continue only if the File was successfully created
            if (photoFile != null) {
                photoURI = FileProvider.getUriForFile(this,
                        "com.example.proyectotiti.fileprovider",
                        photoFile);
                takePictureIntent.putExtra(MediaStore.EXTRA_OUTPUT, photoURI);
                startActivityForResult(takePictureIntent, REQUEST_IMAGE_CAPTURE);
            }

        }
    }
    private File createImageFile() throws IOException {
        // Create an image file name
        String timeStamp = new SimpleDateFormat("yyyyMMdd_HHmmss").format(new Date());
        String imageFileName = "JPEG_" + timeStamp;
        File image = File.createTempFile(
                imageFileName,  /* prefix */
                ".jpg"         /* suffix */
        );

        // Save a file: path for use with ACTION_VIEW intents
        return image;
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if(requestCode == REQUEST_IMAGE_CAPTURE && resultCode == RESULT_OK) {
            //displaying progress dialog while image is uploading
            final ProgressDialog progressDialog = new ProgressDialog(this);
            progressDialog.setTitle("Uploading");
            progressDialog.show();

            Uri uri = photoURI;

            //getting the storage reference
            StorageReference filePath = storageReference.child("Photos").child(uri.getLastPathSegment());

            //adding the file to reference
            filePath.putFile(uri)
                    .addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                        @Override
                        public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                            //dismissing the progress dialog
                            progressDialog.dismiss();

                            //displaying success toast
                            Toast.makeText(getApplicationContext(), "File Uploaded ", Toast.LENGTH_LONG).show();

                            // Add uri to list of new uris
                            uris.add(taskSnapshot.getDownloadUrl().toString());

                            // Display new image
                            ImageView image = new ImageView(basicData.this);
                            Picasso.with(image.getContext()).load(taskSnapshot.getDownloadUrl().toString()).into(image);
                            mainLinearLayout.addView(image);

                        }
                    })
                    .addOnFailureListener(new OnFailureListener() {
                        @Override
                        public void onFailure(@NonNull Exception exception) {
                            progressDialog.dismiss();
                            Toast.makeText(getApplicationContext(), exception.getMessage(), Toast.LENGTH_LONG).show();
                        }
                    });
        }

    }

    /* This function runs upon the creation of the basic data screen.
    * It will set up the spinner with the correct spinner options.*/
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

    /* This function runs upon the creation of the basic data screen if it is a follow up visit.
    It will read from the database to prepopulate the text boxes and well as determine which
    number of visit the follow up is.*/
    public void readFromDB() {
        Integer prevVisitInt = Integer.valueOf(visitNum) - 1;

        // Add value event listener to the list of families
        ValueEventListener bdListener = new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                Log.e(TAG, String.valueOf(dataSnapshot));
                Visit post = dataSnapshot.getValue(Visit.class);
                mDatabase.child("visits").child("visit"+visitNum).setValue(post);
                prepopulate(post);
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                // Getting Family failed, log a message
                Log.w(TAG, "loadPost:onCancelled", databaseError.toException());
            }
        };
        mDatabase.child("visits").child("visit"+String.valueOf(prevVisitInt)).addListenerForSingleValueEvent(bdListener);

    }

    /* This function runs once the family has been read from the database if it is not an initial
    visit.
     It will prepopulate the editTexts with the most current information.*/
    public void prepopulate(Visit visit) {
        // Set the date to last visit
        Date_Class date = visit.date;

        for (int i=0;i<spinnerDay.getCount();i++){
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
        family_no.setText(familyNum);
        family_name.setText(visit.basicData.name);
        family_phone.setText(visit.basicData.phone_number);
        family_address.setText(visit.basicData.address);
        family_comm.setText(visit.basicData.community);

        // Information on which fields the family has committed to
        animals = visit.animals.committed;
        structures = visit.structures.committed;
        recycle = visit.recycle.committed;
        conservation = visit.conservation.committed;


        Map<String, String> image_object = visit.basicData.images;

        // Display all saved images
        Iterator it = null;


        // Display all saved images
        if (image_object!=null){
            it = image_object.entrySet().iterator();

            while(it.hasNext()){
                Map.Entry pair = (Map.Entry)it.next();
                ImageView image = new ImageView(basicData.this);
                Picasso.with(image.getContext()).load(pair.getValue().toString()).into(image);
                mainLinearLayout.addView(image);
            }

        }

    }

    /* This function runs if the back button is pressed.
    * If it is an initial visit, this button will bring the user back to the home screen.  If it
    * is a follow up visit, this button will bring the user back to the family screen.*/
    public void openContinue(View v){
        if (visitNum.equals("1")){
            startActivity(new Intent(basicData.this, home.class));
        }
        else{
            startActivity(new Intent(basicData.this, continuePage.class));
        }
    }

    public void openCommitments(){
        Intent intentDetails = new Intent(basicData.this, commitments.class);
        Bundle bundle = new Bundle();
        bundle.putString("familyNum", familyNum);
        bundle.putString("visitNum", visitNum);
        intentDetails.putExtras(bundle);
        startActivity(intentDetails);
    }

    public void openNextActivity(){
        Intent intentDetails;
        if (animals){
            intentDetails = new Intent(basicData.this, animalsHome.class);

        }
        else if (structures){
            intentDetails = new Intent(basicData.this, madera0.class);

        }
        else if(recycle){
            intentDetails = new Intent(basicData.this, recycle1.class);

        }
        else if(conservation){
            intentDetails = new Intent(basicData.this, conservacion1.class);

        }
        else{
            intentDetails = new Intent(basicData.this, visitOverview.class);

        }
        Bundle bundle = new Bundle();
        bundle.putString("familyNum", familyNum);
        bundle.putString("visitNum", visitNum);
        intentDetails.putExtras(bundle);
        startActivity(intentDetails);
    }



    /* This function runs if the forward button is pressed.
    * This will submit the basic data to the database.  It will create a new family if it is the
    * initial visit, or create a new visit and document changes made.*/
    public void submitBasicData(View v) {
        Map<String, String> uploads = new HashMap<String, String>();

        for (String uri : uris){
            String uploadId = mDatabase.push().getKey();
            uploads.put(uploadId, uri);
        }

        // Create new instance of BasicData
        BasicData bdata = new BasicData(family_name.getText().toString(), family_comm.getText().toString(), family_address.getText().toString(), family_phone.getText().toString(), uploads, gpsCoords.getText().toString());
        // Get values of spinner
        String day = spinnerDay.getSelectedItem().toString();
        String month = spinnerMonth.getSelectedItem().toString();
        String year = spinnerYear.getSelectedItem().toString();
        // Create new instance of Date_Class
        Date_Class date = new Date_Class(month, day, year);
        // Make Visit object
//        Visit visit = new Visit(bdata, new Animal(new HashMap<String, AnimalDesc>(), new HashMap<String, AnimalDesc>()), new Structure(new HashMap<String, StructureDesc>(), new HashMap<String, StructureDesc>(), false, "", "", null), new Recycle(false, "", "", "", null), date);
        mDatabase.child("visits").child("visit"+visitNum).child("basicData").setValue(bdata);
        mDatabase.child("visits").child("visit"+visitNum).child("date").setValue(date);
        mDatabase.child("name").setValue(family_name.getText().toString());
        mDatabase.child("id").setValue(familyNum);
        mDatabase.child("visits").child("visit"+visitNum).child("userID").setValue(getUid());

        if(visitNum.equals("1")){
            openCommitments();
        }
        else{
            openNextActivity();
        }

    }

}