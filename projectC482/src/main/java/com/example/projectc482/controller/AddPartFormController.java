package com.example.projectc482.controller;

import com.example.projectc482.Model.*;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventType;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import javafx.stage.Window;
import javafx.stage.WindowEvent;

import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.ResourceBundle;

/**
 * This class uses methods within it to create part objects using the Part class
 */
public class AddPartFormController implements Initializable {
    public AnchorPane AddPartWindow;
    public Button AddPartCancelButton;
    public Button AddPartSaveButton;
    public Label AddPartLabel;
    public RadioButton AddPartRadInHouse;
    public RadioButton AddPartRadOutSource;
    public Label AddPartLabelID;
    public Label AddPartLabelName;
    public Label AddPartLabelInventory;
    public Label AddPartLabelPriceCost;
    public Label AddPartLabelMax;
    public Label AddPartLabelMachID;
    public Label AddPartLabelMin;
    public TextField AddPartTextFieldID;
    public TextField AddPartTextFieldMin;
    public TextField AddPartTextFieldName;
    public TextField AddPartTextFieldinventory;
    public TextField AddPartTextFieldPriceCost;
    public TextField AddPartTextFieldMax;
    public TextField AddPartTextFieldMachID;

    /**
     * @param url
     * @param resourceBundle
     * generates a new part id by checking all parts and uses the next immediate integer.
     * also, populates the part id Text Field.
     */
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        int partID = 1;
        List<Integer> partsIDs = Inventory.getPartIDList();
        while (partsIDs.contains(partID)) {
            partID += 1;
        }
        AddPartTextFieldID.setText(String.valueOf(partID));
    }

    /**
     * @param actionEvent
     * Changes the AddPartLabelMachID to MachineID  if the radio in house button is selected.
     */
    public void InHouseSelected(ActionEvent actionEvent) {
        if (AddPartTextFieldMachID == null){
            System.out.println("No machine ID given.");
        }
        else {
            AddPartLabelMachID.setText("Machine ID");
        }
    }

    /**
     * @param actionEvent
     * Changes the AddPartLabelMachID to Company name  if the radio outsource button is selected.
     */
    public void OutSourcedSelected(ActionEvent actionEvent) {
        if (AddPartTextFieldMachID == null){
            System.out.println("No company name given.");
        }
        else {
            AddPartLabelMachID.setText("Company Name");
        }
    }

    /**
     * @param actionEvent
     * @throws IOException
     * Cancel button closes the add part window and opens up the main controller window.
     */
    public void OnCancelPartButtonClick(ActionEvent actionEvent) throws IOException {
        Parent root = null;
        try {
            root = FXMLLoader.load(getClass().getResource("/com/example/projectc482/Main Form.fxml"));
            Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
            alert.setTitle("Alert");
            alert.setContentText("Are you sure you want to cancel?");
            Optional<ButtonType> result = alert.showAndWait();
            if(result.isPresent() && result.get() == ButtonType.OK) {
                Scene scene = new Scene(root);
                Stage mainForm = (Stage) ((Node)actionEvent.getSource()).getScene().getWindow();
                mainForm.setScene(scene);
                mainForm.show();
            }
        } catch (IOException e) {
            throw new RuntimeException(e);

        }

    }

    /**
     * @param actionEvent
     * creates the part and checks fields to make sure they are properly filled out and adds the part to the part list in the Inventory class.
     */
    public void OnSavePartButtonClick(ActionEvent actionEvent) {
        if (AddPartTextFieldName.getText().equals("Name")) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Alert");
            alert.setContentText("No name given." +
                    "\n\nPlease provide a name.");
            alert.showAndWait();
        }
        else if (AddPartTextFieldinventory.getText().equals("Inventory")) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Alert");
            alert.setContentText("No stock amount given." +
                    "\n\nPlease provide a stock amount.");
            alert.showAndWait();
        } else if (AddPartTextFieldMax.getText().equals("Maximum")) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Alert");
            alert.setContentText("No max stock given." +
                    "\n\nPlease provide a stock maximum.");
            alert.showAndWait();
        } else if (AddPartTextFieldMin.getText().equals("Minimum")) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Alert");
            alert.setContentText("No min stock given." +
                    "\n\nPlease provide a stock minimum.");
            alert.showAndWait();
        }
        else if (AddPartTextFieldPriceCost.getText().equals("Cost")) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Alert");
            alert.setContentText("No price given." +
                    "\n\nPlease provide a price amount.");
            alert.showAndWait();
            }
        else if (AddPartTextFieldMachID.getText().equals("")){
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Alert");
            alert.setContentText("No company name or machine id given." +
                    "\n\nPlease provide a company name or machine id.");
            alert.showAndWait();
        }
         else {
            int id = Integer.parseInt(AddPartTextFieldID.getText());
            String name = AddPartTextFieldName.getText();
            //checks to see if name for part is already in use
            for (Part part : Inventory.partInventory) {
                if (part.getName().equals(name)) {
                    Alert alert = new Alert(Alert.AlertType.ERROR);
                    alert.setTitle("Alert");
                    alert.setContentText("Name in use." +
                            "\n\nPlease provide a different name.");
                    Optional<ButtonType> result = alert.showAndWait();
                    if(result.isPresent() && result.get() == ButtonType.OK) {
                        return;
                    }
                }
            }
            //sets variables to be used to create the part object
            try {
                int stock = Integer.parseInt(AddPartTextFieldinventory.getText());
                int min = Integer.parseInt(AddPartTextFieldMin.getText());
                int max = Integer.parseInt(AddPartTextFieldMax.getText());
                double price = Double.parseDouble(AddPartTextFieldPriceCost.getText());
                if (stock < min || stock > max) {
                    Alert alert = new Alert(Alert.AlertType.ERROR);
                    alert.setTitle("Alert");
                    alert.setContentText("Stock amount is below the minimum amount allowed." +
                            "\n\nOr" + "\n\nStock amount is greater than maximum amount allowed." +
                            "\n\nAlso, make sure the Min amount is not greater than the Max amount.");
                    Optional<ButtonType> result = alert.showAndWait();
                    if(result.isPresent() && result.get() == ButtonType.OK) {
                        return;
                    }
                } else if (min > max) {
                    Alert alert = new Alert(Alert.AlertType.ERROR);
                    alert.setTitle("Alert");
                    alert.setContentText("Min is greater than Max");
                    Optional<ButtonType> result = alert.showAndWait();
                    if(result.isPresent() && result.get() == ButtonType.OK) {
                        return;
                    }
                }
                //creates part as either inhouse or outsourced.
                if (AddPartRadInHouse.isSelected()) {
                    try {
                        int machineID = Integer.parseInt(AddPartTextFieldMachID.getText());
                        Part temp = new InHouse(id, name, price, stock, min, max, machineID);
                        Inventory.addPart(temp);
                    }
                    catch (NumberFormatException a){
                        Alert alert = new Alert(Alert.AlertType.ERROR);
                        alert.setTitle("Alert");
                        alert.setContentText("Machine Id needs to be an Integer." +
                                "\n\nPlease provide an Integer value.");
                        Optional<ButtonType> result = alert.showAndWait();
                        if(result.isPresent() && result.get() == ButtonType.OK) {
                            return;
                        }
                    }
                }
                if (AddPartRadOutSource.isSelected()) {
                    String machineID = AddPartTextFieldMachID.getText();
                    Part temp = new Outsourced(id, name, price, stock, min, max, machineID);
                    Inventory.addPart(temp);
                }
            }
            catch(NumberFormatException a){
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
            //checks to make sure min, max, and inventory input works.

            //closes this window and opens up the main controller window.
            Parent root = null;
            try {
                root = FXMLLoader.load(getClass().getResource("/com/example/projectc482/Main Form.fxml"));
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
            Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
            alert.setTitle("Alert");
            alert.setContentText("Are you sure you want to save this part?");
            Optional<ButtonType> result = alert.showAndWait();
            if(result.isPresent() && result.get() == ButtonType.OK) {
                Scene scene = new Scene(root);
                Stage mainForm = (Stage) ((Node) actionEvent.getSource()).getScene().getWindow();
                mainForm.setScene(scene);
                mainForm.show();
            }
        }
    }
}
