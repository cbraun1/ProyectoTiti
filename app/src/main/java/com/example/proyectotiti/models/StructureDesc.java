package com.example.proyectotiti.models;

import java.util.Map;

/** StructureDesc Class
 * This class contains the different attributes given to each structure.
 * Type (String): designates the type of structure
 * Function (String): describes the function of the structure
 * Name (String): name of the structure
 * Active (Boolean): true if the structure is stil relevant
 * Size (String): describes size of the structure
 */

public class StructureDesc {

    public String type;
    public String function;
    public String name;
    public Boolean active;
    public String size;
    public Boolean compliant;
    public String compliant_desc;


    public StructureDesc() {
        // Default constructor required for calls to DataSnapshot.getValue(StructureDesc.class)
    }

    public StructureDesc(String type, String function, String name, Boolean active, String size, Boolean compliant,String compliant_desc) {
        this.type = type;
        this.function = function;
        this.name = name;
        this.active = active;
        this.size = size;
        this.compliant = compliant;
        this.compliant_desc = compliant_desc;

    }
}
