package com.example.proyectotiti.models;

import java.util.Map;

/** OldNewPair Class
 * This class contains old and new values for each change.
 * old_value (String): the value of the original
 * new_value (String): the new value
 */

public class OldNewPair {

    public String old_value;
    public String new_value;

    public OldNewPair() {
        // Default constructor required for calls to DataSnapshot.getValue(OldNewPair.class)
    }

    public OldNewPair(String old_value, String new_value) {
        this.old_value = old_value;
        this.new_value = new_value;
    }
}
