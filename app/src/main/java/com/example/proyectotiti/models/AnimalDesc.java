package com.example.proyectotiti.models;

import java.util.Map;

/** AnimalDesc Class
 * This class contains the different attributes given to each animl.
 * Type: designates the type of animal
 * Marking: describes the animal
 * Name: name that will be used to differentiate
 * Active: true if the animal is alive and still around, false if not
 */

public class AnimalDesc {

    public String type;
    public String marking;
    public String name;
    public Boolean active;


    public AnimalDesc() {
        // Default constructor required for calls to DataSnapshot.getValue(AnimalDesc.class)
    }

    public AnimalDesc(String type, String marking, String name, Boolean active) {
        this.type = type;
        this.marking = marking;
        this.name = name;
        this.active = active;

    }
}
