package com.example.projectc482.Model;

/**
 * Used to create inhouse parts.
 */
public class InHouse extends Part{
//    Will have machineid as an int with inheritance from part.java
    private int machineID;

    /**
     *
     * @param id
     * @param name
     * @param price
     * @param stock
     * @param min
     * @param max
     * @param machineID
     */
    public InHouse(int id, String name, double price, int stock, int min, int max, int machineID) {
        super (id,name, price, stock, min, max);
        this.machineID = machineID;
    }

    /**
     *
     * @return
     */
    public int getMachineID() {
        return machineID;
    }

    /**
     * @param machineID
     */
    public void setMachineID(int machineID) {
        this.machineID = machineID;
    }
}
