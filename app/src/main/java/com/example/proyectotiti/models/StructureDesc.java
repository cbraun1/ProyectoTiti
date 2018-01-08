package com.example.proyectotiti.models;

import java.util.Map;

/** StructureDesc Class
 * This class contains the different attributes given to each animl.
 * Type: designates the type of animal
 * Marking: describes the animal
 * Name: name that will be used to differentiate
 * Active: true if the animal is alive and still around, false if not
 */

public class StructureDesc {

    public String type;
    public String function;
    public String name;
    public Boolean active;
    public String size;


    public StructureDesc() {
        // Default constructor required for calls to DataSnapshot.getValue(AnimalDesc.class)
    }

    public StructureDesc(String type, String function, String name, Boolean active, String size) {
        this.type = type;
        this.function = function;
        this.name = name;
        this.active = active;
        this.size = size;

    }
}
