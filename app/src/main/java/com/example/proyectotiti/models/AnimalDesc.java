package com.example.proyectotiti.models;

import java.util.Map;

/** AnimalDesc Class
 * This class contains the different attributes given to each animal.
 * Type (String): designates the type of animal
 * Marking (String): describes the animal
 * Name (String): name that will be used to differentiate
 * Active (Boolean): true if the animal is alive/still around, false if not
 */

public class AnimalDesc {

    public String type;
    public String marking;
    public String name;
    public Boolean active;
    public Map<String, String> images;


    public AnimalDesc() {
        // Default constructor required for calls to DataSnapshot.getValue(AnimalDesc.class)
    }

    public AnimalDesc(String type, String marking, String name, Boolean active, Map<String, String> images) {
        this.type = type;
        this.marking = marking;
        this.name = name;
        this.active = active;
        this.images = images;

    }
}
