package com.example.proyectotiti.models;

import java.util.Map;

/** Animal class
 * This class contains two different objects "wild" and "domestic" that contain maps so each animal
 * has an ID (String) and a description (AnimalDesc).
 */

public class Animal {

    public Map<String, AnimalDesc> wild;
    public Map<String, AnimalDesc> domestic;


    public Animal() {
        // Default constructor required for calls to DataSnapshot.getValue(Animal.class)
    }

    public Animal(Map<String, AnimalDesc> wild, Map<String, AnimalDesc> domestic) {
        this.wild = wild;
        this.domestic = domestic;

    }
}
