package com.example.proyectotiti.models;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

/**
 * Holds the information for the family
 */

public class Family {

    public Double id;
    public BasicData basic_data;
    public Map<String, Date_Class> visits;

    public Family() {
        // Default constructor required for calls to DataSnapshot.getValue(Family.class)
    }

    public Family(Double id, BasicData basicData, Map<String, Date_Class> visits) {
        this.id = id;
        this.basic_data = basicData;
        this.visits = visits;
    }

    public BasicData getBasic_data() {
        return basic_data;
    }

    public void setBasic_data(BasicData basic_data) {
        this.basic_data = basic_data;
    }

}
