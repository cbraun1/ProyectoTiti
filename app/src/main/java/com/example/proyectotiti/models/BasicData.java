package com.example.proyectotiti.models;

/**
 * Created by katieschermerhorn on 11/13/17.
 */

public class BasicData {

    public String name;
    public String community;
    public String address;
    public String phone_number;

    public BasicData() {
        // Default constructor required for calls to DataSnapshot.getValue(Family.class)
    }

    public BasicData(String name, String community, String address, String phone_number) {
        this.name = name;
        this.community = community;
        this.phone_number = phone_number;
        this.address = address;
    }

}
