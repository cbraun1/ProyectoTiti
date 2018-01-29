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

    public Double id;
    public BasicData basic_data;
    public Map<String, Date_Class> visits;
    public CurrentVisit curr_visit;

    public Family() {
        // Default constructor required for calls to DataSnapshot.getValue(Family.class)
    }

    public Family(Double id, BasicData basicData, Map<String, Date_Class> visits, CurrentVisit curr_visit) {
        this.id = id;
        this.basic_data = basicData;
        this.visits = visits;
        this.curr_visit = curr_visit;
    }

    public BasicData getBasic_data() {
        return basic_data;
    }

    public void setBasic_data(BasicData basic_data) {
        this.basic_data = basic_data;
    }

}
