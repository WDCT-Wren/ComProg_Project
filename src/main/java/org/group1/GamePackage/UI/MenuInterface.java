package org.group1.GamePackage.UI;

import java.io.IOException;
import java.util.Objects;

import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.stage.Modality;
import org.group1.GamePackage.Handlers.GameMechanics;

import com.almasb.fxgl.app.scene.FXGLMenu;
import com.almasb.fxgl.app.scene.MenuType;
import com.almasb.fxgl.dsl.FXGL;
import com.almasb.fxgl.dsl.views.SelfScrollingBackgroundView;

import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.Pagination;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import org.group1.GamePackage.UI.Controllers.MainMenuController;
import org.group1.GamePackage.UI.Controllers.PauseController;

public class MenuInterface extends FXGLMenu {

    public MenuInterface() throws IOException {
        super(MenuType.MAIN_MENU);

        //call the fxml file
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/Scenes/MainMenu.fxml"));
        Parent root = loader.load();

        //set the controller
        MainMenuController controller = loader.getController();
        controller.setMenu(this);

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
     */
    public void openInstructionsWindow() {
        Stage stage = new Stage();
        stage.initStyle(StageStyle.UNDECORATED);
        stage.setTitle("Instructions");

        Label titleLabel = new Label();
        titleLabel.getStyleClass().add("popup-title");


        // PAGES LOGIC
        Pagination pagination = new Pagination();

        pagination.setPageFactory(pageIndex -> {
            switch (pageIndex) {

                // first page
                case 0:
                    ImageView WASD_KEYS = new ImageView(
                            new Image(getClass().getResource("/assets/GIF/WASD_KEYS.gif").toExternalForm())
                            );
                    WASD_KEYS.setFitWidth(200);  
                    WASD_KEYS.setFitHeight(200);
                    WASD_KEYS.setPreserveRatio(true);

                    ImageView ESC_KEY = new ImageView(
                            new Image(getClass().getResource("/assets/GIF/ESC_KEY.gif").toExternalForm())
                            );
                    ESC_KEY.setFitHeight(200);
                    ESC_KEY.setFitWidth(200);
                    ESC_KEY.setPreserveRatio(true);

                    HBox firstGifs = new HBox(50, WASD_KEYS, ESC_KEY);
                    firstGifs.setAlignment(Pos.CENTER);

                    titleLabel.setText("Controls - How To");
                    Label controls = new Label(
                            "Controls:\n\n" +
                            "• W — Move Up\t\t   • ESC\n" +
                            "• S — Move Down\t\t —Pause—\n" +
                            "• A — Move Left\n" +
                            "• D — Move Right\n"
                            );
                    controls.getStyleClass().add("popup-content");
                    controls.setWrapText(true);
                    controls.setMaxWidth(400);

                    VBox page1 = new VBox(5, firstGifs, controls);
                    page1.setAlignment(Pos.CENTER);
                    return page1;

                // second page
                case 1:
                    ImageView SPACE_KEY = new ImageView(
                            new Image(getClass().getResource("/assets/GIF/SPACE_KEY.gif").toExternalForm())
                            );

                    ImageView NORMAL_BULLET = new ImageView(
                            new Image(getClass().getResource("/assets/GIF/NORMAL_BULLET.gif").toExternalForm())
                            );
                    SPACE_KEY.setFitWidth(200);  
                    SPACE_KEY.setFitHeight(200);
                    SPACE_KEY.setPreserveRatio(true);

                    NORMAL_BULLET.setScaleX(2);
                    NORMAL_BULLET.setScaleY(2);

                    HBox secondGifs= new HBox(50, SPACE_KEY, NORMAL_BULLET);
                    secondGifs.setAlignment(Pos.CENTER);

                    Label shoot = new Label(
                            "| SPACE | Normal Bullet:\n" +
                            "• Unlimited Ammo\n" +
                            "• More damage than Ice Bullet\n" +
                            "• Hold to Keep Firing"
                            );
                    shoot.getStyleClass().add("popup-content");
                    shoot.setWrapText(true);
                    shoot.setMaxWidth(400);



                    titleLabel.setText("Normal Bullet - How To");

                    VBox page2 = new VBox(10, secondGifs, shoot);
                    page2.setAlignment(Pos.CENTER);
                    return page2;

                // third page
                case 2: 
                    ImageView E_KEY = new ImageView(
                            new Image(getClass().getResource("/assets/GIF/E_KEY.gif").toExternalForm())
                            );

                    ImageView ICE_BULLET = new ImageView(
                            new Image(getClass().getResource("/assets/GIF/ICE_BULLET.gif").toExternalForm())
                            );
                    E_KEY.setFitWidth(200);  
                    E_KEY.setFitHeight(200);
                    E_KEY.setPreserveRatio(true);

                    ICE_BULLET.setScaleX(1);
                    ICE_BULLET.setScaleY(1);

                    HBox thirdGifs = new HBox(10, E_KEY, ICE_BULLET);
                    thirdGifs.setAlignment(Pos.CENTER);

                    Label switchIce = new Label(
                            "| E | Switch to Ice Bullet:\n" +
                            "• Pierces enemies\n" +
                            "• Slows Enemies Down\n\n" +
                            " Requirement: Ice Ammo\n" +
                            " Maximum ammo: 60"
                            );
                    switchIce.getStyleClass().add("popup-content");
                    switchIce.setWrapText(true);
                    switchIce.setMaxWidth(400);

                    titleLabel.setText("Ice Bullet - How To");

                    VBox page3 = new VBox(5, thirdGifs, switchIce);
                    page3.setAlignment(Pos.CENTER);
                    return page3;


                // fourth page
                case 3:
                    ImageView Q_KEY = new ImageView(
                            new Image(getClass().getResource("/assets/GIF/Q_KEY.gif").toExternalForm())
                            );

                    ImageView FIRE_BULLET = new ImageView(
                            new Image(getClass().getResource("/assets/GIF/FIRE_BULLET.gif").toExternalForm())
                            );
                    Q_KEY.setFitWidth(200);  
                    Q_KEY.setFitHeight(200);
                    Q_KEY.setPreserveRatio(true);

                    FIRE_BULLET.setScaleX(1);
                    FIRE_BULLET.setScaleY(1);

                    HBox fourthGifs = new HBox(10, Q_KEY, FIRE_BULLET);
                    fourthGifs.setAlignment(Pos.CENTER);

                    Label switchFire = new Label(
                            "| Q | Switch to Fire Bullet:\n" +
                            "• Pierces enemies\n" +
                            "• Gives Damage over Time Effect\n\n" +
                            " Requirement: Fire Ammo\n" +
                            " Maximum ammo: 60"
                            );
                    switchFire.getStyleClass().add("popup-content");
                    switchFire.setWrapText(true);
                    switchFire.setMaxWidth(400);

                    titleLabel.setText("Fire Bullet - How To");

                    VBox page4 = new VBox(5, fourthGifs, switchFire);
                    page4.setAlignment(Pos.CENTER);
                    return page4;


                // fifth page
                case 4:
                    ImageView normal_enemy = new ImageView(
                            new Image(getClass().getResource("/assets/GIF/normal_enemy.gif").toExternalForm())
                            );
                    Label objective = new Label(
                            "Normal Enemy:\n\n" +
                            "• Gives 1 point\n" +
                            "• Chance to Drop PowerUps"
                            );
                    objective.getStyleClass().add("popup-content");
                    objective.setWrapText(true);
                    objective.setMaxWidth(400);

                    titleLabel.setText("Normal Enemy - How To");

                    VBox page5 = new VBox(10, normal_enemy, objective);
                    page5.setAlignment(Pos.CENTER);
                    return page5;

                case 5:
                    ImageView boss = new ImageView(
                        new Image(getClass().getResource("/assets/GIF/boss.gif").toExternalForm())
                        );
                    boss.setFitWidth(200);  
                    boss.setFitHeight(200);  
                    boss.setPreserveRatio(true);

                    Label bossLabel = new Label(
                            "Boss:\n\n" +
                            "• Defeat to Win the Game\n\n"+
                            "Phases:\n" +
                            "• IDLE • CHARGING • SHOOTING •\n"
                            );
                    bossLabel.getStyleClass().add("popup-content");
                    bossLabel.setWrapText(true);
                    bossLabel.setMaxWidth(400);

                    titleLabel.setText("Boss - How To");

                    VBox page6 = new VBox(10, boss, bossLabel);
                    page6.setAlignment(Pos.CENTER);
                    return page6;

                default:
                    return null;
            }
        });

        pagination.getStyleClass().add("popup-content");
        pagination.setMaxPageIndicatorCount(0);
        pagination.setMouseTransparent(true);
        pagination.setMaxWidth(650);
        pagination.setMaxHeight(450);

        Button btnNext = createButton("➡");
        Button btnPrev = createButton("⬅");
        btnNext.setMaxWidth(50);
        btnNext.setMaxHeight(50);
        btnPrev.setMaxWidth(50);
        btnPrev.setMaxHeight(50);

        btnNext.setOnAction(e -> {
            int nextPage = pagination.getCurrentPageIndex() + 1;
            if (nextPage < pagination.getPageCount()) {
                pagination.setCurrentPageIndex(nextPage);
            }
        });

        btnPrev.setOnAction(e -> {
            int prevPage = pagination.getCurrentPageIndex() - 1;
            if (prevPage >= 0) {
                pagination.setCurrentPageIndex(prevPage);
            }
        });

        VBox navButtons = new VBox(10, btnPrev, btnNext);

        navButtons.setAlignment(Pos.CENTER);


        Button btnClose = createCloseButton(stage);

        // Button HBOX in the bottom
        HBox bottomButtons = new HBox(50, btnPrev, btnClose, btnNext);
        bottomButtons.setAlignment(Pos.CENTER);


        // Changed to border pane for consistency
        BorderPane root = new BorderPane();
        root.getStyleClass().add("popup-root");
        root.setPrefSize(800, 650);
        root.setCenter(pagination);
        root.setTop(titleLabel);
        root.setBottom(bottomButtons);


        BorderPane.setMargin(titleLabel, new Insets(0, 0, 0, 0));
        BorderPane.setAlignment(titleLabel, Pos.CENTER);
        BorderPane.setAlignment(bottomButtons, Pos.CENTER);

        Scene scene = new Scene(root);
        scene.getStylesheets().add(
            getClass().getResource("/StyleSheets/Window.css").toExternalForm()
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
