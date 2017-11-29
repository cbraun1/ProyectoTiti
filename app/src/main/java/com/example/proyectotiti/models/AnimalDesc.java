package com.example.proyectotiti.models;

import java.util.Map;

/**
 * Created by katieschermerhorn on 11/20/17.
 */

public class AnimalDesc {

    public String type;
    public String marking;
    public String name;
    public Boolean active;


    public AnimalDesc() {
        // Default constructor required for calls to DataSnapshot.getValue(Family.class)
    }

    public AnimalDesc(String type, String marking, String name, Boolean active) {
        this.type = type;
        this.marking = marking;
        this.name = name;
        this.active = active;

    }
}
