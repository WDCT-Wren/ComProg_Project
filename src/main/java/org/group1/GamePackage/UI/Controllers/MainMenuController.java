package org.group1.GamePackage.UI.Controllers;

import com.almasb.fxgl.dsl.FXGL;
import com.almasb.fxgl.dsl.views.SelfScrollingBackgroundView;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.layout.*;
import org.group1.GamePackage.Handlers.GameMechanics;
import org.group1.GamePackage.UI.Interfaces.MenuInterface;

import java.io.IOException;

public class MainMenuController {
    private MenuInterface menu;

    @FXML
    private Pane backgroundContainer;

    private final int GAME_WIDTH = FXGL.getAppWidth();
    private final int GAME_HEIGHT = FXGL.getAppHeight();

    /**
     * Initializes the MainMenuController by loading the background images
     */
    public void initialize() {
        loadBackground();
    }

    public void setMenu(MenuInterface menu) {
        this.menu = menu;
    }

    /**
     * Starts the game by calling the GameMechanics's restartGame method
     */
    @FXML
    private void playGame() {
        GameMechanics.restartGame();
    }

    /**
     * Exits the game by calling the FXGL's exit method
     */
    @FXML
    private void quitGame() {
        FXGL.getGameController().exit();
    }

    /**
     * Loads the background images into the backgroundContainer
     */
    private void loadBackground() {
        int w;
        var stars1      = scrollLayer("background_stars1.png",      GAME_WIDTH, GAME_HEIGHT, 10,  0,   0);
        var stars2      = scrollLayer("background_stars2.png",      GAME_WIDTH, GAME_HEIGHT, 15,  0,   0);
        var stars3      = scrollLayer("background_stars3.png",      GAME_WIDTH, GAME_HEIGHT, 13,  0,   0);
        var cloud1      = scrollLayer("background_cloud1.png",      GAME_WIDTH, GAME_HEIGHT, 20,  0,   0);
        var cloud2      = scrollLayer("background_cloud2.png",      GAME_WIDTH, GAME_HEIGHT, 30,  0, 100);
        var mist1       = scrollLayer("background_mist1.png",       GAME_WIDTH, GAME_HEIGHT, 30,  0, 500);
        var hills       = scrollLayer("background_hills.png",       GAME_WIDTH, GAME_HEIGHT,  5,  0, 500);
        var forest      = scrollLayer("background_forest.png",      GAME_WIDTH, GAME_HEIGHT, 10,  0, 550);
        var largeForest = scrollLayer("background_large_forest.png",GAME_WIDTH, GAME_HEIGHT, 10,  0, 550);
        backgroundContainer.getChildren().addAll(stars1, stars2, stars3, cloud1, cloud2, mist1, hills, forest, largeForest);
    }

    /**
     * Creates a SelfScrollingBackgroundView
     */
    private SelfScrollingBackgroundView scrollLayer(String texture, int w, int h, double speed, double x, double y) {
        var view = new SelfScrollingBackgroundView(
                FXGL.getAssetLoader().loadTexture(texture).getImage(), w, h, speed
        );
        view.setLayoutX(x);
        view.setLayoutY(y);
        return view;
    }

    /**
     * Opens the AboutWindow by accessing the MenuInterface's openAboutWindow method
     */
    @FXML
    void openAboutWindow() throws IOException {
        menu.openAboutWindow();
    }

    /**
     * Opens the InstructionsWindow by accessing the MenuInterface's openInstructionsWindow method
     */
    @FXML
    void openInstructionsWindow() throws IOException {
        menu.openInstructionsWindow();
    }
}
