package com.example.proyectotiti.models;

import java.util.Map;

/**
 * Created by katieschermerhorn on 11/20/17.
 */

public class Animal {

    public Map<String, AnimalDesc> wild;
    public Map<String, AnimalDesc> domestic;


    public Animal() {
        // Default constructor required for calls to DataSnapshot.getValue(Family.class)
    }

    public Animal(Map<String, AnimalDesc> wild, Map<String, AnimalDesc> domestic) {
        this.wild = wild;
        this.domestic = domestic;

    }
}
