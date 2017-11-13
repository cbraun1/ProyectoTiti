package com.example.proyectotiti.models;

/**
 * Holds the information for the family
 */

public class Family {

    public Double id;
    public BasicData basic_data;

    public Family() {
        // Default constructor required for calls to DataSnapshot.getValue(Family.class)
    }

    public Family(Double id, BasicData basicData) {
        this.id = id;
        this.basic_data = basicData;
    }

    public BasicData getBasic_data() {
        return basic_data;
    }

    public void setBasic_data(BasicData basic_data) {
        this.basic_data = basic_data;
    }

}
