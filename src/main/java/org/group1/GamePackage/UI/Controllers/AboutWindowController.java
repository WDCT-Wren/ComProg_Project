package org.group1.GamePackage.UI.Controllers;


import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.stage.Stage;

public class AboutWindowController {

    @FXML
    private Button closeButton;

    /**
     * Closes the current window
     */
    @FXML
    void closeWindow() {
        ((Stage)closeButton.getScene().getWindow()).close();
    }
}

