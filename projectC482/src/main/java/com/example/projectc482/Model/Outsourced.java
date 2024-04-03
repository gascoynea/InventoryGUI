package com.example.projectc482.Model;

/**
 * Used for creating outsourced parts
 */
public class Outsourced extends Part{
//    Will have companyName as a string, inheritance from part.java
    private String companyName;

    /**
     * @param id
     * @param name
     * @param price
     * @param stock
     * @param min
     * @param max
     * @param companyName
     */
    public Outsourced(int id, String name, double price, int stock, int min, int max, String companyName){
        super(id,name, price, stock, min, max);
        this.companyName = companyName;
    }


    /**
     * @return
     */
    public String getCompanyName() {
        return companyName;
    }

    /**
     * @param companyName
     */
    public void setCompanyName(String companyName) {
        this.companyName = companyName;
    }
}
