
package com.example.projectc482.controller;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;

/**
 * JAVADOC FOLDER LOCATION: I put the javadoc folder in this project under the src folder named javadoc.
 * FUTURE ENHANCEMENT: I wanted to implement specific method/s that took data from the product/part
 * and extracted the data for the text fields rather than doing it within another method. This
 * would clean-up the look of the code making it more readable.
 * Also, I would create an alert UI method that I would call when needed so I can make my code smaller and save me time.
 * Main class of the app.
 */
public class Main extends Application {
    /**
     * @param stage
     * @throws IOException
     * Initializes the program and opens up the Main Controller Window.
     * No logical/runtime errors.
     */
    @Override
    public void start(Stage stage) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(Main.class.getResource("/com/example/projectc482/Main Form.fxml"));
        Scene scene = new Scene(fxmlLoader.load(), 1050, 450);
        stage.setTitle("Inventory");
        stage.setScene(scene);
        stage.show();
    }

    /**
     * @param args
     * Launches the program.
     */
    public static void main(String[] args) {
        launch();
    }
}
