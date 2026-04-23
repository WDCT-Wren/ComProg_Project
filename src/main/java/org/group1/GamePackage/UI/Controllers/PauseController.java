package org.group1.GamePackage.UI.Controllers;

import javafx.fxml.FXML;
import org.group1.GamePackage.UI.PauseInterface;

public class PauseController {
    private PauseInterface menu;

    public void setMenu(PauseInterface menu) {
        this.menu = menu;
    }

    /**
     * Resumes the game by calling the PauseInterface's resume method inherited from FXGLMenu
     */
    @FXML
    private void onResume() { menu.resume(); }

    /**
     * Exits the game to the main menu
     * using the PauseInterface's exitToMainMenu method inherited from FXGLMenu
     */
    @FXML
    private void onMainMenu() { menu.exitToMainMenu(); }
}
