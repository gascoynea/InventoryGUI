package com.example.projectc482.Model;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

/**
 * class/model for creating the inventory of parts and products
 */

public class Inventory {
    /**
     * Will Have part and product list and output them, dependant of part.java and product.java
     * creates a part list and a method to add to the list
     */

    public static ObservableList<Part> partInventory = FXCollections.observableArrayList();

    /**
     * @param part
     */
    public static void addPart(Part part){
        partInventory.add(part);
    }

    /**
     * @return
     */
    // method called when getting a list of part ids for creating a unique id.
    public static List<Integer> getPartIDList(){
        List<Integer> partIDs = new ArrayList<>();
        for (int i = 0; i < partInventory.size(); i++){
            partIDs.add(partInventory.get(i).getId());

        }
        return partIDs;
    }

    /**
     * @return
     */
    //returns the partInventory list. It is the same as the getAllParts method in the UML. (different name).
    public static ObservableList<Part> getPartInventory(){
        return partInventory;
    }

    /**
     * @param partNameID
     * @return
     */
    //used for searching part table on the main controller.
    //searchPart same as lookup part for ID and name with the part name and part ID from UML diagram.
    public static ObservableList searchParts(String partNameID) {
        //temp list for seeing matching parts
        ObservableList<Part> matchedParts = FXCollections.observableArrayList();
        //checks if empty to return all parts
        if(partNameID == "") {
            matchedParts = partInventory;
        }
        //checks to see if we are searching with ids
        else {
            if (Character.isDigit(partNameID.charAt(0))) {
                for (Part part : partInventory) {
                    String tempPartID = String.valueOf(part.getId());
                    if (tempPartID.equals(partNameID) || tempPartID.contains(partNameID)) {
                        matchedParts.add(part);
                    }
                }
            }
            //compares input to names in part inventory to return results that contain the inputted string
            else {
                for (Part part : partInventory) {
                    String tempPart = part.getName();
                    if (tempPart.contains(partNameID) || tempPart.toLowerCase().contains(partNameID) || tempPart.toUpperCase().contains(partNameID)) {
                        matchedParts.add(part);

                    }
                }
            }
        }
        //if the size of matched parts is 0 that means no parts were found.
        if (matchedParts.size() == 0){
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Alert");
            alert.setContentText("No Parts found.");
            alert.showAndWait();
        }
        return matchedParts;
    }

    /**
     * @param partID
     * @return
     */
    //Looks up specific part in part list by its part ID
    public static Part lookupPart(Integer partID){
        for (Part lookedupPart : partInventory){
            if (lookedupPart.getId() == partID);
                return lookedupPart;
        }
        return null;
    }

    /**
     * @param productID
     * @return
     */
    //Looks up specific product in product list by its product ID
    public static Product lookupProduct(Integer productID){
        for (Product lookedupProduct : productInventory){
            if (lookedupProduct.getId() == productID);
            return lookedupProduct;
        }
        return null;
    }

    /**
     * @param partName
     * @return
     */
    //Looks up specific part in part list by its part name
    public static ObservableList<Part> lookupPart(String partName){
        ObservableList<Part> lookedUpParts = FXCollections.observableArrayList();

        for (Part lookedupPart : partInventory){
            if (lookedupPart.getName() == partName);
            lookedUpParts.add(lookedupPart);
            return lookedUpParts;
        }
        return null;
    }

    /**
     * @param productName
     * @return
     */
    //Looks up specific product in product list by its product name
    public static ObservableList<Product> lookupProduct(String productName){
        ObservableList<Product> lookedUpProducts = FXCollections.observableArrayList();

        for (Product lookedupProduct : productInventory){
            if (lookedupProduct.getName() == productName);
            lookedUpProducts.add(lookedupProduct);
            return lookedUpProducts;
        }
        return null;
    }

    /**
     * creates a product inventory
     */
    public static ObservableList<Product> productInventory = FXCollections.observableArrayList();

    /**
     * @param product
     */
    //method called to add new products to product inventory
    public static void addProduct(Product product){
        productInventory.add(product);
    }

    /**
     * @return
     */
    //method returns current productInventory. Same method as the getAllProducts method in the UML diagram. Different Name.
    public static ObservableList<Product> getProductInventory(){
        return productInventory;
    }

    /**
     * @param productNameID
     * @return
     */
    //Method does the same thing that the searchPart method does except for products.
    //searchProduct is the same as the lookupProduct method in the UML diagram. This method can use the Product ID or name
    //to find the correct product. It is the same as the 2 methods for lookup product in the UML diagram.
    public static ObservableList searchProducts(String productNameID){
        //temp list for seeing matching products
        ObservableList<Product> matchedProducts = FXCollections.observableArrayList();
        //checks if empty to return all parts
        if(productNameID.equals("")) {
            matchedProducts = productInventory;
        }
        //checks to see if we are searching with ids
        else {
            if (Character.isDigit(productNameID.charAt(0))) {
                for (Product product : productInventory) {
                    String tempProductID = String.valueOf(product.getId());
                    if (tempProductID.equals(productNameID) || tempProductID.contains(productNameID)) {
                        matchedProducts.add(product);
                    }
                }
            }
            //compares input to names in product inventory to return results that contain the inputted string
            else if (Character.isAlphabetic(productNameID.charAt(0))){
                for (Product product : productInventory) {
                    String tempPart = product.getName();
                    if (tempPart.contains(productNameID) || tempPart.toLowerCase().contains(productNameID) || tempPart.toUpperCase().contains(productNameID)) {
                        matchedProducts.add(product);
                    }
                }
            }
        }
        //if the size of matched products is 0 that means no parts were found.
        if (matchedProducts.size() == 0){
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Alert");
            alert.setContentText("No Products found.");
            alert.showAndWait();
        }
        return matchedProducts;
    }

    //Test Data

//    static {
//        addTestData();
//    }
//    public static void addTestData() {
//        Part handle = new Outsourced(1, "Handle", 1.00, 2, 2, 2, "HandleCo.");
//        Inventory.addPart(handle);
//        InHouse bell = new InHouse(2, "bell", 2.00, 4, 2, 5, 1);
//        Inventory.addPart(bell);
//        InHouse pipe = new InHouse(3, "pipe", 1.00, 45, 5, 50, 3);
//        Inventory.addPart(pipe);
//        InHouse tacoBell = new InHouse(12, "tacobell", 2.00, 4, 2, 5, 2);
//        Inventory.addPart(tacoBell);
//        Product handleBell = new Product(1,"HandleBell", 3.00, 2, 2, 6);
//        Inventory.addProduct(handleBell);
//        Product hanBell = new Product(2,"HanBell", 3.00, 4, 4, 6);
//        Inventory.addProduct(hanBell);
//        Product haBell = new Product(12,"HaBell", 3.00, 6, 4, 6);
//        Inventory.addProduct(haBell);
//    }

    /**
     * @return
     */
    //used to return product Ids list and generating new product ids.
    public static List<Integer> getProductIDList() {
        List<Integer> productIDs = new ArrayList<>();
        for (int i = 0; i < productInventory.size(); i++){
            productIDs.add(productInventory.get(i).getId());

        }
        return productIDs;
    }
    //Finds the element to be updated by a given index and then updates it with the new data form the part given.

    /**
     * @param index
     * @param selectedPart
     */
    public static void updatePart(int index, Part selectedPart){
        partInventory.set(index, selectedPart);
    }

    /**
     * @param index
     * @param selectedProduct
     */
    //Finds the element to be updated by a given index and then updates it with the new data form the product given.
    public static void updateProduct(int index, Product selectedProduct){
        productInventory.set(index, selectedProduct);
    }

    /**
     * @param selectedPart
     */
    //Deletes a specific element in the partInventory
    public static void deletePart(Part selectedPart){
        partInventory.remove(selectedPart);
    }

    /**
     * @param selectedProduct
     */
    //Deletes a specific element in the productInventory
    public static void deleteProduct(Part selectedProduct){
        productInventory.remove(selectedProduct);
    }

    /**
     * @return
     */
    //Used to return the entire product list
    public static ObservableList<Part> getAllParts(){
        return partInventory;
    }

    /**
     * @return
     */
    //Used to return the entire part list
    public static ObservableList<Product> getAllProducts(){
        return productInventory;
    }
}
