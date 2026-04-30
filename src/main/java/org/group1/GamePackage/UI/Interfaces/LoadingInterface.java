package org.group1.GamePackage.UI.Interfaces;

import com.almasb.fxgl.app.scene.LoadingScene;
import com.almasb.fxgl.dsl.FXGL;
import com.almasb.fxgl.dsl.views.SelfScrollingBackgroundView;
import com.almasb.fxgl.ui.ProgressBar; import javafx.geometry.Pos;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.Text;

public class LoadingInterface extends LoadingScene {

    public LoadingInterface() {

        int w = FXGL.getAppWidth();
        int h = FXGL.getAppHeight();

        ImageView bgImage = new ImageView(FXGL.getAssetLoader().loadTexture("background.png").getImage());
        bgImage.setFitWidth(w);
        bgImage.setFitHeight(h);
        bgImage.setLayoutX(0);
        bgImage.setLayoutY(0);

        var stars1      = scrollLayer("background_stars1.png",       w, h, 10,  0,   0);
        var stars2      = scrollLayer("background_stars2.png",       w, h, 15,  0,   0);
        var stars3      = scrollLayer("background_stars3.png",       w, h, 13,  0,   0);
        var cloud1      = scrollLayer("background_cloud1.png",       w, h, 20,  0,   0);
        var cloud2      = scrollLayer("background_cloud2.png",       w, h, 30,  0, 100);
        var mist1       = scrollLayer("background_mist1.png",        w, h, 30,  0, 500);
        var hills       = scrollLayer("background_hills.png",        w, h,  5,  0, 500);
        var forest      = scrollLayer("background_forest.png",       w, h, 10,  0, 550);
        var largeForest = scrollLayer("background_large_forest.png", w, h, 10,  0, 550);

        ProgressBar loading = new ProgressBar();
        loading.setMaxValue(100);
        loading.setCurrentValue(70);
        loading.setWidth(300);
        loading.setHeight(30);
        loading.setFill(Color.LIGHTBLUE);
        loading.setBackgroundFill(Color.TRANSPARENT);
        loading.setTraceFill(Color.BLUE);        
        loading.setEffect(null);

        Text loadingText = new Text("Loading...");
        loadingText.setFill(Color.LIGHTGRAY);
        loadingText.setFont(Font.font(108));

        VBox loadingLayout = new VBox(loadingText, loading);
        loadingLayout.setAlignment(Pos.CENTER);
        loadingLayout.setPrefWidth(w);
        loadingLayout.setPrefHeight(h);

        Pane root = new Pane(
            bgImage,
            stars1, stars2, stars3,
            cloud1, cloud2,
            mist1, hills, forest, largeForest,
            loadingLayout
        );

        getContentRoot().getChildren().add(root);
    }

    private SelfScrollingBackgroundView scrollLayer(String texture, int w, int h, double speed, double x, double y) {
        var view = new SelfScrollingBackgroundView(
            FXGL.getAssetLoader().loadTexture(texture).getImage(), w, h, speed
        );
        view.setLayoutX(x);
        view.setLayoutY(y);
        return view;
    }
}
