package com.example.projectc482.Model;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

/**
 * Class meant for creating product objects
 */
public class Product extends Inventory {
    //all variables in the product object
    private int id;
    private String name;
    private double price;
    private int stock;
    private int min;
    private int max;
    //list of associated parts
    private ObservableList<Part> partsInProduct = FXCollections.observableArrayList();

    /**
     * @param id
     * @param name
     * @param price
     * @param stock
     * @param min
     * @param max
     */
    //For creation of Product
    public Product(int id, String name, double price, int stock, int min, int max) {
        this.id = id;
        this.name = name;
        this.price = price;
        this.stock = stock;
        this.min = min;
        this.max = max;
    }

    /**
     * @return
     */
    //returns current variables, or sets the variables to new values.
    public int getId() {
        return id;
    }

    /**
     * @param id
     */
    public void setId(int id) {
        this.id = id;
    }

    /**
     * @return
     */
    public String getName() {
        return name;
    }

    /**
     * @param name
     */
    public void setName(String name) {
        this.name = name;
    }

    /**
     * @return
     */
    public double getPrice() {
        return price;
    }

    /**
     * @param price
     */
    public void setPrice(double price) {
        this.price = price;
    }

    /**
     * @return
     */
    public int getStock() {
        return stock;
    }

    /**
     * @param stock
     */
    public void setStock(int stock) {
        this.stock = stock;
    }

    /**
     * @return
     */
    public int getMin() {
        return min;
    }

    /**
     * @param min
     */
    public void setMin(int min) {
        this.min = min;
    }

    /**
     * @return
     */
    public int getMax() {
        return max;
    }

    /**
     * @param max
     */
    public void setMax(int max) {
        this.max = max;
    }

    /**
     * @param partsInProduct
     */
    //used to set associated parts within the product or get current associated part list
    public void setPartsInProduct(ObservableList<Part> partsInProduct) {
        this.partsInProduct = partsInProduct;
    }

    /**
     * @return
     */
    //This is the same method as getAllAssociatedParts() from the UML diagram.
    public ObservableList<Part> getPartsInProduct() {
        return partsInProduct;
    }

    /**
     * @param part
     */
    //Adds a part to the partsInProduct list
    public void addAssociatedProduct(Part part){
        this.partsInProduct.add(part);
    }

    /**
     * @param associatedPart
     * @return
     */
    //checks if part is in the list then deletes it if it is within it.
    //returns T/F depending on if it is in or not
    public Boolean deleteAssociatedPart(Part associatedPart){
        if (partsInProduct.contains(associatedPart)) {
            this.partsInProduct.remove(associatedPart);
            return true;
        }
        return false;
    }
}
