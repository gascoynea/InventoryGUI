package com.example.projectc482.controller;

import com.example.projectc482.Model.InHouse;
import com.example.projectc482.Model.Inventory;
import com.example.projectc482.Model.Outsourced;
import com.example.projectc482.Model.Part;
import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.util.Optional;
import java.util.ResourceBundle;

/**
 * This class changes already created parts using methods within itself and updates the Part list in Inventory class.
 */

public class ModifyPartFormController implements Initializable {
    public AnchorPane ModifyPartFormWindow;
    public Button ModifyPartFormButtonCancel;
    public Button ModifyPartFormButtonSave;
    public Label ModifyPartFormLabelModifyPart;
    public RadioButton ModifyPartFormRadInHouse;
    public RadioButton ModifyPartFormRadOutsourced;
    public Label ModifyPartFormLabelID;
    public Label ModifyPartFormLabelName;
    public Label ModifyPartFormLabelInventory;
    public Label ModifyPartFormLabelPriceCost;
    public Label ModifyPartFormLabelMax;
    public Label ModifyPartFormLabelMachID;
    public Label ModifyPartFormLabelMin;
    public TextField ModifyPartFormTFID;
    public TextField ModifyPartFormTFMin;
    public TextField ModifyPartFormTFName;
    public TextField ModifyPartFormTFInventory;
    public TextField ModifyPartFormTFPriceCost;
    public TextField ModifyPartFormTFMax;
    public TextField ModifyPartFormTFMachID;
    //creates a public variable of the part object to be manipulated.
    public Part selectedPartModify;

    /**
     * @param url
     * @param resourceBundle
     */
    public void initialize(URL url, ResourceBundle resourceBundle) {
    }

    /**
     * sets the ModifyPartFormLabelMachID based on radio button selected
     */

    public void InHouseSelected() {
        ModifyPartFormLabelMachID.setText("Machine ID");
        InHouse temp = null;
    }

    /**
     * sets the ModifyPartFormLabelMachID based on radio button selected
     */
    public void OutSourcedSelected() {
        ModifyPartFormLabelMachID.setText("Company Name");
        Outsourced temp = null;
    }

    /**
     * @param selectedPartModify Gets all the information from the part to be modified so we can fill out all text fields.
     */
    public void setPartToModify(Part selectedPartModify) {
        //Grabs each variable in the part object that was selected using methods from the Part Class.
        //sets the Text fields with the variables.
        this.selectedPartModify = selectedPartModify;
        int id = selectedPartModify.getId();
        String name = selectedPartModify.getName();
        int inventory = selectedPartModify.getStock();
        Double price = selectedPartModify.getPrice();
        int max = selectedPartModify.getMax();
        int min = selectedPartModify.getMin();
        ModifyPartFormTFID.setText(String.valueOf(id));
        ModifyPartFormTFInventory.setText(String.valueOf(inventory));
        ModifyPartFormTFMin.setText(String.valueOf(min));
        ModifyPartFormTFMax.setText(String.valueOf(max));
        ModifyPartFormTFName.setText(name);
        ModifyPartFormTFPriceCost.setText(String.valueOf(price));
        //sets the machine name or company name dependent if part is in house or out sourced.
        if (selectedPartModify instanceof InHouse) {
            ModifyPartFormRadInHouse.setSelected(true);
            int machineID = ((InHouse) selectedPartModify).getMachineID();
            ModifyPartFormTFMachID.setText(String.valueOf(machineID));
        } else {
            ModifyPartFormRadOutsourced.setSelected(true);
            String companyName = ((Outsourced) selectedPartModify).getCompanyName();
            ModifyPartFormTFMachID.setText(companyName);
            ModifyPartFormLabelMachID.setText("Company Name");
        }
    }

    /**
     * @param actionEvent sets the values in the part object that is being modified.
     */
    public void onCLick(ActionEvent actionEvent) {
        if (ModifyPartFormTFName.getText().isEmpty()) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Alert");
            alert.setContentText("No name given." +
                    "\n\nPlease provide a name.");
            alert.showAndWait();
        } else if (ModifyPartFormTFInventory.getText().isEmpty()) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Alert");
            alert.setContentText("No stock amount given." +
                    "\n\nPlease provide a stock amount.");
            alert.showAndWait();
        } else if (ModifyPartFormTFMax.getText().isEmpty()) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Alert");
            alert.setContentText("No max stock given." +
                    "\n\nPlease provide a stock maximum.");
            alert.showAndWait();
        } else if (ModifyPartFormTFMin.getText().isEmpty()) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Alert");
            alert.setContentText("No min stock given." +
                    "\n\nPlease provide a stock minimum.");
            alert.showAndWait();
        } else if (ModifyPartFormTFPriceCost.getText().isEmpty()) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Alert");
            alert.setContentText("No price given." +
                    "\n\nPlease provide a price amount.");
            alert.showAndWait();
        } else if (ModifyPartFormTFMachID.getText().isEmpty()) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Alert");
            alert.setContentText("No company name or machine id given." +
                    "\n\nPlease provide a company name or machine id.");
            alert.showAndWait();
        } else {
            String name = ModifyPartFormTFName.getText();
            try {
                int stock = Integer.parseInt(ModifyPartFormTFInventory.getText());
                int min = Integer.parseInt(ModifyPartFormTFMin.getText());
                int max = Integer.parseInt(ModifyPartFormTFMax.getText());
                double price = Double.parseDouble(ModifyPartFormTFPriceCost.getText());
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
                //creates part as either inhouse or outsourced.
                if (ModifyPartFormRadInHouse.isSelected()) {
                    try {
                        int id = Integer.parseInt(ModifyPartFormTFID.getText());
                        int machineID = Integer.parseInt(ModifyPartFormTFMachID.getText());
                        Part newPart = new InHouse(id, name, price, stock, min, max, machineID);
                        Inventory.partInventory.add(newPart);
                        Inventory.partInventory.remove(selectedPartModify);
                    } catch (NumberFormatException a) {
                        Alert alert = new Alert(Alert.AlertType.ERROR);
                        alert.setTitle("Alert");
                        alert.setContentText("Machine Id needs to be an Integer." +
                                "\n\nPlease provide an Integer value.");
                        Optional<ButtonType> result = alert.showAndWait();
                        if (result.isPresent() && result.get() == ButtonType.OK) {
                            return;
                        }
                    }
                }
                if (ModifyPartFormRadOutsourced.isSelected()) {
                    String machineID = ModifyPartFormTFMachID.getText();
                    int id = Integer.parseInt(ModifyPartFormTFID.getText());
                    Part newPart = new Outsourced(id, name, price, stock, min, max, machineID);
                    Inventory.partInventory.add(newPart);
                    Inventory.partInventory.remove(selectedPartModify);
                }
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
            Scene scene = new Scene(root);
            Stage mainForm = (Stage) ((Node) actionEvent.getSource()).getScene().getWindow();
            mainForm.setScene(scene);
            mainForm.show();
        }
    }
    /**
     * @param actionEvent
     * sets the values in the part object that is being modified.
     */
    public void OnCancelClick(ActionEvent actionEvent) {
        Alert alertcancel = new Alert(Alert.AlertType.CONFIRMATION);
        alertcancel.setTitle("Alert");
        alertcancel.setContentText("Are you sure you want to cancel?");
        Optional<ButtonType> resultcancel = alertcancel.showAndWait();
        if (resultcancel.isPresent() && resultcancel.get() == ButtonType.OK) {
            Parent root = null;
            try {
                root = FXMLLoader.load(getClass().getResource("/com/example/projectc482/Main Form.fxml"));
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
            Scene scene = new Scene(root);
            Stage mainForm = (Stage) ((Node) actionEvent.getSource()).getScene().getWindow();
            mainForm.setScene(scene);
            mainForm.show();
        }
    }
}