package org.group1.GamePackage.UI;

import com.almasb.fxgl.app.scene.FXGLMenu;
import com.almasb.fxgl.app.scene.MenuType;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import org.group1.GamePackage.UI.Controllers.PauseController;

import java.io.IOException;

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
    public void exitToMainMenu() { fireExitToMainMenu(); }
}
