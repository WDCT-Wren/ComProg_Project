package org.group1.GamePackage.UI;

import com.almasb.fxgl.dsl.views.SelfScrollingBackgroundView;
import org.group1.GamePackage.Handlers.GameMechanics;
import com.almasb.fxgl.app.scene.FXGLMenu;
import com.almasb.fxgl.app.scene.MenuType;
import com.almasb.fxgl.dsl.FXGL;

import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.Pagination;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

import javafx.scene.image.Image;
import java.util.Objects;

public class MenuInterface extends FXGLMenu {

    public MenuInterface() {
        super(MenuType.MAIN_MENU);

        getContentRoot().getStylesheets().add(
            Objects.requireNonNull(getClass().getResource("/assets/styles.css")).toExternalForm()
        );

        int w = FXGL.getAppWidth();
        int h = FXGL.getAppHeight();

        ImageView bgImage = new ImageView(FXGL.getAssetLoader().loadTexture("background.png").getImage());
        bgImage.setFitWidth(w);
        bgImage.setFitHeight(h);
        bgImage.setLayoutX(0);
        bgImage.setLayoutY(0);

        var stars1      = scrollLayer("background_stars1.png",      w, h, 10,  0,   0);
        var stars2      = scrollLayer("background_stars2.png",      w, h, 15,  0,   0);
        var stars3      = scrollLayer("background_stars3.png",      w, h, 13,  0,   0);
        var cloud1      = scrollLayer("background_cloud1.png",      w, h, 20,  0,   0);
        var cloud2      = scrollLayer("background_cloud2.png",      w, h, 30,  0, 100);
        var mist1       = scrollLayer("background_mist1.png",       w, h, 30,  0, 500);
        var hills       = scrollLayer("background_hills.png",       w, h,  5,  0, 500);
        var forest      = scrollLayer("background_forest.png",      w, h, 10,  0, 550);
        var largeForest = scrollLayer("background_large_forest.png",w, h, 10,  0, 550);

        Label TITLE = new Label("LARPATHON");
        TITLE.getStyleClass().add("title-text");

        Button START_BUTTON = createButton("Start Game");
        START_BUTTON.setOnAction(click -> GameMechanics.restartGame());

        Button ABOUT_BUTTON = createButton("About");
        ABOUT_BUTTON.setOnAction(click -> openAboutWindow());

        Button INSTRUCTIONS_BUTTON = createButton("Instructions");
        INSTRUCTIONS_BUTTON.setOnAction(click -> openInstructionsWindow());

        Button EXIT_BUTTON = createButton("Exit");
        EXIT_BUTTON.setOnAction(click -> FXGL.getGameController().exit());

        VBox menuLayout = new VBox(20,
            TITLE,
            START_BUTTON,
            ABOUT_BUTTON,
            INSTRUCTIONS_BUTTON,
            EXIT_BUTTON
        );
        menuLayout.setAlignment(Pos.CENTER);
        menuLayout.setPrefWidth(w);
        menuLayout.setPrefHeight(h);

        Pane root = new Pane(
            bgImage,
            stars1, stars2, stars3,
            cloud1, cloud2,
            mist1, hills, forest, largeForest,
            menuLayout
        );

        getContentRoot().getChildren().add(root);
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
            "DEVELOPED WITH THE USE OF FXGL\n\n" +
            "Project Members:\n" +
            "• Josh Santeno - Leader\n" +
            "• Kherbin Buenaventura - Larper\n" +
            "• Wren Tulio - Cleanup Crew\n" +
            "• Renzo Diaz - Designer UI/UX\n" +
            "• Jemima Pablo- Designer Animations\n" +
            "• Shane Calara - Designer Menu Layout\n"
        );
        contentLabel.getStyleClass().add("about-content");
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


                    titleLabel.setText("Controls - How To");
                    Label controls = new Label(
                            "Controls:\n\n" +
                            "• W — Move Up\n" +
                            "• S — Move Down\n" +
                            "• A — Move Left\n" +
                            "• D — Move Right\n"
                            );
                    controls.getStyleClass().add("popup-content");
                    controls.setWrapText(true);
                    controls.setMaxWidth(400);

                    VBox page1 = new VBox(5, WASD_KEYS, controls);
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
                    ImageView S_KEY = new ImageView(
                            new Image(getClass().getResource("/assets/GIF/S_KEY.gif").toExternalForm())
                            );

                    ImageView ICE_BULLET = new ImageView(
                            new Image(getClass().getResource("/assets/GIF/ICE_BULLET.gif").toExternalForm())
                            );
                    S_KEY.setFitWidth(200);  
                    S_KEY.setFitHeight(200);
                    S_KEY.setPreserveRatio(true);

                    ICE_BULLET.setScaleX(1);
                    ICE_BULLET.setScaleY(1);

                    HBox thirdGifs = new HBox(10, S_KEY, ICE_BULLET);
                    thirdGifs.setAlignment(Pos.CENTER);

                    Label switchIce = new Label(
                            "| S | Switch to Ice Bullet:\n" +
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

                default:
                    return null;
            }
        });

        pagination.getStyleClass().add("popup-content");
        pagination.setMaxPageIndicatorCount(0);
        pagination.setMouseTransparent(true);
        pagination.setMaxWidth(650);
        pagination.setMaxHeight(450);

        Button btnNext = createButton("->");
        Button btnPrev = createButton("<-");
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
            getClass().getResource("/assets/styles.css").toExternalForm()
        );

        stage.setScene(scene);
        stage.show();
    }

    private SelfScrollingBackgroundView scrollLayer(String texture, int w, int h, double speed, double x, double y) {
        var view = new SelfScrollingBackgroundView(
            FXGL.getAssetLoader().loadTexture(texture).getImage(), w, h, speed
        );
        view.setLayoutX(x);
        view.setLayoutY(y);
        return view;
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
