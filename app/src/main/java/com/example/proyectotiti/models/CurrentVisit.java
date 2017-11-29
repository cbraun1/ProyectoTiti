package com.example.proyectotiti.models;

import java.util.Map;

/** CurrentVisit Class
 * This class contains the animal, structure, etc. objects for each family.
 */

public class CurrentVisit {

    public Animal animals;

    public CurrentVisit() {
        // Default constructor required for calls to DataSnapshot.getValue(CurrentVisit.class)
    }

    public CurrentVisit(Animal animals) {
        this.animals = animals;

    }
}
