package com.example.proyectotiti.models;

/**
 * Holds the information for the family
 */

public class Family {

    public Double id;
    public String name;
    public String phone_number;

    public Family() {
        // Default constructor required for calls to DataSnapshot.getValue(User.class)
    }

    public Family(Double id, String name, String phone_number) {
        this.id = id;
        this.name = name;
        this.phone_number = phone_number;
    }
}
