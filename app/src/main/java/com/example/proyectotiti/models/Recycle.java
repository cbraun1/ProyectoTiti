package com.example.proyectotiti.models;

import java.util.Map;

/**
 * Created by katieschermerhorn on 1/8/18.
 */

public class Recycle {

    public Boolean doRecycle;
    public String recycle_items;
    public String recycle_deliver;
    public String waste_man;

    public Recycle() {
        // Default constructor required for calls to DataSnapshot.getValue(Animal.class)
    }

    public Recycle(Boolean doRecycle, String recycle_items, String recycle_deliver, String waste_man) {
        this.doRecycle = doRecycle;
        this.recycle_items = recycle_items;
        this.recycle_deliver = recycle_deliver;
        this.waste_man = waste_man;
    }
}
