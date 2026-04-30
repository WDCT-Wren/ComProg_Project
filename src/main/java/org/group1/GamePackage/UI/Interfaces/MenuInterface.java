package org.group1.GamePackage.UI.Interfaces;

import java.io.IOException;

import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.stage.Modality;
import org.group1.GamePackage.Music.AudioManager;

import com.almasb.fxgl.app.scene.FXGLMenu;
import com.almasb.fxgl.app.scene.MenuType;

import javafx.scene.Scene;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import org.group1.GamePackage.UI.Controllers.MainMenuController;

public class MenuInterface extends FXGLMenu {

    public MenuInterface() throws IOException {
        super(MenuType.MAIN_MENU);

        //call the fxml file
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/Scenes/MainMenu.fxml"));
        Parent root = loader.load();

        //set the controller
        MainMenuController controller = loader.getController();
        controller.setMenu(this);

        AudioManager.playMenuMusic();
        getContentRoot().getChildren().add(root);
    }

    /**
     * Opens the about window
     * <br><br>
     * creates a new stage to make a popup window that shows the game's information
     */
    public void openAboutWindow() throws IOException {
        // call the new FXML file
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/Scenes/AboutWindow.fxml"));
        Parent root = loader.load();

        // Create the new Stage
        Stage popupStage = new Stage();
        popupStage.setTitle("About window");
        popupStage.setScene(new Scene(root));
        popupStage.initStyle(StageStyle.TRANSPARENT);

        // Make it a modal window
        popupStage.initModality(Modality.APPLICATION_MODAL);

        // Show and wait for user action
        popupStage.showAndWait();
    }

    /**
     * Method to open the instruction window
     * <ul>creates a new stage to make a popup window that shows the game's controls as well as:
     *      <li>How to play the game</li>
     *      <li>Information about Bullets that the player can use</li>
     *      <li>Information about enemies (i.e., normal enemies, miniboss, and the main boss)</li>
     * </ul>
     * Uses {@code pagination} control to show the different pages of the window
     */
    public void openInstructionsWindow() throws IOException {
        // call the new FXML file
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/Scenes/InstructionsWindow.fxml"));
        Parent root = loader.load();

        // Create the new Stage
        Stage popupStage = new Stage();
        popupStage.setTitle("Instructions Window");
        popupStage.setScene(new Scene(root));
        popupStage.initStyle(StageStyle.TRANSPARENT);

        // Make it a modal window
        popupStage.initModality(Modality.APPLICATION_MODAL);

        // Show and wait for user action
        popupStage.showAndWait();
    }
}
