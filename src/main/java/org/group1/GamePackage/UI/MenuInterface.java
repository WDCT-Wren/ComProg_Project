package org.group1.GamePackage.UI;

import org.group1.GamePackage.Handlers.GameMechanics;
import com.almasb.fxgl.app.scene.FXGLMenu;
import com.almasb.fxgl.app.scene.MenuType;
import com.almasb.fxgl.dsl.FXGL;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

public class MenuInterface extends FXGLMenu {

    public MenuInterface() {
        super(MenuType.MAIN_MENU);

        getContentRoot().getStylesheets().add(
            getClass().getResource("/assets/styles.css").toExternalForm()
        );

        Button START_BUTTON = createButton("Start Game");
        START_BUTTON.setOnAction(click -> GameMechanics.restartGame());

        Button ABOUT_BUTTON = createButton("About");
        ABOUT_BUTTON.setOnAction(click -> openAboutWindow());

        Button INSTRUCTIONS_BUTTON = createButton("Instructions");
        INSTRUCTIONS_BUTTON.setOnAction(click -> openInstructionsWindow());

        Button EXIT_BUTTON = createButton("Exit");
        EXIT_BUTTON.setOnAction(click -> FXGL.getGameController().exit());

        VBox menuLayout = new VBox(20,
            START_BUTTON,
            ABOUT_BUTTON,
            INSTRUCTIONS_BUTTON,
            EXIT_BUTTON
        );
        menuLayout.setAlignment(Pos.CENTER);
        menuLayout.setPrefWidth(FXGL.getAppWidth());
        menuLayout.setPrefHeight(FXGL.getAppHeight());

        getContentRoot().getChildren().add(menuLayout);
    }

    // About
    private void openAboutWindow() {
        Stage stage = new Stage();
        stage.initStyle(StageStyle.UNDECORATED);
        stage.setTitle("About");

        Label titleLabel = new Label("About This Game");
        titleLabel.getStyleClass().add("popup-title");

        Label contentLabel = new Label(
            "Project: COMPROG FINALS PROJECT\n" +
            "WRITTEN IN FXGL\n\n" +
            "Project Members:\n" +
            "• Josh Santeno - Leader\n" +
            "• Kherbin Buenaventura - Larper\n" +
            "• Wren Tulio - Cleanup Crew\n" +
            "• Renzo Diaz - Designer UI/UX\n" +
            "• Jemima Pablo- Designer Animations\n" +
            "• Shane Calara - Designer Menu Layout\n"
        );
        contentLabel.getStyleClass().add("popup-content");
        contentLabel.setWrapText(true);
        contentLabel.setMaxWidth(400);

        Button btnClose = createCloseButton(stage);

        VBox layout = new VBox(20, titleLabel, contentLabel, btnClose);
        layout.getStyleClass().add("popup-root");
        layout.setAlignment(Pos.CENTER);
        layout.setPrefSize(700, 550);

        Scene scene = new Scene(layout);
        scene.getStylesheets().add(
            getClass().getResource("/assets/styles.css").toExternalForm()
        );

        stage.setScene(scene);
        stage.show();
    }

    // INSTRUCTIONS 
    private void openInstructionsWindow() {
        Stage stage = new Stage();
        stage.initStyle(StageStyle.UNDECORATED);
        stage.setTitle("Instructions");

        Label titleLabel = new Label("How to Play");
        titleLabel.getStyleClass().add("popup-title");

        Label contentLabel = new Label(
            "Controls:\n" +
            "•   W    — Move Up\n" +
            "•   S    — Move Down\n" +
            "•   A    — Move Left\n" +
            "•   D    — Move Right\n" +
            "• SPACE  — Shoot \n" +
            "•   Q    — Switch to Fire\n" +
            "•   Q    — Switch to Ice\n\n" +
            "Objective:\n" +
            "• Defeat the boss within the time\n" +
            "• Goodluck."
        );
        contentLabel.getStyleClass().add("popup-content");
        contentLabel.setWrapText(true);
        contentLabel.setMaxWidth(400);

        Button btnClose = createCloseButton(stage);

        VBox layout = new VBox(20, titleLabel, contentLabel, btnClose);
        layout.getStyleClass().add("popup-root");
        layout.setAlignment(Pos.CENTER);
        layout.setPrefSize(700, 550);

        Scene scene = new Scene(layout);
        scene.getStylesheets().add(
            getClass().getResource("/assets/styles.css").toExternalForm()
        );

        stage.setScene(scene);
        stage.show();
    }

    // CREATE BUTTONS
    private Button createButton(String text) {
        Button button = new Button(text);
        button.getStyleClass().add("play-button");
        button.setPrefWidth(400);
        button.setPrefHeight(80);
        return button;
    }

    private Button createCloseButton(Stage stage) {
        Button button = new Button("Close");
        button.getStyleClass().add("close-button");
        button.setOnAction(e -> stage.close());
        return button;
    }
}
