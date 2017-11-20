package com.example.proyectotiti.models;

import java.util.Map;

/**
 * Created by katieschermerhorn on 11/15/17.
 */

public class Changes {

    public Map<String, OldNewPair> path;

    public Changes() {
        // Default constructor required for calls to DataSnapshot.getValue(Family.class)
    }

    public Changes(Map<String, OldNewPair> path) {
        this.path = path;
    }

}
