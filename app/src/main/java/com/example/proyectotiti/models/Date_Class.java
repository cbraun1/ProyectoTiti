package com.example.proyectotiti.models;

import java.util.Map;

/** Date_Class class
 * This class contains the date and changes for each visit.
 * <String, OldNewPair>: String is the path that was changed.  OldNewPair is the old and new
 * values given.
 */

public class Date_Class {

    public String month;
    public String day;
    public String year;

    public Date_Class() {
        // Default constructor required for calls to DataSnapshot.getValue(Date_Class.class)
    }

    public Date_Class(String month, String day, String year) {
        this.month = month;
        this.day = day;
        this.year = year;

    }
}
