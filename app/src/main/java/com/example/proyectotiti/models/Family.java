package com.example.proyectotiti.models;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

/** Family class
 * Holds the information for the family.
 * id (Double): the unique number given to the family
 * basic_data (BasicData): the basic data object for the family
 * visits (Map<String, Date_Class>): String is the visit number corresponding to a specific date class
 * curr_visit (CurrentVisit): the current visit object for the family.
 */

public class Family {

    public Map<String, Visit> visits;
    public String name;

    public Family() {
        // Default constructor required for calls to DataSnapshot.getValue(Family.class)
    }

    public Family(Map<String, Visit> visits, String name) {
        this.visits = visits;
        this.name = name;
    }

}
