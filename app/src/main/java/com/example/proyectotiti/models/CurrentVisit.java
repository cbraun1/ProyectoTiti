package com.example.proyectotiti.models;

import java.util.Map;

/**
 * Created by katieschermerhorn on 11/20/17.
 */

public class CurrentVisit {

    public Animal animals;

    public CurrentVisit() {
        // Default constructor required for calls to DataSnapshot.getValue(Family.class)
    }

    public CurrentVisit(Animal animals) {
        this.animals = animals;

    }
}
