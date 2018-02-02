package com.example.proyectotiti;

import android.app.ProgressDialog;
import android.content.Intent;
import android.net.Uri;
import android.provider.MediaStore;
import android.support.annotation.NonNull;
import android.support.v4.content.FileProvider;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.example.proyectotiti.models.AnimalDesc;
import com.example.proyectotiti.models.Family;
import com.example.proyectotiti.models.OldNewPair;
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

public class animals2 extends AppCompatActivity {

    private DatabaseReference mDatabase;
    private DatabaseReference visitsDatabase;
    private StorageReference storageReference;

    private ImageButton mImageButton;
    private Uri photoURI;
    private ArrayList<String> uris = new ArrayList<String>() {};

    private LinearLayout mainLinearLayout;
    private Map<String, String> images;


    private EditText animal_name;
    private EditText animal_marking;
    private EditText animal_type;

    private Integer family_no;
    private Integer animal_no;
    private Long visit_num;

    private long animals_count;

    private boolean new_animal = false;

    private AnimalDesc animal;

    private static final int REQUEST_IMAGE_CAPTURE = 1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_animals2);

        //Views
        animal_name = (EditText)findViewById(R.id.editTextAnimal);
        animal_marking = (EditText)findViewById(R.id.editTextMarking);
        animal_type = (EditText)findViewById(R.id.editTextType);

        Intent intentExtras = getIntent();
        Bundle extrasBundle = intentExtras.getExtras();
        animal_no = extrasBundle.getInt("animal_no");
        family_no = extrasBundle.getInt("family_no");
        visit_num = extrasBundle.getLong("visit_num");

        mainLinearLayout = (LinearLayout) findViewById(R.id.linearLayoutMain);
        mImageButton = (ImageButton)findViewById(R.id.imageButton);
        storageReference = FirebaseStorage.getInstance().getReference();

        // When the photo button is pressed, app will switch to android camera
        mImageButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dispatchTakePictureIntent();
            }
        });


        if (animal_no == -1){
            new_animal = true;
            mDatabase = FirebaseDatabase.getInstance().getReference().child("families").child(String.valueOf(family_no)).child("curr_visit").child("animals").child("wild");
            getAnimalNumber();
        }
        else {
            String visitID = "visit_" + visit_num;
            mDatabase = FirebaseDatabase.getInstance().getReference().child("families").child(String.valueOf(family_no)).child("curr_visit").child("animals").child("wild");
            visitsDatabase = FirebaseDatabase.getInstance().getReference().child("families").child(String.valueOf(family_no)).child("visits").child(visitID).child("changes");
            readFromDB();
        }
    }

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
        //mCurrentPhotoPath = image.getAbsolutePath();
        return image;
    }

    /* This function runs upon completing taking a photo.
*   It will upload the file to storage and add the uri to an array.*/
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
                            ImageView image = new ImageView(animals2.this);
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

    /* This function runs once the family count has been read from the database.*/
    public void getAnimalNumber(){
        // Add value event listener to find the family number
        final ValueEventListener animalListener = new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                Log.e("DEBUG", String.valueOf(dataSnapshot));
                animals_count = dataSnapshot.getChildrenCount();
                Log.e("DEBUG", String.valueOf(animals_count));
                animal_no = (int)animals_count + 1;
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                // Getting Family failed, log a message
                Log.w("DEBUG", "loadPost:onCancelled", databaseError.toException());
            }
        };
        mDatabase.addValueEventListener(animalListener);
    }

    public void readFromDB(){
        // Add value event listener to the list of families
        ValueEventListener aListener = new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                Log.e("DEBUG", String.valueOf(dataSnapshot));
                AnimalDesc post = dataSnapshot.getValue(AnimalDesc.class);
                prepopulate(post);
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                // Getting Family failed, log a message
                Log.w("DEBUG", "loadPost:onCancelled", databaseError.toException());
            }
        };
        String id = "a_" + animal_no.toString();
        mDatabase.child(id).addValueEventListener(aListener);
    }

    public void prepopulate(AnimalDesc post){
        animal = post;
        // Set all the editTexts to original data
        animal_marking.setText(String.valueOf(animal.marking));
        animal_name.setText(animal.name);
        animal_type.setText(animal.type);
        Map<String, String> image_object = animal.images;
        images = animal.images;
        Iterator it = null;


        // Display all saved images
        if (image_object!=null){
            it = image_object.entrySet().iterator();

            while(it.hasNext()){
                Map.Entry pair = (Map.Entry)it.next();
                ImageView image = new ImageView(animals2.this);
                Picasso.with(image.getContext()).load(pair.getValue().toString()).into(image);
                mainLinearLayout.addView(image);
            }

        }

    }

    public void submitAnimal(View v){
        Map<String, String> uploads = new HashMap<String, String>();

        for (String uri : uris){
            String uploadId = mDatabase.push().getKey();
            //creating the upload object to store uploaded image details
            uploads.put(uploadId, uri);
            //adding an upload to firebase database
        }
        if(images !=null){
            images.putAll(uploads);
        }
        else{
            images = uploads;
        }

        String id = "a_" + animal_no.toString();
        if(!new_animal){
            if (!animal_type.getText().toString().equals(animal.type)){
                OldNewPair new_pair = new OldNewPair(animal.type, animal_type.getText().toString());
                visitsDatabase.child("curr_visit-animals-wild-"+id+"-type").setValue(new_pair);
            }
            if (!animal_marking.getText().toString().equals(animal.marking)){
                OldNewPair new_pair = new OldNewPair(animal.marking, animal_marking.getText().toString());
                visitsDatabase.child("curr_visit-animals-wild-"+id+"-marking").setValue(new_pair);
            }

            if (!animal_name.getText().toString().equals(animal.name)){
                OldNewPair new_pair = new OldNewPair(animal.name, animal_name.getText().toString());
                visitsDatabase.child("curr_visit-animals-wild-"+id+"-name").setValue(new_pair);
            }
        }
        AnimalDesc new_animal = new AnimalDesc(animal_type.getText().toString(), animal_marking.getText().toString(), animal_name.getText().toString(), true, images);
        mDatabase.child(id).setValue(new_animal);
        openAnimals0(v);
    }

    public void openAnimals0(View v){
        Intent intentDetails = new Intent(animals2.this, animals0.class);
        Bundle bundle = new Bundle();
        bundle.putLong("visit_num", visit_num);
        bundle.putInt("family_no", family_no);
        intentDetails.putExtras(bundle);
        startActivity(intentDetails);
    }

    public void openAnimals3(View v){
        startActivity(new Intent(animals2.this, animals3.class));
    }
}
