package com.example.proyectotiti.models;

import java.util.Map;

/** Structure class
 * This class contains two different objects "construction" and "fence" that contain maps so each structure
 * has an ID and a description.
 * cookWithWoodCoal (Boolean): true if the family cooks with wood or coal
 * stove_freq (String): indicates how frequently the family uses the stove
 * stove_type (String): indicates the type of stove used by the family.
 */

public class Structure {

    public Map<String, StructureDesc> construction;
    public Map<String, StructureDesc> fence;
    public Boolean cookWithWoodCoal;
    public String stove_freq;
    public String stove_type;
    public Map<String, String> images;
    public boolean committed;

    public Structure() {
        // Default constructor required for calls to DataSnapshot.getValue(Structure.class)
    }

    public Structure(Map<String, StructureDesc> construction, Map<String, StructureDesc> fence, Boolean cookWithWoodCoal, String stove_freq, String stove_type, Map<String,String> images, boolean committed) {
        this.construction = construction;
        this.fence = fence;
        this.cookWithWoodCoal = cookWithWoodCoal;
        this.stove_freq = stove_freq;
        this.stove_type = stove_type;
        this.images = images;
        this.committed = committed;
    }
}
