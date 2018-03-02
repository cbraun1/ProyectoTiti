package com.example.proyectotiti.models;

import java.util.Map;

/**
 * Created by katieschermerhorn on 3/1/18.
 */

public class Conservation {

    public boolean committed;

    public Conservation() {
        // Default constructor required for calls to DataSnapshot.getValue(Conservation.class)
    }

    public Conservation(boolean committed) {
        this.committed = committed;
    }
}
