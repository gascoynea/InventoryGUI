package com.example.projectc482.controller;

import com.example.projectc482.Model.*;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.AnchorPane;
import javafx.scene.shape.Rectangle;
import javafx.stage.Stage;
import java.io.IOException;
import java.net.URL;
import java.util.Optional;
import java.util.ResourceBundle;

/**
 * This is the Main hub for the GUI and acts as the intermediary for the rest of the windows.
 * This class is what is used to display the main window and also holds the methods for opening all other windows.
 */
public class MainController implements Initializable {

    public AnchorPane MainWindow;
    public Rectangle MainPartWindow;
    public Button MainAddPartButton;
    public Button MainModifyPartButton;
    public Button MainDeletePartButton;
    public Label InvManSysLabel;
    public TableView MainPartTable;
    public TableColumn MainPartIDCol;
    public TableColumn MainPartNameCol;
    public TableColumn MainPartInvLvlCol;
    public TableColumn MainPartPriceCostCol;
    public TextField MainPartSearchBox;
    public Rectangle MainProductWindow;
    public TableView MainProductTable;
    public TableColumn MainProductIDCol;
    public TableColumn MainProductNameCol;
    public TableColumn MainProductInvLvlCol;
    public TableColumn MainProductCostCol;
    public Button MainDeleteProductButton;
    public Button MainModifyProductButton;
    public Button MainAddProductButton;
    public TextField MainProductSearchBox;
    public Label MainProductsLabel;
    public Label MainPartsLabel;

    /**
     * @param url
     * @param resourceBundle
     * This method allows the controller to be initialized.
     * It also sets the part table and the product table w/ 4 columns which are for ID, Name, Inventory, Price.
     */

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        MainPartTable.setItems(Inventory.getPartInventory());

        MainPartIDCol.setCellValueFactory(new PropertyValueFactory<>("id"));
        MainPartNameCol.setCellValueFactory(new PropertyValueFactory<>("name"));
        MainPartInvLvlCol.setCellValueFactory(new PropertyValueFactory<>("stock"));
        MainPartPriceCostCol.setCellValueFactory(new PropertyValueFactory<>("price"));

        MainProductTable.setItems(Inventory.getProductInventory());

        MainProductIDCol.setCellValueFactory(new PropertyValueFactory<>("id"));
        MainProductNameCol.setCellValueFactory(new PropertyValueFactory<>("name"));
        MainProductInvLvlCol.setCellValueFactory(new PropertyValueFactory<>("stock"));
        MainProductCostCol.setCellValueFactory(new PropertyValueFactory<>("price"));
    }

    /**
     * @param actionEvent
     * @throws IOException
     * The add button just opens up the add part window.
     * I had an issue where it would leave the main window up as well but I was able to
     * get that to only open the Add part window.
     */

    public void onAddPartButton(ActionEvent actionEvent) throws IOException {
        Stage stage = (Stage)((Button)actionEvent.getSource()).getScene().getWindow();
        Object scene = FXMLLoader.load(getClass().getResource("/com/example/projectc482/Add Part Form.fxml"));
        stage.setScene(new Scene((Parent) scene));
        stage.show();
    }

    /**
     * @param actionEvent
     * The add button just opens up the Modify part window.
     * I had an issue where it would leave the main window up as well but I was able to
     * get that to only open the modify part window.
     */

    public void onModifyPartButton(ActionEvent actionEvent) {
        try {
            Part selectedPartModify = (Part) MainPartTable.getSelectionModel().getSelectedItem();
                if(selectedPartModify == null){
                    Alert alert_Null = new Alert(Alert.AlertType.ERROR);
                    alert_Null.setTitle("Alert");
                    alert_Null.setContentText("Select a part to open up the modify part menu.");
                    alert_Null.showAndWait();
                }
                else {
                    Stage stage = (Stage) ((Button) actionEvent.getSource()).getScene().getWindow();
                    FXMLLoader loader = new FXMLLoader(getClass().getResource("/com/example/projectc482/Modify Part Form.fxml"));
                    Object scene = loader.load();
                    ModifyPartFormController controller = loader.getController();
                    controller.setPartToModify(selectedPartModify);
                    stage.setTitle("Modify Part");
                    stage.setScene(new Scene((Parent) scene));
                    stage.show();
                }
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * @param actionEvent
     * Deletes a selected Part while clicked.
     * Also warns you if nothing is selected.
     */

    public void onDeletePartButton(ActionEvent actionEvent) {
        Part SelectedPartDel = (Part) MainPartTable.getSelectionModel().getSelectedItem();

        if (SelectedPartDel == null) {
            Alert alert_Null = new Alert(Alert.AlertType.ERROR);
            alert_Null.setTitle("Alert");
            alert_Null.setContentText("You have not selected a part to Delete. " +
                    "\nPlease select a part to delete.");
            alert_Null.showAndWait();
        }
        else {
            //Throws a warning box up to confirm deletion of part
            Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
            alert.setTitle("Alert");
            alert.setContentText("Are you sure you want to delete this part?");
            Optional<ButtonType> result = alert.showAndWait();
            if(result.isPresent() && result.get() == ButtonType.OK) {
                Inventory.partInventory.remove(SelectedPartDel);
                MainPartTable.setItems(Inventory.getPartInventory());
            }
        }
    }

    /**
     * @param actionEvent
     * Deletes a selected product while clicked.
     * Also warns you if nothing is selected.
     */

    public void onDeleteProductButton(ActionEvent actionEvent) {
        Product SelectedProductDel = (Product) MainProductTable.getSelectionModel().getSelectedItem();

        if (SelectedProductDel == null) {
            Alert alert_Null = new Alert(Alert.AlertType.ERROR);
            alert_Null.setTitle("Alert");
            alert_Null.setContentText("You have not selected a Product to delete. " +
                    "\nPlease select a Product to delete.");
            alert_Null.showAndWait();
        } else if (SelectedProductDel.getPartsInProduct().size() > 0) {
            Alert alert_Null = new Alert(Alert.AlertType.ERROR);
            alert_Null.setTitle("Alert");
            alert_Null.setContentText("The selected product has associated parts.");
            alert_Null.showAndWait();
        } else {
            //Throws a warning box up to confirm deletion of part
            Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
            alert.setTitle("Alert");
            alert.setContentText("Are you sure you want to delete this product?");
            Optional<ButtonType> result = alert.showAndWait();
            if(result.isPresent() && result.get() == ButtonType.OK) {
                Inventory.productInventory.remove(SelectedProductDel);
                MainProductTable.setItems(Inventory.getProductInventory());
            }
        }
    }

    /**
     * @param actionEvent
     * Opens up the modify product window.
     * It takes the product that is selected on the product table and
     * uses that data to fill in the windows text fields.
     * Also, if nothing is selected it will warn you and will not open up
     * the window
     */

    public void onModifyProductButton(ActionEvent actionEvent) {
        try {
            Product selectedProductModify = (Product) MainProductTable.getSelectionModel().getSelectedItem();
            if(selectedProductModify == null){
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setTitle("Alert");
                alert.setContentText("Please select a Product to open up the Modify Product menu.");
                alert.showAndWait();
            }
            else {
                Stage stage = (Stage) ((Button) actionEvent.getSource()).getScene().getWindow();
                FXMLLoader loader = new FXMLLoader(getClass().getResource("/com/example/projectc482/Modify Product Form.fxml"));
                Object scene = loader.load();
                ModifyProductFormController controller = loader.getController();
                controller.setProductToModify(selectedProductModify);
                stage.setTitle("Modify Product");
                stage.setScene(new Scene((Parent) scene));
                stage.show();
            }
        }
        catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * @param actionEvent
     * @throws IOException
     * Opens up the add product window. With all of this we are using the fxml file which is called.
     */

    public void onAddProductButton(ActionEvent actionEvent) throws IOException {
        Stage stage = (Stage)((Button)actionEvent.getSource()).getScene().getWindow();
        Object scene = FXMLLoader.load(getClass().getResource("/com/example/projectc482/Add Product Form.fxml"));
        stage.setScene(new Scene((Parent) scene));
        stage.show();
    }

    /**
     * @param actionEvent
     * This allows the user to type in a name or partial name get all parts that match.
     * Also works with ID. If you have nothing in the search bar it will repopulate the table with all parts.
     */

    public void onSearchPartBar(ActionEvent actionEvent) {
        String searchedPart = MainPartSearchBox.getText();
        ObservableList partsFound = Inventory.searchParts(searchedPart);
        MainPartTable.setItems(partsFound);
    }

    /**
     * @param actionEvent
     * Same as the onSearchPartBar
     */
    public void onSearchProductBar(ActionEvent actionEvent) {
        String searchedProduct = MainProductSearchBox.getText();
        ObservableList productsFound = Inventory.searchProducts(searchedProduct);
        MainProductTable.setItems(productsFound);
    }

    /**
     * @param actionEvent
     */
    public void OnExitButtonClick(ActionEvent actionEvent) {
        Alert alertcancel = new Alert(Alert.AlertType.CONFIRMATION);
        alertcancel.setTitle("Alert");
        alertcancel.setContentText("Are you sure you want to exit?");
        Optional<ButtonType> resultcancel = alertcancel.showAndWait();
        if (resultcancel.isPresent() && resultcancel.get() == ButtonType.OK) {
            System.exit(0);
        }
    }
}