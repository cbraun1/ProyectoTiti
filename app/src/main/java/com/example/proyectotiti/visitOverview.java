package com.example.proyectotiti;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.proyectotiti.models.AnimalDesc;
import com.example.proyectotiti.models.Family;
import com.example.proyectotiti.models.Structure;
import com.example.proyectotiti.models.StructureDesc;
import com.example.proyectotiti.models.User;
import com.example.proyectotiti.models.Visit;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

public class visitOverview extends AppCompatActivity {

    private static final String TAG = "visitOverview";

    private String familyNum;
    private String visitNum;

    private DatabaseReference mDatabase;
    private DatabaseReference userDatabase;
    private LinearLayout mlinearLayout;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_visit_overview);

        mlinearLayout = (LinearLayout) findViewById(R.id.linearLayout);
        // Get current Info
        Intent intentExtras = getIntent();
        Bundle extrasBundle = intentExtras.getExtras();
        familyNum = extrasBundle.getString("familyNum");
        visitNum = extrasBundle.getString("visitNum");

        mDatabase = FirebaseDatabase.getInstance().getReference().child("families").child(familyNum).child("visits").child("visit"+visitNum);
        userDatabase = FirebaseDatabase.getInstance().getReference().child("users");
        getVisitInfo();
    }

    public void getVisitInfo(){

        // Add value event listener to the list of families
        ValueEventListener visitListener = new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot visitSnapshot) {
                Log.e(TAG, String.valueOf(visitSnapshot));
                Visit post = visitSnapshot.getValue(Visit.class);
                populateForm(post);

            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                // Getting Family failed, log a message
                Log.w(TAG, "loadPost:onCancelled", databaseError.toException());
            }
        };

        // Add value event listener to the list of families
        ValueEventListener userListener = new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot userSnapshot) {
                Log.e(TAG, String.valueOf(userSnapshot));
                User post = userSnapshot.getValue(User.class);

            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                // Getting Family failed, log a message
                Log.w(TAG, "loadPost:onCancelled", databaseError.toException());
            }
        };
        mDatabase.addListenerForSingleValueEvent(visitListener);
        userDatabase.addListenerForSingleValueEvent(userListener);
    }

    public void populateForm(Visit visit){
        TextView nameView = new TextView(this);
        nameView.setText(visit.basicData.name);
        nameView.setTextSize(40);

        TextView userView = new TextView(this);
        userView.setText(visit.userID);

        TextView bdataView = new TextView(this);
        bdataView.setText("Dirrecion: " + visit.basicData.address + "\n" + "Communidad: " + visit.basicData.community + "\n" + "Telefono: "+ visit.basicData.phone_number);

        mlinearLayout.addView(nameView);
        mlinearLayout.addView(bdataView);
        mlinearLayout.addView(userView);

        TextView animalTitle = new TextView(this);
        animalTitle.setText("Animales");
        animalTitle.setTextSize(25);
        mlinearLayout.addView(animalTitle);

        TextView wildTitle = new TextView(this);
        wildTitle.setText("Silvestres");
        wildTitle.setTextSize(15);
        mlinearLayout.addView(wildTitle);

        TextView wildText = new TextView(this);
        String animalWildText = "";
        Map<String, AnimalDesc> wildMap = new HashMap<>();
        wildMap = visit.animals.wild;
        for(Map.Entry<String, AnimalDesc> e: wildMap.entrySet()){

            AnimalDesc ad = e.getValue();
            if(ad.active){
                animalWildText += ad.name + "\n" + "Marking: " + ad.marking + "\n" + "Type: " + ad.type + "\n";
            }
        }
        wildText.setText(animalWildText);
        mlinearLayout.addView(wildText);

        TextView domTitle = new TextView(this);
        domTitle.setText("Domesticos");
        domTitle.setTextSize(15);
        mlinearLayout.addView(domTitle);

        TextView domText = new TextView(this);
        String animalDomText = "";
        Map<String, AnimalDesc> domMap = new HashMap<>();
        domMap = visit.animals.domestic;
        for(Map.Entry<String, AnimalDesc> e: domMap.entrySet()){

            AnimalDesc ad = e.getValue();
            if(ad.active){
                animalDomText += ad.name + "\n" + "Marking: " + ad.marking + "\n" + "Type: " + ad.type + "\n";
            }
        }
        domText.setText(animalDomText);
        mlinearLayout.addView(domText);

        TextView structureTitle = new TextView(this);
        structureTitle.setText("Madera del bosque");
        structureTitle.setTextSize(25);
        mlinearLayout.addView(structureTitle);

        TextView conTitle = new TextView(this);
        conTitle.setText("Construcciones");
        conTitle.setTextSize(15);
        mlinearLayout.addView(conTitle);

        TextView conText = new TextView(this);
        String structureConText = "";
        Map<String, StructureDesc> conMap = new HashMap<>();
        conMap = visit.structures.construction;
        for(Map.Entry<String, StructureDesc> e: conMap.entrySet()){

            StructureDesc sd = e.getValue();
            if(sd.active){
                structureConText += sd.name + "\n" + "Size: " + sd.size + "\n" + "Type: " + sd.type + "\n" + "Function: " + sd.function + "\n";
            }
        }
        conText.setText(structureConText);
        mlinearLayout.addView(conText);

        TextView fenceTitle = new TextView(this);
        domTitle.setText("Cercados");
        domTitle.setTextSize(15);
        mlinearLayout.addView(fenceTitle);

        TextView fenceText = new TextView(this);
        String structureFenceText = "";
        Map<String, StructureDesc> fenceMap = new HashMap<>();
        fenceMap = visit.structures.fence;
        for(Map.Entry<String, StructureDesc> e: fenceMap.entrySet()){

            StructureDesc sd = e.getValue();
            if(sd.active){
                structureFenceText += sd.name + "\n" + "Size: " + sd.size + "\n" + "Type: " + sd.type + "\n" + "Function: " + sd.function + "\n";
            }
        }
        fenceText.setText(structureFenceText);
        mlinearLayout.addView(fenceText);

        if(visit.structures.cookWithWoodCoal){
            TextView structureText = new TextView(this);
            structureText.setText("Cooks with wood and coal" + "\n" + "Frequency: " + visit.structures.stove_freq + "\n" + "Type: " + visit.structures.stove_type + "\n");
            mlinearLayout.addView(structureText);
        }

        TextView recycleText = new TextView(this);

        if(visit.recycle.doRecycle){
            recycleText.setText(visit.recycle.recycle_deliver + "\n" + visit.recycle.recycle_items);
        }
        else{
            recycleText.setText(visit.recycle.waste_man);
        }
        mlinearLayout.addView(recycleText);

    }

    public void openViewVisits(View v){

        Intent intentDetails = new Intent(visitOverview.this, viewVisits.class);
        Bundle bundle = new Bundle();
        bundle.putString("familyNum", familyNum);
        intentDetails.putExtras(bundle);
        startActivity(intentDetails);
    }
}
