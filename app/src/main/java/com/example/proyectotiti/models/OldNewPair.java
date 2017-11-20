package com.example.proyectotiti.models;

import java.util.Map;

/**
 * Created by katieschermerhorn on 11/15/17.
 */

public class OldNewPair {

    public String old_value;
    public String new_value;

    public OldNewPair() {
        // Default constructor required for calls to DataSnapshot.getValue(Family.class)
    }

    public OldNewPair(String old_value, String new_value) {
        this.old_value = old_value;
        this.new_value = new_value;
    }
}
