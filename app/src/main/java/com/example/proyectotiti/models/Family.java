package com.example.proyectotiti;

/**
 * Holds the information for the family
 */

public class Family {

    public String name;
    public String phone_number;

    public Family() {
        // Default constructor required for calls to DataSnapshot.getValue(User.class)
    }

    public Family(String name, String phone_number) {
        this.name = name;
        this.phone_number = phone_number;
    }
}
