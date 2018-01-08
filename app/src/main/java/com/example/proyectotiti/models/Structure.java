package com.example.proyectotiti.models;

import java.util.Map;

/** Structure class
 * This class contains two different objects "construction" and "fence" that contain maps so each structure
 * has an ID and a description.
 */

public class Structure {

    public Map<String, StructureDesc> construction;
    public Map<String, StructureDesc> fence;
    public Boolean cookWithWoodCoal;
    public String stove_freq;
    public String stove_type;

    public Structure() {
        // Default constructor required for calls to DataSnapshot.getValue(Animal.class)
    }

    public Structure(Map<String, StructureDesc> construction, Map<String, StructureDesc> fence, Boolean cookWithWoodCoal, String stove_freq, String stove_type) {
        this.construction = construction;
        this.fence = fence;
        this.cookWithWoodCoal = cookWithWoodCoal;
        this.stove_freq = stove_freq;
        this.stove_type = stove_type;
    }
}
