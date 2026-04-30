package org.group1.GamePackage.UI;

import com.almasb.fxgl.app.scene.FXGLMenu;
import com.almasb.fxgl.app.scene.MenuType;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import org.group1.GamePackage.Music.AudioManager;
import org.group1.GamePackage.UI.Controllers.PauseController;

import java.io.IOException;

import static com.almasb.fxgl.dsl.FXGL.getDialogService;
import static com.almasb.fxgl.dsl.FXGL.getGameController;

public class PauseInterface extends FXGLMenu {

    public PauseInterface() throws IOException {
        super(MenuType.GAME_MENU);

        //call the fxml file
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/Scenes/PauseMenu.fxml"));
        Parent root = loader.load();

        //set the controller
        PauseController controller = loader.getController();
        controller.setMenu(this);

        getContentRoot().getChildren().add(root);
    }

    //getters for the buttons
    public void resume() { fireResume(); }

    /**
     * Invokes a confirmation dialog to confirm if the user really wants to exit the game
     * <br><br>
     * Basically overrides the default {@code exitToMainMenu} method in {@code FXGLMenu} that doesn't stop the music.
     */
    public void exitToMainMenu() {
        getDialogService().showConfirmationBox("Are you sure you want to end the adventure early?", (Boolean answer) -> {
            if (answer) {
                // automatically goes to main menu when confirmed
                getGameController().gotoMainMenu();

                // Game music stops, and menu music plays
                AudioManager.stopAll();
                AudioManager.playMenuMusic();
            }
        });
    }
}
