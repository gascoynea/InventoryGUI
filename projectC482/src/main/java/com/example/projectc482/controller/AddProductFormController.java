package com.example.projectc482.controller;

import com.example.projectc482.Model.Inventory;
import com.example.projectc482.Model.Outsourced;
import com.example.projectc482.Model.Part;
import com.example.projectc482.Model.Product;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.util.List;
import java.util.Optional;
import java.util.ResourceBundle;

/**
 * LOGICAL ERROR: While using the save button I was getting a NumberFormatException Error where I was generating the cost of a part from the
 * Text field but it was a String type so I was recieving an error. Parsing it as an Integer fixed this with using a try/catch
 * for NumberFormatException I was able to fix this.
 *This class is for the add product window, which creates new product using the product mode/class.
 */
public class AddProductFormController implements Initializable {
    public AnchorPane AddProductFormWindow;
    public TextField AddProductFormTFID;
    public TextField AddProductFormTFName;
    public TextField AddProductFormTFInventory;
    public TextField AddProductFormTFPrice;
    public TextField AddProductFormTFMax;
    public TextField AddProductFormTFMin;
    public TextField AddProductFormSearchIDName;
    public Button AddProductFormButtonCancel;
    public Button AddProductFormButtonSave;
    public Button AddProductFormButtonRemovePart;
    public Button AddProductFormButtonAdd;
    public TableView AddProductFormTableAddPart;
    public TableView AddProductFormTableRemovePart;
    public Label AddProductFormMainLabel;
    public Label AddProductFormLabelID;
    public Label AddProductFormLabelName;
    public Label AddProductFormLabelInventory;
    public Label AddProductFormLabelPrice;
    public Label AddProductFormLabelMax;
    public Label AddProductFormLabelMin;
    public TableColumn AddProductFormTableAddPartcolPartID;
    public TableColumn AddProductFormTableAddPartcolPartName;
    public TableColumn AddProductFormTableAddPartcolInvLevel;
    public TableColumn AddProductFormTableAddPartcolPriceCostperUnit;
    public TableColumn AddProductFormTableRemovePartcolPartID;
    public TableColumn AddProductFormTableRemovePartcolPartName;
    public TableColumn AddProductFormTableRemovePartcolInvLevel;
    public TableColumn AddProductFormTableRemovePartcolPriceCostperUnit;

    private ObservableList<Part> partsInProduct = FXCollections.observableArrayList();
    private int productID;
    private Product newProduct = null;

    /**
     * @param url
     * @param resourceBundle
     * generates the product id by comparing it to already created products id and taking the next int available.
     * Also, populates the AddProductFormTableAddPart.
     */
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        productID = 1;
        List<Integer> productIDs = Inventory.getProductIDList();
        while (productIDs.contains(productID)) {
            productID += 1;
        }
        AddProductFormTFID.setText(String.valueOf(productID));
        AddProductFormTableAddPart.setItems(Inventory.getPartInventory());

        AddProductFormTableAddPartcolPartID.setCellValueFactory(new PropertyValueFactory<>("id"));
        AddProductFormTableAddPartcolPartName.setCellValueFactory(new PropertyValueFactory<>("name"));
        AddProductFormTableAddPartcolInvLevel.setCellValueFactory(new PropertyValueFactory<>("stock"));
        AddProductFormTableAddPartcolPriceCostperUnit.setCellValueFactory(new PropertyValueFactory<>("price"));
    }

    /**
     * @param actionEvent
     * Allows the user to search for parts in the table.
     */
    //Allows the user to search for parts in the table.
    public void OnSearchPartTable(ActionEvent actionEvent) {
        String searchedPart = AddProductFormSearchIDName.getText();
        ObservableList partsFound = Inventory.searchParts(searchedPart);
        AddProductFormTableAddPart.setItems(partsFound);
    }

    /**
     * @param actionEvent
     */
    //Checks if a part is selected if not throws error message.
    //if found part is added to a list of parts associated with the product.
    //repopulates the associated part list with the all the parts associated with the product
    //populates the associated part table.
    public void OnAddPartClick(ActionEvent actionEvent) {
        Part selectedProductAdd = (Part) AddProductFormTableAddPart.getSelectionModel().getSelectedItem();
        if(selectedProductAdd != null) {
            partsInProduct.add(selectedProductAdd);
            AddProductFormTableRemovePart.setItems(partsInProduct);
            AddProductFormTableRemovePartcolPartID.setCellValueFactory(new PropertyValueFactory<>("id"));
            AddProductFormTableRemovePartcolPartName.setCellValueFactory(new PropertyValueFactory<>("name"));
            AddProductFormTableRemovePartcolInvLevel.setCellValueFactory(new PropertyValueFactory<>("stock"));
            AddProductFormTableRemovePartcolPriceCostperUnit.setCellValueFactory(new PropertyValueFactory<>("price"));
        }
        else{
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Alert");
            alert.setContentText("No part selected." +
                    "\n\nPlease select a part.");
            alert.showAndWait();
        }
    }

    /**
     * @param actionEvent
     */
    //Allows user to remove products from the selected part on the associated part table.
    //also, removes the part from the associated parts in the partsInProduct list.
    public void OnRemoveAssociatedPartClick(ActionEvent actionEvent) {
        Part selectedProductRemove = (Part) AddProductFormTableRemovePart.getSelectionModel().getSelectedItem();
        if (selectedProductRemove != null) {
            //Throws a warning box up to confirm removal of associated part.
            Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
            alert.setTitle("Alert");
            alert.setContentText("Are you sure you want to remove this part?");
            Optional<ButtonType> result = alert.showAndWait();
            if (result.isPresent() && result.get() == ButtonType.OK) {
                partsInProduct.remove(selectedProductRemove);
                AddProductFormTableRemovePart.setItems(partsInProduct);
            }
        } else {
            Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
            alert.setTitle("Alert");
            alert.setContentText("Select a part to remove.");
            alert.showAndWait();
        }
    }

    /**
     * @param actionEvent
     * sets temp variables to what the Text Fields have been set.
     * Creates the Product object and goes back to the main window.
     * LOGICAL ERROR: While using the save button I was getting a NumberFormatException Error where I was generating the cost of a part from the
     * Text field but it was a String type so I was recieving an error. Parsing it as an Integer fixed this with using a try/catch
     * for NumberFormatException I was able to fix this.
     */
    public void OnSaveProductButton(ActionEvent actionEvent) {
        //setting variable to the correlating TF
        String inventory = (AddProductFormTFInventory.getText());
        String price = (AddProductFormTFPrice.getText());
        String name = AddProductFormTFName.getText();
        String max = (AddProductFormTFMax.getText());
        String min = (AddProductFormTFMin.getText());
        //checks to see if the user didn't input into the TF.
        //Also, checks to see if the inventory levels and Max/min levels are correctly inputted.
        if (name.equals("TypeName") || name.isEmpty()){
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Alert");
            alert.setContentText("No name given." +
                    "\n\nPlease provide a name.");
            alert.showAndWait();
        } else if (price.equals("EnterPrice")) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Alert");
            alert.setContentText("No price given." +
                    "\n\nPlease provide a price amount.");
            alert.showAndWait();
        } else if (inventory.equals("EnterInventoryLevel")) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Alert");
            alert.setContentText("No stock amount given." +
                    "\n\nPlease provide a stock amount.");
            alert.showAndWait();
        } else if (min.equals("EnterMin")) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Alert");
            alert.setContentText("No min stock given." +
                    "\n\nPlease provide a stock minimum.");
            alert.showAndWait();
        } else if (max.equals("EnterMax")) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Alert");
            alert.setContentText("No max stock given." +
                    "\n\nPlease provide a stock maximum.");
            alert.showAndWait();
        } else{
            String namenew = AddProductFormTFName.getText();
            //checks to see if name is already taken by another product.
            for (Product product : Inventory.productInventory){
                if (product.getName().equals(namenew)){
                    Alert alert = new Alert(Alert.AlertType.ERROR);
                    alert.setTitle("Alert");
                    alert.setContentText("Name in use." +
                            "\n\nPlease provide a new name.");
                    Optional<ButtonType> result = alert.showAndWait();
                    if(result.isPresent() && result.get() == ButtonType.OK) {
                        return;
                    }
                }
            }
            try {
                int inventorynum = Integer.parseInt((AddProductFormTFInventory.getText()));
                int maxnum = Integer.parseInt((AddProductFormTFMax.getText()));
                int minnum = Integer.parseInt((AddProductFormTFMin.getText()));
                if(inventorynum > maxnum){
                    Alert alert = new Alert(Alert.AlertType.ERROR);
                    alert.setTitle("Alert");
                    alert.setContentText("Stock amount is below the minimum amount allowed." +
                            "\n\nOr" + "\n\nStock amount is greater than maximum amount allowed." +
                            "\n\nAlso, make sure the Min amount is not greater than the Max amount.");
                    Optional<ButtonType> result = alert.showAndWait();
                    if(result.isPresent() && result.get() == ButtonType.OK) {
                        return;
                    }
                }
                else if(inventorynum < minnum){
                    Alert alert = new Alert(Alert.AlertType.ERROR);
                    alert.setTitle("Alert");
                    alert.setContentText("Min is greater than Max");
                    Optional<ButtonType> result = alert.showAndWait();
                    if(result.isPresent() && result.get() == ButtonType.OK) {
                        return;
                    }
                }

                //changes the data in the text fields to their correct data types
                //Creates the product and adds associated parts to its list.
                Double pricenum = Double.valueOf((AddProductFormTFPrice.getText()));
                newProduct = new Product(productID,namenew,pricenum,inventorynum, minnum, maxnum);
                newProduct.setPartsInProduct(partsInProduct);
                Inventory.addProduct(newProduct);
            }
            catch (NumberFormatException a){
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setTitle("Alert");
                alert.setContentText("Max, min, and stock must be whole number Integers.\nCost must be an integer or a decimal." +
                        "\n\nPlease provide an Integer value for Min, Max, or Stock."
                        + "\n\nPlease provide a decimal or whole number Integer for Cost.");
                Optional<ButtonType> result = alert.showAndWait();
                if(result.isPresent() && result.get() == ButtonType.OK) {
                    return;
                }
            }
            //Opens the main form.
            Parent root = null;
            try {
                root = FXMLLoader.load(getClass().getResource("/com/example/projectc482/Main Form.fxml"));
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
            Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
            alert.setTitle("Alert");
            alert.setContentText("Are you sure you want to save this product?");
            Optional<ButtonType> result = alert.showAndWait();
            if(result.isPresent() && result.get() == ButtonType.OK) {
                Scene scene = new Scene(root);
                Stage mainForm = (Stage) ((Node)actionEvent.getSource()).getScene().getWindow();
                mainForm.setScene(scene);
                mainForm.show();
            }


        }
    }

    /**
     * @param actionEvent
     * @throws IOException
     * Closes the Add Product menu and opens the main window.
     */
    public void OnCancelButtonClick(ActionEvent actionEvent) throws IOException {
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle("Alert");
        alert.setContentText("Are you sure you want to cancel?");
        Optional<ButtonType> result = alert.showAndWait();
        if (result.isPresent() && result.get() == ButtonType.OK) {
            Stage stage = (Stage) ((Button) actionEvent.getSource()).getScene().getWindow();
            Object scene = FXMLLoader.load(getClass().getResource("/com/example/projectc482/Main Form.fxml"));
            stage.setScene(new Scene((Parent) scene));
            stage.show();
        }
    }
}
