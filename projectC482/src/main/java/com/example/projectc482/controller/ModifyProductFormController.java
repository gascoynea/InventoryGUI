package com.example.projectc482.controller;

import com.example.projectc482.Model.*;
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
import java.util.Optional;
import java.util.ResourceBundle;

/**
 * We had a null-pointer towards the Text Field boxes which initialized with a null value.
 * To fix it I gave the TFs their own starting strings. So when I pointed to them with a
 * .setText() I didn't receive a null pointer exception.
 * class used to modify products in the products class
 */
public class ModifyProductFormController implements Initializable {
    public AnchorPane ModifyProductFormMainWindow;
    public TextField ModifyProductFormMainTFID;
    public TextField ModifyProductFormMainTFName;
    public TextField ModifyProductFormMainTFInv;
    public TextField ModifyProductFormMainTFPrice;
    public TextField ModifyProductFormMainTFMax;
    public TextField ModifyProductFormMainTFMin;
    public TextField ModifyProductFormMainSearchPartIDName;
    public Button ModifyProductFormButtonCancel;
    public Button ModifyProductFormButtonSave;
    public Button ModifyProductFormButtonRemovePart;
    public Button ModifyProductFormButtonAdd;
    public TableView ModifyProductFormAddTable;
    public TableColumn ModifyProductFormAddTablePartID;
    public TableColumn ModifyProductFormAddTablePartName;
    public TableColumn ModifyProductFormAddTableInvLevel;
    public TableColumn ModifyProductFormAddTablePriceCostperUnit;
    public TableView ModifyProductFormRemovePartTable;
    public TableColumn ModifyProductFormRemovePartTablePartID;
    public TableColumn ModifyProductFormRemovePartTablePartName;
    public TableColumn ModifyProductFormRemovePartTableInvLevel;
    public TableColumn ModifyProductFormRemovePartTablePriceCostperUnit;
    public Label ModifyProductFormMainLabel;
    public Label ModifyProductFormLabelID;
    public Label ModifyProductFormLabelName;
    public Label ModifyProductFormLabelInventory;
    public Label ModifyProductFormLabelPrice;
    public Label ModifyProductFormLabelMax;
    public Label ModifyProductFormLabelMin;
    //data structures used to modify products
    public Product selectedProductModify;
    public ObservableList<Part> partsInProduct = FXCollections.observableArrayList();
    public ObservableList<Part> partsInProductOriginal = FXCollections.observableArrayList();
    ;
    public Product original = new Product(0, " ", 0, 0, 0, 0);

    /**
     * @param url
     * @param resourceBundle
     */
    @Override

    public void initialize(URL url, ResourceBundle resourceBundle) {
    }

    /**
     * @param selectedProductModify sets original values of the selected product to the correct fields also sets the tables with all parts and the associated part table with all associated parts
     */
    public void setProductToModify(Product selectedProductModify) {
        //gets associated parts
        this.selectedProductModify = selectedProductModify;
        partsInProduct = selectedProductModify.getPartsInProduct();
        original = selectedProductModify;
        partsInProductOriginal.addAll(partsInProduct);
        //setting text fields
        int id = selectedProductModify.getId();
        String name = selectedProductModify.getName();
        int inventory = selectedProductModify.getStock();
        Double price = selectedProductModify.getPrice();
        int max = selectedProductModify.getMax();
        int min = selectedProductModify.getMin();
        ModifyProductFormMainTFID.setText(String.valueOf(id));
        ModifyProductFormMainTFInv.setText(String.valueOf(inventory));
        ModifyProductFormMainTFMin.setText(String.valueOf(min));
        ModifyProductFormMainTFMax.setText(String.valueOf(max));
        ModifyProductFormMainTFName.setText(name);
        ModifyProductFormMainTFPrice.setText(String.valueOf(price));
        //populates part table with all available parts
        ModifyProductFormAddTable.setItems(Inventory.getPartInventory());
        ModifyProductFormAddTablePartID.setCellValueFactory(new PropertyValueFactory<>("id"));
        ModifyProductFormAddTablePartName.setCellValueFactory(new PropertyValueFactory<>("name"));
        ModifyProductFormAddTableInvLevel.setCellValueFactory(new PropertyValueFactory<>("stock"));
        ModifyProductFormAddTablePriceCostperUnit.setCellValueFactory(new PropertyValueFactory<>("price"));
        //populates associated parts with parts in partsInProduct list
        ModifyProductFormRemovePartTable.setItems(partsInProduct);
        ModifyProductFormRemovePartTablePartID.setCellValueFactory(new PropertyValueFactory<>("id"));
        ModifyProductFormRemovePartTablePartName.setCellValueFactory(new PropertyValueFactory<>("name"));
        ModifyProductFormRemovePartTableInvLevel.setCellValueFactory(new PropertyValueFactory<>("stock"));
        ModifyProductFormRemovePartTablePriceCostperUnit.setCellValueFactory(new PropertyValueFactory<>("price"));
    }

    /**
     * @param actionEvent uses the search parts method in Inventory to repopulate the part table with search results
     */
    public void OnSearchPartIDName(ActionEvent actionEvent) {
        String searchedPart = ModifyProductFormMainSearchPartIDName.getText();
        ObservableList partsFound = Inventory.searchParts(searchedPart);
        ModifyProductFormAddTable.setItems(partsFound);
    }

    /**
     * @param actionEvent Throws error message if no part is clicked
     *                    If part selected adds it to the associated parts table and also to the parts in products list
     */

    public void OnAddButtonClick(ActionEvent actionEvent) {
        Part partAssociated = (Part) ModifyProductFormAddTable.getSelectionModel().getSelectedItem();
        if (partAssociated != null) {
            partsInProduct.add(partAssociated);
            ModifyProductFormRemovePartTable.setItems(partsInProduct);
            ModifyProductFormRemovePartTablePartID.setCellValueFactory(new PropertyValueFactory<>("id"));
            ModifyProductFormRemovePartTablePartName.setCellValueFactory(new PropertyValueFactory<>("name"));
            ModifyProductFormRemovePartTableInvLevel.setCellValueFactory(new PropertyValueFactory<>("stock"));
            ModifyProductFormRemovePartTablePriceCostperUnit.setCellValueFactory(new PropertyValueFactory<>("price"));
        } else {
            Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
            alert.setTitle("Alert");
            alert.setContentText("Select a Part to add.");
            alert.showAndWait();
        }
    }

    /**
     * @param actionEvent Removes associated part form the table and the parts in product list
     */
    public void OnRemoveButtonClick(ActionEvent actionEvent) {
        Part partToRemove = (Part) ModifyProductFormRemovePartTable.getSelectionModel().getSelectedItem();
        if (partToRemove != null) {
            //Throws a warning box up to confirm removal of associated part.
            Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
            alert.setTitle("Alert");
            alert.setContentText("Are you sure you want to remove this part?");
            Optional<ButtonType> result = alert.showAndWait();
            if (result.isPresent() && result.get() == ButtonType.OK) {
                partsInProduct.remove(partToRemove);
                ModifyProductFormRemovePartTable.setItems(partsInProduct);
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
     * @throws IOException resets list in the selected products associated parts in products list.
     *                     Also, closes modify parts window and opens main controller window
     */

    public void OnCancelButtonClick(ActionEvent actionEvent) throws IOException {
        Alert alertcancel = new Alert(Alert.AlertType.CONFIRMATION);
        alertcancel.setTitle("Alert");
        alertcancel.setContentText("Are you sure you want to cancel?");
        Optional<ButtonType> resultcancel = alertcancel.showAndWait();
        if (resultcancel.isPresent() && resultcancel.get() == ButtonType.OK) {
            partsInProduct = partsInProductOriginal;
            selectedProductModify.setPartsInProduct(partsInProduct);
            selectedProductModify = original;
            Stage stage = (Stage) ((Button) actionEvent.getSource()).getScene().getWindow();
            Object scene = FXMLLoader.load(getClass().getResource("/com/example/projectc482/Main Form.fxml"));
            stage.setScene(new Scene((Parent) scene));
            stage.show();
        }
    }

    /**
     * @param actionEvent updates selected product and partsinproduct list
     */
    public void OnSaveButtonClick(ActionEvent actionEvent) {
        //sets selected product with new values
        if (ModifyProductFormMainTFName.getText().isEmpty()) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Alert");
            alert.setContentText("No name given." +
                    "\n\nPlease provide a name.");
            alert.showAndWait();
        } else if (ModifyProductFormMainTFInv.getText().isEmpty()) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Alert");
            alert.setContentText("No stock amount given." +
                    "\n\nPlease provide a stock amount.");
            alert.showAndWait();
        } else if (ModifyProductFormMainTFMax.getText().isEmpty()) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Alert");
            alert.setContentText("No max stock given." +
                    "\n\nPlease provide a stock maximum.");
            alert.showAndWait();
        } else if (ModifyProductFormMainTFMin.getText().isEmpty()) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Alert");
            alert.setContentText("No min stock given." +
                    "\n\nPlease provide a stock minimum.");
            alert.showAndWait();
        } else if (ModifyProductFormMainTFPrice.getText().isEmpty()) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Alert");
            alert.setContentText("No price given." +
                    "\n\nPlease provide a price amount.");
            alert.showAndWait();
        }
        else {
            String name = ModifyProductFormMainTFName.getText();
            Integer id = Integer.valueOf(ModifyProductFormMainTFID.getText());
            try {
                int stock = Integer.parseInt(ModifyProductFormMainTFInv.getText());
                int min = Integer.parseInt(ModifyProductFormMainTFMin.getText());
                int max = Integer.parseInt(ModifyProductFormMainTFMax.getText());
                double price = Double.parseDouble(ModifyProductFormMainTFPrice.getText());
                if (stock < min || stock > max) {
                    Alert alert = new Alert(Alert.AlertType.ERROR);
                    alert.setTitle("Alert");
                    alert.setContentText("Stock amount is below the minimum amount allowed." +
                            "\n\nOr" + "\n\nStock amount is greater than maximum amount allowed." +
                            "\n\nAlso, make sure the Min amount is not greater than the Max amount.");
                    Optional<ButtonType> result = alert.showAndWait();
                    if (result.isPresent() && result.get() == ButtonType.OK) {
                        return;
                    }
                } else if (min > max) {
                    Alert alert = new Alert(Alert.AlertType.ERROR);
                    alert.setTitle("Alert");
                    alert.setContentText("Min is greater than Max");
                    Optional<ButtonType> result = alert.showAndWait();
                    if (result.isPresent() && result.get() == ButtonType.OK) {
                        return;
                    }
                }
                Product newProduct = new Product(id,name,price,stock, min, max);
                newProduct.setPartsInProduct(partsInProduct);
                Inventory.addProduct(newProduct);
                Inventory.productInventory.remove(selectedProductModify);
            } catch (NumberFormatException a) {
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setTitle("Alert");
                alert.setContentText("Max, min, and stock must be whole number Integers.\nCost must be an integer or a decimal." +
                        "\n\nPlease provide an Integer value for Min, Max, or Stock."
                        + "\n\nPlease provide a decimal or whole number Integer for Cost.");
                Optional<ButtonType> result = alert.showAndWait();
                if (result.isPresent() && result.get() == ButtonType.OK) {
                    return;
                }
            }
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
}

