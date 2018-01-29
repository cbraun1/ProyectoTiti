package com.example.proyectotiti.models;

/**
 * This class contains the recycle information for the family.
 * doRecycle (Boolean): indicates if the family does recycle
 * recycle_items (String): includes all items that the family recycles
 * recycle_delivers (String): includes how the recyclable items are delivered.
 * waste_man (String): includes how the family manages waste if they do not recycle.
 */

public class Recycle {

    public Boolean doRecycle;
    public String recycle_items;
    public String recycle_deliver;
    public String waste_man;

    public Recycle() {
        // Default constructor required for calls to DataSnapshot.getValue(Recycle.class)
    }

    public Recycle(Boolean doRecycle, String recycle_items, String recycle_deliver, String waste_man) {
        this.doRecycle = doRecycle;
        this.recycle_items = recycle_items;
        this.recycle_deliver = recycle_deliver;
        this.waste_man = waste_man;
    }
}
