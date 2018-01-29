package com.example.proyectotiti.models;

import java.util.Map;

/** CurrentVisit Class
 * This class contains the animals, structures, and recycle objects for each family.
 */

public class CurrentVisit {

    public Animal animals;
    public Structure structures;
    public Recycle recycle;

    public CurrentVisit() {
        // Default constructor required for calls to DataSnapshot.getValue(CurrentVisit.class)
    }

    public CurrentVisit(Animal animals, Structure structures, Recycle recycle) {
        this.animals = animals;
        this.structures = structures;
        this.recycle = recycle;

    }
}
