package com.example.proyectotiti.models;

import java.util.Map;

/**
 * Created by katieschermerhorn on 11/16/17.
 */

public class Date_Class {

    public String month;
    public String day;
    public String year;
    public Map<String, OldNewPair> changes;

    public Date_Class() {
        // Default constructor required for calls to DataSnapshot.getValue(Family.class)
    }

    public Date_Class(String month, String day, String year, Map<String, OldNewPair> changes) {
        this.month = month;
        this.day = day;
        this.year = year;
        this.changes = changes;

    }
}
