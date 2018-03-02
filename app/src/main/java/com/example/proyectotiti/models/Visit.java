package com.example.proyectotiti.models;

import java.util.Date;
import java.util.Map;

/** Visit Class
 * This class contains the animals, structures, date, and recycle objects for each visit.
 */

public class Visit {

    public BasicData basicData;
    public Animal animals;
    public Structure structures;
    public Recycle recycle;
    public Conservation conservation;
    public Date_Class date;
    public String userID;

    public Visit() {
        // Default constructor required for calls to DataSnapshot.getValue(CurrentVisit.class)
    }

    public Visit(BasicData basicData, Animal animals, Structure structures, Recycle recycle, Date_Class date, String uid) {
        this.basicData = basicData;
        this.animals = animals;
        this.structures = structures;
        this.recycle = recycle;
        this.date = date;
        this.userID = uid;

    }
}
